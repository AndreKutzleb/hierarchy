package de.andre_kutzleb.hierarchy.builder.generators.cpp;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.andre_kutzleb.hierarchy.builder.model.entry.BaseEntry;
import de.andre_kutzleb.hierarchy.builder.model.tree.Node;
import de.andre_kutzleb.hierarchy.builder.parser.TopicsFileParser;

public class CppGenerationTest {

	private Node<BaseEntry> root;
	private Map<String, String> options;

	@Before
	public void setUp() throws Exception {
		InputStream stream = CppGenerationTest.class.getClassLoader().getResourceAsStream("SwitchAdapter.topics");
		TopicsFileParser parser = new TopicsFileParser(stream,"SwitchAdapter.topics");
		parser.parseTopicsFile();
		root = parser.getRoot();
		options = parser.getOptions();
	}

	@Test
	public void test() {
		CppGenerator cppGenerator = new CppGenerator();
		String generateCppFile = cppGenerator.generateCppFile(root,options);
		System.out.println(generateCppFile);
	}

}
