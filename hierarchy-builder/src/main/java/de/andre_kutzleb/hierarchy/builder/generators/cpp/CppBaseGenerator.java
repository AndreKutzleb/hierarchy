package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import java.nio.charset.StandardCharsets;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;

public abstract class CppBaseGenerator {

	private static final String CPP_FILES_PATH = "stringtemplate/cpp/";
	private static final String CHARSET = StandardCharsets.UTF_8.name();
	private static final char DELIMITER = '$';

	protected final STGroup templates;

	public CppBaseGenerator() {
		STGroup templates = new STGroup();
		importGroupFile(templates, "cpp_general","cpp_main","cpp_builder");
		this.templates = templates;
	}

	private void importGroupFile(STGroup into, String... toLoad) {
		for (String file : toLoad) {
			STGroup loaded = new STGroupFile(CPP_FILES_PATH + file + ".stg", CHARSET, DELIMITER, DELIMITER);
			into.importTemplates(loaded);
		}
	}

	protected String constant(String type, String name, String value) {
		ST constant = templates.getInstanceOf("Constant");
		constant.add("type", type);
		constant.add("name", name);
		constant.add("value", value);
		return constant.render();
	}
	

	protected String subClassField(BaseEntry data) {
		ST subClassField = templates.getInstanceOf("SubClassField");
		subClassField.add("class", data.getName());
		subClassField.add("name", data.getLowerCaseName());
		return subClassField.render();
	}

}
