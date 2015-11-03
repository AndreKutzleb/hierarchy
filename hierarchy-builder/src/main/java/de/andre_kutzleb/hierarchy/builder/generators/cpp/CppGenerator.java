package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
	private static final String DEFAULT_BUFFER_SIZE = "512";
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

	private String constant(String type, String name, String value) {
		ST constant = templates.getInstanceOf("Constant");
		constant.add("type", type);
		constant.add("name", name);
		constant.add("value", value);
		return constant.render();
	}

	private String constantHex(String type, String name, String value) {
		return constant(type, name, "0x" + value);
	}

	private String subClassField(BaseEntry data) {
		ST subClassField = templates.getInstanceOf("SubClassField");
		subClassField.add("class", data.getName());
		subClassField.add("name", data.getLowerCaseName());
		return subClassField.render();
	}

	private String fillRecursive(Node<BaseEntry> node, ST insideNamespace) {

		ST clazz = getBuilderClassTemplate(node.isRoot);

		clazz.add("mainClassName", node.getRoot().data.getName());
		clazz.add("className", node.data.getName());

		for (Node<BaseEntry> child : node.children) {
			String subClass = fillRecursive(child, insideNamespace);
			clazz.add("subClass", subClass);
			clazz.add("subClassField", subClassField(child.data));
			fillSubClassGetters(clazz, child);
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
			insideNamespace.add("constant", constantHex(entry.getDataType().cppName, constantEntry.getConstantValueName(), constantEntry.getConstantValue()));
		}
		if (entry instanceof DefaultValueEntry) {
			DefaultValueEntry defaultEntry = (DefaultValueEntry) entry;
			insideNamespace.add("constant", constantHex(entry.getDataType().cppName, defaultEntry.getDefaultValueName(), defaultEntry.getDefaultValue()));
		}
	}

	private void fillSubClassGetters(ST clazz, Node<BaseEntry> node) {

		if (node.data instanceof ConstantValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterConst");
			subClassGetter.add("class", node.data.getName());
			subClassGetter.add("name", node.data.getLowerCaseName());
			subClassGetter.add("mainClassName", node.getRoot().data.getName());
			subClassGetter.add("constantName", ((ConstantValueEntry) node.data).getConstantValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
		if (node.data instanceof CustomValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterCustom");
			subClassGetter.add("class", node.data.getName());
			subClassGetter.add("name", node.data.getLowerCaseName());
			subClassGetter.add("paramType", node.data.getDataType().cppName);
			clazz.add("subClassGetter", subClassGetter.render());
		}
		if (node.data instanceof DefaultValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterDefault");
			subClassGetter.add("class", node.data.getName());
			subClassGetter.add("name", node.data.getLowerCaseName());
			subClassGetter.add("mainClassName", node.getRoot().data.getName());
			subClassGetter.add("constantName", ((DefaultValueEntry) node.data).getDefaultValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
	}

	public String generateCppFile(Node<BaseEntry> root, Map<String, String> options) {

		ST mainTemplate = this.templates.getInstanceOf("OutsideNamespace");
		mainTemplate.add("define", root.data.getName());

		ST insideNamespace = this.templates.getInstanceOf("InsideNamespace");
		insideNamespace.add("constant", constant(DATA_TYPE.uint32_t.cppName, "BUFFER_SIZE", getBufferSize(options)));
		insideNamespace.add("className", root.data.getName());
		insideNamespace.add("builder", fillRecursive(root, insideNamespace));

		List<String> namespacesReverse = getNamespacesReverse(options);
		if (namespacesReverse.isEmpty()) {
			mainTemplate.add("insideNamespace", insideNamespace.render());
		} else {
			ST lastNamespace = this.templates.getInstanceOf("Namespace");
			lastNamespace.add("namespace", namespacesReverse.get(0));
			lastNamespace.add("content", insideNamespace.render());
			// if necessary, put the intermediate result into the next bigger
			// namespace
			for (int i = 1; i < namespacesReverse.size(); i++) {
				ST currentNamespace = this.templates.getInstanceOf("Namespace");
				currentNamespace.add("namespace", namespacesReverse.get(i));
				currentNamespace.add("content", lastNamespace.render());
				lastNamespace = currentNamespace;
			}
			mainTemplate.add("insideNamespace", lastNamespace.render());
		}

		return mainTemplate.render();
	}

	private String getBufferSize(Map<String, String> options) {
		String bufferSize = options.get("buffer_size");
		if (bufferSize == null) {
			return DEFAULT_BUFFER_SIZE;
		} else {
			return bufferSize;
		}
	}

	private List<String> getNamespacesReverse(Map<String, String> options) {
		String inner = options.get("cpp_inner_namespace");
		String outer = options.get("cpp_outer_namespace");

		String innerNamespaces = inner == null ? "" : inner;
		String outerNamespaces = outer == null ? "" : outer;

		boolean innerAvail = !innerNamespaces.isEmpty();
		boolean outerAvail = !outerNamespaces.isEmpty();
		boolean bothAvail = innerAvail && outerAvail;
		boolean anyAvail = innerAvail || outerAvail;

		if (!anyAvail) {
			return Collections.emptyList();
		}

		String allNamespaces;
		if (bothAvail) {
			allNamespaces = outerNamespaces + "::" + innerNamespaces;
		} else if (innerAvail) {
			allNamespaces = innerNamespaces;
		} else {
			allNamespaces = outerNamespaces;
		}

		List<String> asList = Arrays.asList(allNamespaces.split(Pattern.quote("::")));
		Collections.reverse(asList);
		return asList;

	}
}
