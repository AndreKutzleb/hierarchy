package de.andre_kutzleb.hierarchy.builder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		try {
			ParameterDefinition jct = new ParameterDefinition();
			JCommander jCommander = new JCommander(jct);
			try {
				jCommander.parse(args);
			} catch (ParameterException pe) {
				System.err.println(pe.getMessage());
				jCommander.usage();
				return;
			}

			List<File> foundTopicsFiles = FileCollector.collectAll(jct.sources, jct.recursive);
			Map<String, String> globalOptions = OptionsExtender.extractGlobalOptions(jct);

			if (foundTopicsFiles.isEmpty()) {
				System.out.println("No files found to process in given source directives. Aborting.");
			} else {
				TopicsProcessor.process(foundTopicsFiles, jct.buildThreads, jct.language, jct.outputFolder, globalOptions);
			}

		} catch (Exception e) {
			System.err.println(String.format("Stopping execution due to unexpected exception: %s", e.getMessage()));
		}
	}
}
