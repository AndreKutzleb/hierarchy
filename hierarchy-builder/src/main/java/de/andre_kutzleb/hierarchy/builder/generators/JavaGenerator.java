package de.andre_kutzleb.hierarchy.builder.generators;

import java.nio.charset.StandardCharsets;
import java.util.Map;

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
public class JavaGenerator {

	private static final String JAVA_FILES_PATH = "stringtemplate/java/";
	private static final String CHARSET = StandardCharsets.UTF_8.name();
	private static final String DEFAULT_BUFFER_SIZE = "512";
	private static final char DELIMITER = '$';

	private final STGroup templates;

	public JavaGenerator() {
		STGroup templates = new STGroup();
		importGroupFile(templates,"java_general", "java_outer", "java_builder");
		this.templates = templates;
	}

	private void importGroupFile(STGroup into, String... toLoad) {
		for (String file : toLoad) {
			STGroup loaded = new STGroupFile(JAVA_FILES_PATH + file + ".stg", CHARSET, DELIMITER, DELIMITER);
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

		ST clazz = templates.getInstanceOf("BuilderClass");

		clazz.add("className", node.isRoot ? "Instance" : node.data.getName());

		for (Node<BaseEntry> child : node.children) {
			String subClass = fillRecursive(child, insideNamespace);
			clazz.add("subClass", subClass);
			clazz.add("subClassField", subClassField(child.data));
			fillSubClassGetters(clazz, child);
			fillConstants(insideNamespace, child.data);
		}
		return clazz.render();
	}

	private void fillConstants(ST insideNamespace, BaseEntry entry) {
		if (entry instanceof ConstantValueEntry) {
			ConstantValueEntry constantEntry = (ConstantValueEntry) entry;
			insideNamespace.add("constant", constantHex(entry.getDataType().javaName, constantEntry.getConstantValueName(), constantEntry.getConstantValue()));
		}
		if (entry instanceof DefaultValueEntry) {
			DefaultValueEntry defaultEntry = (DefaultValueEntry) entry;
			insideNamespace.add("constant", constantHex(entry.getDataType().javaName, defaultEntry.getDefaultValueName(), defaultEntry.getDefaultValue()));
		}
	}

	private void fillSubClassGetters(ST clazz, Node<BaseEntry> node) {

		if (node.data instanceof ConstantValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterConst");
			subClassGetter.add("class", node.data.getName());
			subClassGetter.add("name", node.data.getLowerCaseName());
			subClassGetter.add("constantName", ((ConstantValueEntry) node.data).getConstantValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
		
		if (node.data instanceof CustomValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterCustom");
			subClassGetter.add("class", node.data.getName());
			subClassGetter.add("name", node.data.getLowerCaseName());
			subClassGetter.add("paramType", node.data.getDataType().javaName);

			clazz.add("subClassGetter", subClassGetter.render());
		}
		if (node.data instanceof DefaultValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterDefault");
			subClassGetter.add("class", node.data.getName());
			subClassGetter.add("name", node.data.getLowerCaseName());
			subClassGetter.add("constantName", ((DefaultValueEntry) node.data).getDefaultValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
	}

	public String generateJavaFile(Node<BaseEntry> root, Map<String, String> options) {

		ST mainTemplate = this.templates.getInstanceOf("Outer");
		mainTemplate.add("package", options.get("java_package"));
		mainTemplate.add("className", root.data.getName()+"Topics");
		mainTemplate.add("constant", constant(DATA_TYPE.uint32_t.javaName, "BUFFER_SIZE", getBufferSize(options)));
		mainTemplate.add("builder", fillRecursive(root, mainTemplate));
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
}
