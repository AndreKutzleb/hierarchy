// Generated from Topics.g4 by ANTLR 4.5.1

package de.andre_kutzleb.hierarchy.builder.parser.antlr4;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TopicsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, Identifier=5, DecimalIntegerLiteral=6, 
		HexIntegerLiteral=7, CustomHexIntegerLiteral=8, INDENT=9, WS=10, LINE_COMMENT=11, 
		STRING=12;
	public static final int
		RULE_topics = 0, RULE_topicLine = 1, RULE_assign = 2, RULE_custom = 3, 
		RULE_option = 4;
	public static final String[] ruleNames = {
		"topics", "topicLine", "assign", "custom", "option"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'default'", "'option'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "Identifier", "DecimalIntegerLiteral", "HexIntegerLiteral", 
		"CustomHexIntegerLiteral", "INDENT", "WS", "LINE_COMMENT", "STRING"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Topics.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TopicsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class TopicsContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(TopicsParser.EOF, 0); }
		public List<TopicLineContext> topicLine() {
			return getRuleContexts(TopicLineContext.class);
		}
		public TopicLineContext topicLine(int i) {
			return getRuleContext(TopicLineContext.class,i);
		}
		public List<OptionContext> option() {
			return getRuleContexts(OptionContext.class);
		}
		public OptionContext option(int i) {
			return getRuleContext(OptionContext.class,i);
		}
		public List<TerminalNode> LINE_COMMENT() { return getTokens(TopicsParser.LINE_COMMENT); }
		public TerminalNode LINE_COMMENT(int i) {
			return getToken(TopicsParser.LINE_COMMENT, i);
		}
		public TopicsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_topics; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).enterTopics(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).exitTopics(this);
		}
	}

	public final TopicsContext topics() throws RecognitionException {
		TopicsContext _localctx = new TopicsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_topics);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << Identifier) | (1L << INDENT) | (1L << LINE_COMMENT))) != 0)) {
				{
				setState(13);
				switch (_input.LA(1)) {
				case Identifier:
				case INDENT:
					{
					setState(10);
					topicLine();
					}
					break;
				case T__2:
					{
					setState(11);
					option();
					}
					break;
				case LINE_COMMENT:
					{
					setState(12);
					match(LINE_COMMENT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(18);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TopicLineContext extends ParserRuleContext {
		public Token indent;
		public Token topicName;
		public AssignContext assigned;
		public Token comment;
		public TerminalNode Identifier() { return getToken(TopicsParser.Identifier, 0); }
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public TerminalNode INDENT() { return getToken(TopicsParser.INDENT, 0); }
		public TerminalNode LINE_COMMENT() { return getToken(TopicsParser.LINE_COMMENT, 0); }
		public TopicLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_topicLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).enterTopicLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).exitTopicLine(this);
		}
	}

	public final TopicLineContext topicLine() throws RecognitionException {
		TopicLineContext _localctx = new TopicLineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_topicLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_la = _input.LA(1);
			if (_la==INDENT) {
				{
				setState(20);
				((TopicLineContext)_localctx).indent = match(INDENT);
				}
			}

			setState(23);
			((TopicLineContext)_localctx).topicName = match(Identifier);
			setState(24);
			match(T__0);
			setState(25);
			((TopicLineContext)_localctx).assigned = assign();
			setState(27);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(26);
				((TopicLineContext)_localctx).comment = match(LINE_COMMENT);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public TerminalNode HexIntegerLiteral() { return getToken(TopicsParser.HexIntegerLiteral, 0); }
		public CustomContext custom() {
			return getRuleContext(CustomContext.class,0);
		}
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).exitAssign(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			switch (_input.LA(1)) {
			case HexIntegerLiteral:
				{
				setState(29);
				match(HexIntegerLiteral);
				}
				break;
			case CustomHexIntegerLiteral:
				{
				setState(30);
				custom();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CustomContext extends ParserRuleContext {
		public TerminalNode CustomHexIntegerLiteral() { return getToken(TopicsParser.CustomHexIntegerLiteral, 0); }
		public TerminalNode HexIntegerLiteral() { return getToken(TopicsParser.HexIntegerLiteral, 0); }
		public CustomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_custom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).enterCustom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).exitCustom(this);
		}
	}

	public final CustomContext custom() throws RecognitionException {
		CustomContext _localctx = new CustomContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_custom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(CustomHexIntegerLiteral);
			setState(37);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(34);
				match(T__1);
				setState(35);
				match(T__0);
				setState(36);
				match(HexIntegerLiteral);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionContext extends ParserRuleContext {
		public Token optionName;
		public Token optionValue;
		public TerminalNode Identifier() { return getToken(TopicsParser.Identifier, 0); }
		public TerminalNode STRING() { return getToken(TopicsParser.STRING, 0); }
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TopicsListener ) ((TopicsListener)listener).exitOption(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_option);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(T__2);
			setState(40);
			((OptionContext)_localctx).optionName = match(Identifier);
			setState(41);
			match(T__0);
			setState(42);
			((OptionContext)_localctx).optionValue = match(STRING);
			setState(43);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\16\60\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3"+
		"\2\3\2\3\3\5\3\30\n\3\3\3\3\3\3\3\3\3\5\3\36\n\3\3\4\3\4\5\4\"\n\4\3\5"+
		"\3\5\3\5\3\5\5\5(\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2\2"+
		"\61\2\21\3\2\2\2\4\27\3\2\2\2\6!\3\2\2\2\b#\3\2\2\2\n)\3\2\2\2\f\20\5"+
		"\4\3\2\r\20\5\n\6\2\16\20\7\r\2\2\17\f\3\2\2\2\17\r\3\2\2\2\17\16\3\2"+
		"\2\2\20\23\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\24\3\2\2\2\23\21\3\2"+
		"\2\2\24\25\7\2\2\3\25\3\3\2\2\2\26\30\7\13\2\2\27\26\3\2\2\2\27\30\3\2"+
		"\2\2\30\31\3\2\2\2\31\32\7\7\2\2\32\33\7\3\2\2\33\35\5\6\4\2\34\36\7\r"+
		"\2\2\35\34\3\2\2\2\35\36\3\2\2\2\36\5\3\2\2\2\37\"\7\t\2\2 \"\5\b\5\2"+
		"!\37\3\2\2\2! \3\2\2\2\"\7\3\2\2\2#\'\7\n\2\2$%\7\4\2\2%&\7\3\2\2&(\7"+
		"\t\2\2\'$\3\2\2\2\'(\3\2\2\2(\t\3\2\2\2)*\7\5\2\2*+\7\7\2\2+,\7\3\2\2"+
		",-\7\16\2\2-.\7\6\2\2.\13\3\2\2\2\b\17\21\27\35!\'";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}