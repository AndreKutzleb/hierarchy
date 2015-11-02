package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.naming.InitialContext;

import org.omg.CosNaming.IstringHelper;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;
import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry.DATA_TYPE;
import de.andre_kutzleb.hierarchy.builder.model.entry.ConstantValueEntry;
import de.andre_kutzleb.hierarchy.builder.model.entry.CustomValueEntry;
import de.andre_kutzleb.hierarchy.builder.model.entry.DefaultValueEntry;
import de.andre_kutzleb.hierarchy.builder.model.tree.Node;

/**
 *
 * @author Andre Kutzleb
 */
public class CppGenerator {

	// TODO - long literals may not be interpreted as 64-bit-types when using
	// versions prior to c++11 ?

	private static final String CPP_FILES_PATH = "stringtemplate/cpp/";
	private static final String CHARSET = StandardCharsets.UTF_8.name();
	private static final char DELIMITER = '$';

	private final STGroup templates;

	public CppGenerator() {
		STGroup templates = new STGroup();
		importGroupFile(templates, "cpp_general", "cpp_inside_namespace", "cpp_outside_namespace", "cpp_builder");
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

	private String fillRecursive(Node<BaseEntry> node, ST insideNamespace) {

		ST clazz = getBuilderClassTemplate(node.isRoot);

		clazz.add("className", node.data.getName());

		for (Node<BaseEntry> child : node.children) {
			String subClass = fillRecursive(child, insideNamespace);
			clazz.add("subClass", subClass);
			clazz.add("subClassField", subClassField(child.data));
			fillSubClassGetters(clazz, child.data);
			fillConstants(insideNamespace, child.data);
		}
		return clazz.render();
	}

	private ST getBuilderClassTemplate(boolean isRoot) {
		String templateName = isRoot ? "BuilderClassTop" : "BuilderClassSub";
		return this.templates.getInstanceOf(templateName);
	}

	private void fillConstants(ST insideNamespace, BaseEntry entry) {
		if (entry instanceof ConstantValueEntry) {
			ConstantValueEntry constantEntry = (ConstantValueEntry) entry;
			insideNamespace.add("constant", constant(entry.getDataType().cppName, constantEntry.getConstantValueName(), constantEntry.getConstantValue()));
		}
		if (entry instanceof DefaultValueEntry) {
			DefaultValueEntry defaultEntry = (DefaultValueEntry) entry;
			insideNamespace.add("constant", constant(entry.getDataType().cppName, defaultEntry.getDefaultValueName(), defaultEntry.getDefaultValue()));
		}
	}

	private void fillSubClassGetters(ST clazz, BaseEntry entry) {

		// TODO in definition file: concatenate instead of 3 different versions
		if (entry instanceof ConstantValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterConst");
			subClassGetter.add("class", entry.getName());
			subClassGetter.add("name", entry.getLowerCaseName());
			subClassGetter.add("constantName", ((ConstantValueEntry) entry).getConstantValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
		if (entry instanceof CustomValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterCustom");
			subClassGetter.add("class", entry.getName());
			subClassGetter.add("name", entry.getLowerCaseName());
			subClassGetter.add("paramType", entry.getDataType().cppName);
			clazz.add("subClassGetter", subClassGetter.render());
		}
		if (entry instanceof DefaultValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterDefault");
			subClassGetter.add("class", entry.getName());
			subClassGetter.add("name", entry.getLowerCaseName());
			subClassGetter.add("constantName", ((DefaultValueEntry) entry).getDefaultValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
	}

	public String generateCppFile(Node<BaseEntry> root, Map<String, String> options) {

		ST mainTemplate = this.templates.getInstanceOf("OutsideNamespace");
		mainTemplate.add("define", root.data.getName());

		ST insideNamespace = this.templates.getInstanceOf("InsideNamespace");
		insideNamespace.add("constant", constant(DATA_TYPE.uint32_t.cppName, "BUFFER_SIZE", "512"));
		insideNamespace.add("builder", fillRecursive(root, insideNamespace));
		mainTemplate.add("insideNamespace", insideNamespace.render());

		mainTemplate.add("namespace", getNamespace(root.data, options));

		return mainTemplate.render();
	}

	private String getNamespace(BaseEntry rootEntry, Map<String, String> options) {

		String optNamespace = options.get("cpp_namespace_prefix");
		if (optNamespace == null) {
			return rootEntry.getName();
		} else {
			return optNamespace + "::" + rootEntry.getName() + "Topics";
		}
	}
}
