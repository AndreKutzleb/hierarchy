package de.andre_kutzleb.hierarchy.builder;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.andre_kutzleb.hierarchy.builder.generators.CppGenerator;
import de.andre_kutzleb.hierarchy.builder.generators.JavaGenerator;
import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;
import de.andre_kutzleb.hierarchy.builder.model.tree.Node;
import de.andre_kutzleb.hierarchy.builder.parser.TopicsFileParser;

public class TopicsProcessor {

	protected static void process(List<File> foundTopicsFiles, int buildThreads, String language, String outputFolder, Map<String, String> globalOptions)
			throws InterruptedException, ExecutionException {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(buildThreads);
		try {

			List<Future<?>> completionHandles = new ArrayList<>();
			for (File f : foundTopicsFiles) {
				Future<?> completionHandle = newFixedThreadPool.submit(() -> processSingle(f, language, outputFolder, globalOptions));
				completionHandles.add(completionHandle);
			}

			for (Future<?> handle : completionHandles) {
				handle.get();
			}
		} finally {
			newFixedThreadPool.shutdown();
		}
	}

	private static void processSingle(File f, String language, String outputFolder, Map<String, String> globalOptions) {
		try {

			long before = System.currentTimeMillis();
			TopicsFileParser parser = new TopicsFileParser(f);
			parser.parseTopicsFile();

			Map<String, String> options = new HashMap<>(globalOptions);
			options.putAll(parser.getOptions());

			switch (language) {
			case "cpp": {
				CppGenerator generator = new CppGenerator();
				String generateCppFile = generator.generateCppFile(parser.getRoot(), options);
				File outDir = new File(outputFolder + File.separator);
				outDir.mkdirs();
				File outFile = new File(outDir.getAbsoluteFile() + File.separator + getCppOutFileName(options, parser.getRoot()));
				Files.write(outFile.toPath(), generateCppFile.getBytes(StandardCharsets.UTF_8));
				break;
			}
			case "java": {
				JavaGenerator generator = new JavaGenerator();
				String generateCppFile = generator.generateJavaFile(parser.getRoot(), options);
				File outDir = new File(outputFolder + File.separator);
				outDir.mkdirs();
				File outFile = new File(outDir.getAbsoluteFile() + File.separator + getJavaOutFileName(options, parser.getRoot()));
				Files.write(outFile.toPath(), generateCppFile.getBytes(StandardCharsets.UTF_8));
				break;
			}
			default:
				throw new IllegalArgumentException("unknown language: " + language);

			}
			long now = System.currentTimeMillis();
			System.out.println(String.format("Processing of %s finished in %d ms.", f.getAbsolutePath(), (now - before)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getCppOutFileName(Map<String, String> options, Node<BaseEntry> node) {
		String name = options.get("cpp_output_file_name");
		if (name == null) {
			return node.data.getName() + "Topics.hpp";
		} else {
			return name + "Topics.hpp";
		}
	}

	private static String getJavaOutFileName(Map<String, String> options, Node<BaseEntry> node) {
		String name = options.get("java_output_file_name");
		if (name == null) {
			return node.data.getName() + "Topics.java";
		} else {
			return name + "Topics.java";
		}
	}
}
