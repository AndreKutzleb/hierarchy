package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class CppBaseGenerator {

	protected final STGroup templates;

	public CppBaseGenerator(STGroup templates) {
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
