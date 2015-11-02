package de.andre_kutzleb.hierarchy.builder;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class ParameterDefinition {

	@Parameter(names = {"--help","-h"}, help = true)
	private boolean help;

	@Parameter(names = "-lang", description = "target language, cpp or java.",required = true)
	protected String language;

	@Parameter(names = "-out", description = "path to output folder for generated files.",required = true)
	protected String outputFolder;

	@Parameter(names = "-src", description = "Comma-separated list of paths to .topics files or folders containing .topics files.",required = true)
	protected String sources;
	
	@Parameter(names = "-r", description = "Search recursively for .topics files in folders given in src")
	protected boolean recursive = false;

	@Parameter(names = {"-t","--threads"}, description = "Number of threads to use for concurrent generation")
	protected int buildThreads = 4;
}
