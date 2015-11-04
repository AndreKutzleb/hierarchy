package de.andre_kutzleb.hierarchy.builder.generators;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.stringtemplate.v4.ST;

import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;
import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry.DATA_TYPE;
import de.andre_kutzleb.hierarchy.builder.model.tree.Node;

/**
 *
 * @author Andre Kutzleb
 */
public class CppGenerator extends AbstractGenerator {
	
	private static final String TEMPLATE_FOLDER = "stringtemplate/cpp/";
	private static final String[] TEMPLATE_FILES = new String[]{"cpp_general", "cpp_inside_namespace", "cpp_outside_namespace", "cpp_builder"};
	private static final char DELIMITER = '$';

	public CppGenerator() {
		super(TEMPLATE_FOLDER, DELIMITER, TEMPLATE_FILES);
	}

	@Override
	protected String fillRecursive(Node<BaseEntry> node, ST insideNamespace) {

		ST clazz = templates.getInstanceOf(node.isRoot ? "BuilderClassTop" : "BuilderClassSub");

		clazz.add("mainClassName", node.getRoot().data.getName());
		clazz.add("className", node.data.getName());
		fillChildren(node, clazz, insideNamespace);
		
		return clazz.render();
	}


	public String generateCppFile(Node<BaseEntry> root, Map<String, String> options) {

		ST mainTemplate = this.templates.getInstanceOf("OutsideNamespace");
		mainTemplate.add("define", root.data.getName());

		ST insideNamespace = this.templates.getInstanceOf("InsideNamespace");
		insideNamespace.add("constant", constant(DATA_TYPE.uint32_t, "BUFFER_SIZE", getBufferSize(options)));
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

	@Override
	protected String getLanguageSpecificType(DATA_TYPE dataType) {

		switch (dataType) {
		case uint8_t:
			return "uint8_t";
		case uint16_t:
			return "uint16_t";
		case uint32_t:
			return "uint32_t";
		case uint64_t:
			return "uint64_t";
		default:
			throw new IllegalArgumentException();
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
