package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

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
public class CppGenerator extends CppBaseGenerator {

	// TODO - long literals may not be interpreted as 64-bit-types when using
	// versions prior to c++11 ?

	private String fillRecursive(Node<BaseEntry> node, ST mainTemplate) {
	
	 ST clazz = templates.getInstanceOf("BuilderClass");
	 
	 clazz.add("className", node.data.getName());
	 if(node.isRoot) {clazz.add("root", "true");};
	 
	 for (Node<BaseEntry> child : node.children) {
		 String subClass = fillRecursive(child, mainTemplate);
		 clazz.add("subClass", subClass);
		 clazz.add("subClassField", subClassField(child.data));
		 fillSubClassGetters(clazz,child.data);
		 }
	
//	 clazz.add(name, value)
//	 clazz.add("className", node.data.getName());
//	 fillConstants(node, mainTemplate);
//	
//	 // insert child classes recursively
//	 for (Node<BaseEntry> child : node.children) {
//	 String subClass = fillRecursive(child, mainTemplate);
//	 clazz.add("public", subClass);
//	 }
//	
//	 clazz.add("public", createConstructor(node));
//	
//	 // insert subclass getter
//	 for (Node<BaseEntry> child : node.children) {
//	 insertSubClassGetter(child, clazz);
//	 }
	
	 return clazz.render();
	 }

	private void fillSubClassGetters(ST clazz, BaseEntry entry) {
		if (entry instanceof ConstantValueEntry) {
			ST subClassGetter = templates.getInstanceOf("SubClassGetterConst");
			subClassGetter.add("class", entry.getName());
			subClassGetter.add("name", entry.getLowerCaseName());
			subClassGetter.add("value", ((ConstantValueEntry) entry).getConstantValueName());
			clazz.add("subClassGetter", subClassGetter.render());
		}
	}

	// MODULE_X& module_x() {
	// this->append(Constants::val);
	// return from_;
	// }
	// MODULE_X& module_x(uint8_t val) {
	// this->append(val);
	// return from_;
	// }
	// MODULE_X& module_x_default() {
	// this->append(val);
	// return from_;
	// }

	// private String hexLiteral(String hexDigits) {
	// return String.format("0x%s", hexDigits);
	// }
	// private void fillConstants(Node<BaseEntry> node, ST mainTemplate) {
	//
	// if (node.data instanceof ConstantValueEntry) {
	// ConstantValueEntry entry = (ConstantValueEntry) node.data;
	// String constant = createConstant(entry.getDataType().cppName,
	// entry.getConstantValueName(), hexLiteral(entry.getConstantValue()),
	// entry.getComment());
	// mainTemplate.add("constants", constant);
	// }
	// if (node.data instanceof DefaultValueEntry) {
	// DefaultValueEntry entry = (DefaultValueEntry) node.data;
	// String constant = createConstant(entry.getDataType().cppName,
	// entry.getDefaultValueName(), hexLiteral(entry.getDefaultValue()),
	// entry.getComment());
	// mainTemplate.add("constants", constant);
	// }
	//
	// }

	// private String createConstant(String constantType, String constantName,
	// String constantValue, String comment) {
	//
	// ST st = group.getInstanceOf("Declaration");
	//
	// st.add("modifier", "const");
	// st.add("type", String.format("%-5s", constantType));
	// st.add("comment", comment);
	// st.add("name", constantName);
	// st.add("value", constantValue);
	//
	// return st.render();
	//
	// }

	// private String createConstructor(Node<BaseEntry> node) {
	// ST constructor = group.getInstanceOf("Constructor");
	// constructor.add("className", node.data.getUpperCaseName());
	// ST appendStatement = group.getInstanceOf("Statement");
	// appendStatement.add("call",
	// "id."+node.data.getDataType().appendFunctionName);
	// appendStatement.add("cast", node.data.getDataType().cppName);
	//
	// if (node.level == 0) {
	// constructor.add("initializer", "id()");
	// // TODO currently only allows constant at level 0
	// appendStatement.add("param",
	// ((ConstantValueEntry)node.data).getConstantValueName());
	//
	// }
	//
	// // constant value for field
	// else if (node.data instanceof ConstantValueEntry) {
	// constructor.add("param", MSG_TYPE + "& f");
	// constructor.add("initializer", "AbstractFactory(f)");
	// appendStatement.add("param",
	// ((ConstantValueEntry)node.data).getConstantValueName());
	//
	// }
	// // user provided value for field
	// else {
	// constructor.add("param", MSG_TYPE + "& f");
	// constructor.add("param", node.data.getDataType().cppName + " value");
	// constructor.add("initializer", "AbstractFactory(f)");
	// appendStatement.add("param", "value");
	// }
	// constructor.add("statement", appendStatement.render());
	//
	// return constructor.render();
	// }

	// private void insertSubClassGetter(Node<BaseEntry> child, ST clazz) {
	// BaseEntry data = child.data;
	//
	// ST getter = group.getInstanceOf("Function");
	// getter.add("returnType", data.getUpperCaseName());
	// getter.add("functionName", data.getLowerCaseName());
	//
	// ST getterStatement = group.getInstanceOf("Statement");
	// getterStatement.add("call", "return " + data.getUpperCaseName());
	// getterStatement.add("param", "id");
	//
	// if (data instanceof CustomValueEntry) {
	//
	// getter.add("param", data.getDataType().cppName + " value");
	// getterStatement.add("param", "value");
	//
	// if (data instanceof DefaultValueEntry) {
	// ST defaultGetter = group.getInstanceOf("Function");
	// defaultGetter.add("returnType", data.getUpperCaseName());
	// defaultGetter.add("functionName", data.getLowerCaseName()+ "_default");
	//
	// ST customGetterStatement = group.getInstanceOf("Statement");
	// customGetterStatement.add("call", "return " + data.getUpperCaseName());
	// customGetterStatement.add("param", "id");
	// customGetterStatement.add("param",
	// ((DefaultValueEntry)data).getDefaultValueName());
	//
	// defaultGetter.add("statement", customGetterStatement.render());
	// clazz.add("public", defaultGetter.render());
	//
	// }
	// }
	// getter.add("statement", getterStatement.render());
	// clazz.add("public", getter.render());
	//
	//
	// }

	// public String getAbstractFactory() {
	// ST classBody = group.getInstanceOf("Class");
	//
	// classBody.add("className", "AbstractFactory");
	//
	// ST id = group.getInstanceOf("Declaration");
	// id.add("type", MSG_TYPE + "&");
	// id.add("name", "id");
	// classBody.add("protected", id);
	//
	// ST constructor = group.getInstanceOf("Constructor");
	// constructor.add("className", "AbstractFactory");
	// constructor.add("param", MSG_TYPE + "& f");
	// constructor.add("initializer", "id(f)");
	//
	// classBody.add("protected", constructor);
	//
	// ST buildFunction = group.getInstanceOf("Function");
	// buildFunction.add("returnType", MSG_TYPE);
	// buildFunction.add("functionName", "build");
	// buildFunction.add("statement", "return id");
	//
	// classBody.add("public", buildFunction);
	// return classBody.render();
	// }

	public String generateCppFile(Node<BaseEntry> root) {

		ST mainTemplate = this.templates.getInstanceOf("MainTemplate");
		mainTemplate.add("constant", constant(DATA_TYPE.uint32_t.cppName, "BUFFER_SIZE", "512"));

		String builderHierarchy = fillRecursive(root,null);
		mainTemplate.add("builder", builderHierarchy);
		// String projectName = "ZSDN";
		// String headerFileName = root.data.getName();
		// String define = String.format(" %s_%s_H", projectName,
		// headerFileName);
		//
		// mainTemplate.add("header", "Autogenerated Code. Changes here will be
		// automatically overwritten.");
		// mainTemplate.add("pre", "ifndef" + define);
		// mainTemplate.add("pre", "define" + define);
		// mainTemplate.add("inc", "stdint.h");
		// mainTemplate.add("inc", "zmf/MessageType.hpp");
		// mainTemplate.add("namespace", headerFileName.toLowerCase() +
		// "_topics");
		// mainTemplate.add("footer", "#endif" + " // " +define);
		// mainTemplate.add("inner", getAbstractFactory());
		//
		// root.children.forEach(child -> {
		// String wholeClass = fillRecursive(child, mainTemplate);
		// mainTemplate.add("inner", wholeClass);
		// });

		return mainTemplate.render();
	}

	private ST mainTemplate() {
		return null;
	}

}
