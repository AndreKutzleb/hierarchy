package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import java.nio.charset.StandardCharsets;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public abstract class CppBaseGenerator {

	protected final STGroup templates;

	public CppBaseGenerator() {
		STGroup templates = new STGroup();
		
		STGroup cppGeneral = new STGroupFile("stringtemplate/cpp/cpp_general.stg",StandardCharsets.UTF_8.name(),'$','$');
		STGroup cppHeader = new STGroupFile("stringtemplate/cpp/cpp_main.stg",StandardCharsets.UTF_8.name(),'$','$');
		
		templates.importTemplates(cppGeneral);
		templates.importTemplates(cppHeader);
		this.templates = templates;
	}

	protected String constant(String type, String name, String value) {
		ST constant = templates.getInstanceOf("Constant");
		constant.add("type", type);
		constant.add("name", name);
		constant.add("value", value);
		return constant.render();
	}
	

}
