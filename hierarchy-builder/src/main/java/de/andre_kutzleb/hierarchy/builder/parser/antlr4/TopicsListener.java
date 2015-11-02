// Generated from Topics.g4 by ANTLR 4.5.1

package de.andre_kutzleb.hierarchy.builder.parser.antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TopicsParser}.
 */
public interface TopicsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TopicsParser#topics}.
	 * @param ctx the parse tree
	 */
	void enterTopics(TopicsParser.TopicsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#topics}.
	 * @param ctx the parse tree
	 */
	void exitTopics(TopicsParser.TopicsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TopicsParser#topicLine}.
	 * @param ctx the parse tree
	 */
	void enterTopicLine(TopicsParser.TopicLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#topicLine}.
	 * @param ctx the parse tree
	 */
	void exitTopicLine(TopicsParser.TopicLineContext ctx);
	/**
	 * Enter a parse tree produced by {@link TopicsParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(TopicsParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(TopicsParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link TopicsParser#custom}.
	 * @param ctx the parse tree
	 */
	void enterCustom(TopicsParser.CustomContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#custom}.
	 * @param ctx the parse tree
	 */
	void exitCustom(TopicsParser.CustomContext ctx);
	/**
	 * Enter a parse tree produced by {@link TopicsParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(TopicsParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(TopicsParser.OptionContext ctx);
}