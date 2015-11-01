// Generated from Topics.g4 by ANTLR 4.3

package de.andre_kutzleb.hierarchy.builder.parser.antlr4;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TopicsParser}.
 */
public interface TopicsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TopicsParser#topicLine}.
	 * @param ctx the parse tree
	 */
	void enterTopicLine(@NotNull TopicsParser.TopicLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#topicLine}.
	 * @param ctx the parse tree
	 */
	void exitTopicLine(@NotNull TopicsParser.TopicLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link TopicsParser#topics}.
	 * @param ctx the parse tree
	 */
	void enterTopics(@NotNull TopicsParser.TopicsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#topics}.
	 * @param ctx the parse tree
	 */
	void exitTopics(@NotNull TopicsParser.TopicsContext ctx);

	/**
	 * Enter a parse tree produced by {@link TopicsParser#custom}.
	 * @param ctx the parse tree
	 */
	void enterCustom(@NotNull TopicsParser.CustomContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#custom}.
	 * @param ctx the parse tree
	 */
	void exitCustom(@NotNull TopicsParser.CustomContext ctx);

	/**
	 * Enter a parse tree produced by {@link TopicsParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(@NotNull TopicsParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(@NotNull TopicsParser.AssignContext ctx);

	/**
	 * Enter a parse tree produced by {@link TopicsParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(@NotNull TopicsParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TopicsParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(@NotNull TopicsParser.OptionContext ctx);
}