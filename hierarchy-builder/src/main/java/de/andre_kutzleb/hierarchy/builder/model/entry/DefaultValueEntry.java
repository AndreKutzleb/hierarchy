package de.andre_kutzleb.hierarchy.builder.model.entry;

public class DefaultValueEntry extends CustomValueEntry {
	
	private final String defaultValueName;
	private final String defaultValue;

	public DefaultValueEntry(DATA_TYPE dataType, String name, String comment, String defaultValueName, String defaultValue) {
		super(dataType, name, comment);
		this.defaultValueName = defaultValueName;
		this.defaultValue = defaultValue;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public String getDefaultValueName() {
		return defaultValueName;
	}
}
