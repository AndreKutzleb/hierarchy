package de.andre_kutzleb.hierarchy.builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;

import de.andre_kutzleb.hierarchy.builder.generators.cpp.CppGenerator;

public class Main {
	
	private String constant(String type, String name) {
		return null;
	}
	
	public static void main(String[] args) throws IOException {

		STGroup cpp = new STGroup();
		
		STGroup cppGeneral = new STGroupFile("stringtemplate/cpp/cpp_general.stg",StandardCharsets.UTF_8.name(),'$','$');
		STGroup cppHeader = new STGroupFile("stringtemplate/cpp/cpp_main.stg",StandardCharsets.UTF_8.name(),'$','$');
		
		cpp.importTemplates(cppGeneral);
		cpp.importTemplates(cppHeader);
		
	CppGenerator cppGen = new CppGenerator(cpp);
	//cppGen.generateCppFile(root);
	}
}
