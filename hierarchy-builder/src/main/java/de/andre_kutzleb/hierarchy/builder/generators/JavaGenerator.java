package de.andre_kutzleb.hierarchy.builder.generators;

import java.util.Map;

import org.stringtemplate.v4.ST;

import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;
import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry.DATA_TYPE;
import de.andre_kutzleb.hierarchy.builder.model.tree.Node;

/**
 *
 * @author Andre Kutzleb
 */
public class JavaGenerator extends AbstractGenerator {
	private static final String TEMPLATE_FOLDER = "stringtemplate/java/";
	private static final String[] TEMPLATE_FILES = new String[]{"java_general", "java_outer", "java_builder"};
	private static final char DELIMITER = '$';

	public JavaGenerator() {
		super(TEMPLATE_FOLDER, DELIMITER, TEMPLATE_FILES);
	}
	

	@Override
	protected String fillRecursive(Node<BaseEntry> node, ST insideNamespace) {

		ST clazz = templates.getInstanceOf("BuilderClass");
		clazz.add("className", node.isRoot ? "Instance" : node.data.getName());
		fillChildren(node,clazz,insideNamespace);
		return clazz.render();
	}

	public String generateJavaFile(Node<BaseEntry> root, Map<String, String> options) {

		ST mainTemplate = this.templates.getInstanceOf("Outer");
		mainTemplate.add("package", options.get("java_package"));
		mainTemplate.add("className", root.data.getName() + "Topics");
		mainTemplate.add("constant", constant(DATA_TYPE.uint32_t, "BUFFER_SIZE", getBufferSize(options)));
		mainTemplate.add("builder", fillRecursive(root, mainTemplate));
		return mainTemplate.render();
	}

	@Override
	protected String getLanguageSpecificType(DATA_TYPE dataType) {
		switch (dataType) {
		case uint8_t:
			return "byte";
		case uint16_t:
			return "short";
		case uint32_t:
			return "int";
		case uint64_t:
			return "long";
		default:
			throw new IllegalArgumentException();
		}
	}
}
