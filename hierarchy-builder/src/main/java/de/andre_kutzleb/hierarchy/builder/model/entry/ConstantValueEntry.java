package de.andre_kutzleb.hierarchy.builder.model.entry;

public class ConstantValueEntry extends BaseEntry {
	private final String constantValueName;
	private final String constantValue;

	public ConstantValueEntry(DATA_TYPE dataType, String name, String comment,String constantValueName, String constantValue) {
		super(dataType, name, comment);
		this.constantValueName = constantValueName;
		this.constantValue = constantValue;
	}


	public String getConstantValue() {
		return constantValue;
	}
	
	public String getConstantValueName() {
		return constantValueName;
	}
}
