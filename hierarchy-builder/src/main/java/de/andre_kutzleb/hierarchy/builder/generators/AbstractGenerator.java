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

public abstract class AbstractGenerator {

	protected static final String DEFAULT_BUFFER_SIZE = "512";
	protected final STGroup templates;

	public AbstractGenerator(String folder, char delimiter, String... files) {
		STGroup templates = new STGroup();
		importGroupFile(templates, folder, delimiter, files);
		this.templates = templates;
	}

	protected String constant(DATA_TYPE type, String name, String value) {
		ST constant = templates.getInstanceOf("Constant");
		constant.add("type", getLanguageSpecificType(type));
		constant.add("name", name);
		constant.add("value", value);
		return constant.render();
	}

	protected String constantHex(DATA_TYPE type, String name, String value) {
		return constant(type, name, "0x" + value);
	}

	protected void fillChildren(Node<BaseEntry> node,ST clazz, ST insideNamespace) {
		for (Node<BaseEntry> child : node.children) {
			String subClass = fillRecursive(child, insideNamespace);
			clazz.add("subClass", subClass);
			clazz.add("subClassField", subClassField(child.data));
			fillSubClassGetters(clazz, child);
			fillConstants(insideNamespace, child.data);
		}		
	}

	protected void fillConstants(ST insideNamespace, BaseEntry entry) {
		if (entry instanceof ConstantValueEntry) {
			ConstantValueEntry constantEntry = (ConstantValueEntry) entry;
			insideNamespace.add("constant", constantHex(entry.getDataType(), constantEntry.getConstantValueName(), constantEntry.getConstantValue()));
		}
		if (entry instanceof DefaultValueEntry) {
			DefaultValueEntry defaultEntry = (DefaultValueEntry) entry;
			insideNamespace.add("constant", constantHex(entry.getDataType(), defaultEntry.getDefaultValueName(), defaultEntry.getDefaultValue()));
		}
	}

	protected abstract String fillRecursive(Node<BaseEntry> child, ST insideNamespace);

	protected void fillSubClassGetters(ST clazz, Node<BaseEntry> node) {

		if (node.data instanceof ConstantValueEntry) {
			String constantName = ((ConstantValueEntry) node.data).getConstantValueName();
			clazz.add("subClassGetter", subClassGetter(node, node.data.getLowerCaseName(),null, constantName, null));
		}

		if (node.data instanceof CustomValueEntry) {
			String typeName = getLanguageSpecificType(node.data.getDataType());
			clazz.add("subClassGetter", subClassGetter(node, node.data.getLowerCaseName(),null, null, typeName));
		}
		
		if (node.data instanceof DefaultValueEntry) {
			String defaultName = ((DefaultValueEntry) node.data).getDefaultValueName();
			clazz.add("subClassGetter", subClassGetter(node, node.data.getLowerCaseName(),"_default", defaultName, null));
		}
	}

	protected String getBufferSize(Map<String, String> options) {
		String bufferSize = options.get("buffer_size");
		if (bufferSize == null) {
			return DEFAULT_BUFFER_SIZE;
		} else {
			return bufferSize;
		}
	}

	protected abstract String getLanguageSpecificType(DATA_TYPE dataType);
	
	private void importGroupFile(STGroup into, String folder, char delimiter, String... toLoad) {
		for (String file : toLoad) {
			STGroup loaded = new STGroupFile(folder + file + ".stg", StandardCharsets.UTF_8.name(), delimiter, delimiter);
			into.importTemplates(loaded);
		}
	}
	
	protected String subClassField(BaseEntry data) {
		ST subClassField = templates.getInstanceOf("SubClassField");
		subClassField.add("class", data.getName());
		subClassField.add("name", data.getLowerCaseName());
		return subClassField.render();
	}

	private String subClassGetter(Node<BaseEntry> node, String name,String defaultt, String constantName, String paramType) {
		ST subClassGetter = templates.getInstanceOf("SubClassGetter");
		subClassGetter.add("class", node.data.getName());
		subClassGetter.add("name", name);
		subClassGetter.add("defaultt", defaultt);
		subClassGetter.add("mainClassName", node.getRoot().data.getName());
		subClassGetter.add("constantName", constantName);
		subClassGetter.add("paramType", paramType);
		return subClassGetter.render();
	}

}
