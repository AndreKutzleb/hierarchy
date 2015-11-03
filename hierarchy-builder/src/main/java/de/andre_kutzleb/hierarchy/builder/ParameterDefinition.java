package de.andre_kutzleb.hierarchy.builder;

import com.beust.jcommander.Parameter;

public class ParameterDefinition {

	@Parameter(names = {"--help","-h"}, help = true)
	private boolean help;

	@Parameter(names = {"--java_package", "-j"}, description = "package name for all generated java files.")
	protected String javaPackageName;
	
	@Parameter(names = {"--cpp_outer_namespace","-c"}, description = "outer namespace(s), (e.g. \"a::b::c\" to be wrapped around all cpp files.")
	protected String cppOuterNamespace;
	
	@Parameter(names = {"--language", "-l"}, description = "target language, cpp or java.",required = true)
	protected String language;
	
	@Parameter(names = {"--out", "-o"}, description = "path to output folder for generated files.",required = true)
	protected String outputFolder;

	@Parameter(names = {"--source", "-s"}, description = "Comma-separated list of paths to .topics files or folders containing .topics files.",required = true)
	protected String sources;
	
	@Parameter(names = {"--recursive", "-r"}, description = "Search recursively for .topics files in folders given in src")
	protected boolean recursive = false;

	@Parameter(names = {"--threads", "-t"}, description = "Number of threads to use for concurrent generation")
	protected int buildThreads = 4;
}
