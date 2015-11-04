package de.andre_kutzleb.hierarchy.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FileUtils;

public class FileCollector {

	protected static List<File> collectAll(String sources, boolean recursive) throws FileNotFoundException {
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
