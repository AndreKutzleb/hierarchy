package de.andre_kutzleb.hierarchy.builder;

import java.util.HashMap;
import java.util.Map;

public class OptionsExtender {
	
	protected static Map<String,String> extractGlobalOptions(ParameterDefinition jct) {
		Map<String,String> globalOptions = new HashMap<>();
		if(jct.javaPackageName != null) {
			globalOptions.put("java_package", jct.javaPackageName);
		}
		if(jct.cppOuterNamespace != null) {
			globalOptions.put("cpp_outer_namespace", jct.cppOuterNamespace);
		}	
		return globalOptions;
	}

}
