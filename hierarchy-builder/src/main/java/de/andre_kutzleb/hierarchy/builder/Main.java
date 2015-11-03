package de.andre_kutzleb.hierarchy.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FileUtils;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import de.andre_kutzleb.hierarchy.builder.generators.CppGenerator;
import de.andre_kutzleb.hierarchy.builder.generators.JavaGenerator;
import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;
import de.andre_kutzleb.hierarchy.builder.model.tree.Node;
import de.andre_kutzleb.hierarchy.builder.parser.TopicsFileParser;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		// TODO
		//args = new String[]{ "-l", "java", "-s", "/home/zsdn/module_interface_definitions/topics/SwitchRegistryModule.topics", "-r", "-o", "/home/zsdn/testout/", "-j", "test.bla.bla", "-c" , "zsdn::modules"};

		ParameterDefinition jct = new ParameterDefinition();
		JCommander jCommander = new JCommander(jct);
		try {
			jCommander.parse(args);
		} catch (ParameterException pe) {
			System.err.println(pe.getMessage());
			jCommander.usage();
			return;
		}

		List<File> foundTopicsFiles = collectAll(jct.sources, jct.recursive);
		Map<String,String> globalOptions = new HashMap<>();
		if(jct.javaPackageName != null) {
			globalOptions.put("java_package", jct.javaPackageName);
		}
		if(jct.cppOuterNamespace != null) {
			globalOptions.put("cpp_outer_namespace", jct.cppOuterNamespace);
		}

		if (foundTopicsFiles.isEmpty()) {
			System.out.println("No files found to process in given source directives. Aborting.");
		} else {
			process(foundTopicsFiles, jct.buildThreads, jct.language, jct.outputFolder,globalOptions);
		}

		// System.out.println(jct.groups);

		// cppGen.generateCppFile(root);
	}

	private static void process(List<File> foundTopicsFiles, int buildThreads, String language, String outputFolder, Map<String, String> globalOptions) throws InterruptedException, ExecutionException {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(buildThreads);
		try {

			System.out.println("Processing the following files:");
			for (File f : foundTopicsFiles) {
				System.out.println(" " + f.getAbsolutePath());
			}
			List<Future<?>> completionHandles = new ArrayList<>();
			for (File f : foundTopicsFiles) {
				Future<?> completionHandle = newFixedThreadPool.submit(() -> processSingle(f, language, outputFolder,globalOptions));
				completionHandles.add(completionHandle);
			}

			for (Future<?> handle : completionHandles) {
				handle.get();
			}
		} finally {
			newFixedThreadPool.shutdown();
		}
	}

	private static void processSingle(File f, String language, String outputFolder,Map<String,String> globalOptions) {
		try {

			System.out.println("Processing of " + f.getAbsolutePath() + " started.");
			TopicsFileParser parser = new TopicsFileParser(f);
			parser.parseTopicsFile();
			
			Map<String,String> options = new HashMap<>(globalOptions);
			options.putAll(parser.getOptions());
			
			switch (language) {
			case "cpp": {
				CppGenerator generator = new CppGenerator();
				String generateCppFile = generator.generateCppFile(parser.getRoot(), options);
				// FIXME
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
			System.out.println("Processing of " + f.getAbsolutePath() + " finished.");
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private static String getCppOutFileName(Map<String, String> options, Node<BaseEntry> node) {
		String name = options.get("cpp_output_file_name");
		if (name == null) {
			return node.data.getName() + "Topics.hpp";
		} else {
			return name + "Topics.hpp";
		}
	}

	private static List<File> collectAll(String sources, boolean recursive) throws FileNotFoundException {
		List<File> sourcesIt = Arrays.asList(sources.split(Pattern.quote(","))).stream().map(String::trim).map(File::new).collect(Collectors.toList());

		List<File> nonExisting = sourcesIt.stream().filter(f -> !f.exists()).collect(Collectors.toList());
		if (nonExisting.size() > 0) {
			StringBuilder b = new StringBuilder();
			nonExisting.forEach(s -> b.append(s + "\n"));
			throw new FileNotFoundException("Aborting, The following files could not be found:\n" + b.toString());
		}

		Stream<File> folders = sourcesIt.stream().filter(File::isDirectory);
		Stream<File> files = sourcesIt.stream().filter(File::isFile);

		Stream<File> recursivelyFoundFiles = folders
				.flatMap(folder -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(FileUtils.iterateFiles(folder, new String[] { "topics" }, recursive), Spliterator.ORDERED), false));

		Stream<File> allFiles = Stream.concat(files, recursivelyFoundFiles);

		return allFiles.distinct().collect(Collectors.toList());
	}

}
