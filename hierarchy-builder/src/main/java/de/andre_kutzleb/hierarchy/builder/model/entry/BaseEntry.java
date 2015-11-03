package de.andre_kutzleb.hierarchy.builder.model.entry;

/**
 *
 * @author Andre Kutzleb
 */
public class BaseEntry {

	public enum DATA_TYPE {
		uint8_t("byte", "uint8_t", "appendMatch8"), uint16_t("short", "uint16_t", "appendMatch16"), uint32_t("int", "uint32_t", "appendMatch32"), uint64_t("long", "uint64_t", "appendMatch64");

		public final String javaName;
		public final String cppName;
		public final String appendFunctionName;

		DATA_TYPE(String javaName, String cppName, String appendFunctionName) {
			this.javaName = javaName;
			this.cppName = cppName;
			this.appendFunctionName = appendFunctionName;
		};

		public String javaNameCamelCase() {
			return javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
		}
	}

	private final DATA_TYPE dataType;
	private final String name;
	private final String comment;

	public BaseEntry(DATA_TYPE dataType, String name, String comment) {
		this.dataType = dataType;
		this.name = name;
		this.comment = comment;
	}

	public DATA_TYPE getDataType() {
		return dataType;
	}

	public String getComment() {
		return comment;
	}

	public String getName() {
		// TO avoid a getter name vs className conflict, make first letter
		// uppercase if the whole string is lowercase
		if (name.equals(name.toLowerCase())) {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		} else {
			return name;
		}
	}
	// public String getUpperCaseName() {
	// return name.toUpperCase();
	// }

	public String getLowerCaseName() {
		return name.toLowerCase();
	}

	// public String getGetterName() {
	// return name.toLowerCase();
	// }
	//
	// public TYPE getType() {
	// return numberType;
	// }
	//
	// public LENGTH getLength() {
	// return length;
	// }
	//
	// public String getHexValueWithPrefix() {
	// return valueAsHexWithPrefix;
	// }
	//
	// public boolean hasComment() {
	// return comment != null;
	// }
	//
	// public String getComment() {
	// return comment;
	// }
	//
	// public boolean hasWildcardDefault () {
	// return numberType == TYPE.CUSTOM &&
	// wildcardDefaultValueAsHexWithoutPrefix != null;
	// }
	//
	// public String getWildcardDefaultValueAsHexWithoutPrefix() {
	// return wildcardDefaultValueAsHexWithoutPrefix;
	// }
	//
	// public String getWildcardDefaultValueAsHexWithPrefix() {
	// return wildcardDefaultValueAsHexWithPrefix;
	// }
	//
	// public Long getValue() {
	// return value;
	// }
	//
	// public static Entry_ root(String string) {
	// return null;
	// }

}
