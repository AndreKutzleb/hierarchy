// Generated from Topics.g4 by ANTLR 4.5.1

package de.andre_kutzleb.hierarchy.builder.parser.antlr4;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TopicsLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, Identifier=5, DecimalIntegerLiteral=6, 
		HexIntegerLiteral=7, CustomHexIntegerLiteral=8, INDENT=9, WS=10, LINE_COMMENT=11, 
		STRING=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "Identifier", "Letter", "LetterOrDigit", 
		"DecimalIntegerLiteral", "HexIntegerLiteral", "CustomHexIntegerLiteral", 
		"DecimalNumeral", "HexNumeral", "CustomHexNumeral", "Digits", "Digit", 
		"NonZeroDigit", "HexDigits", "HexDigit", "CustomHexDigits", "CustomHexDigit", 
		"INDENT", "WS", "LINE_COMMENT", "STRING"
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


	public TopicsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Topics.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16\u00a4\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\3\6\3\6\7\6I\n\6\f\6\16\6L\13\6\3\7\3\7\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\5\f\\\n\f\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\7\17h\n\17\f\17\16\17k\13\17\3\20\3\20\5\20o\n\20\3"+
		"\21\3\21\3\22\3\22\7\22u\n\22\f\22\16\22x\13\22\3\23\3\23\3\24\3\24\7"+
		"\24~\n\24\f\24\16\24\u0081\13\24\3\25\3\25\3\26\6\26\u0086\n\26\r\26\16"+
		"\26\u0087\3\27\6\27\u008b\n\27\r\27\16\27\u008c\3\27\3\27\3\30\3\30\3"+
		"\30\5\30\u0094\n\30\3\30\7\30\u0097\n\30\f\30\16\30\u009a\13\30\3\31\3"+
		"\31\7\31\u009e\n\31\f\31\16\31\u00a1\13\31\3\31\3\31\2\2\32\3\3\5\4\7"+
		"\5\t\6\13\7\r\2\17\2\21\b\23\t\25\n\27\2\31\2\33\2\35\2\37\2!\2#\2%\2"+
		"\'\2)\2+\13-\f/\r\61\16\3\2\n\5\2C\\aac|\6\2\62;C\\aac|\4\2ZZzz\3\2\63"+
		";\5\2\62;CHch\5\2\13\f\17\17\"\"\4\2\f\f\17\17\3\2$$\u00a2\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\3\63"+
		"\3\2\2\2\5\65\3\2\2\2\7=\3\2\2\2\tD\3\2\2\2\13F\3\2\2\2\rM\3\2\2\2\17"+
		"O\3\2\2\2\21Q\3\2\2\2\23S\3\2\2\2\25U\3\2\2\2\27[\3\2\2\2\31]\3\2\2\2"+
		"\33a\3\2\2\2\35e\3\2\2\2\37n\3\2\2\2!p\3\2\2\2#r\3\2\2\2%y\3\2\2\2\'{"+
		"\3\2\2\2)\u0082\3\2\2\2+\u0085\3\2\2\2-\u008a\3\2\2\2/\u0093\3\2\2\2\61"+
		"\u009b\3\2\2\2\63\64\7?\2\2\64\4\3\2\2\2\65\66\7f\2\2\66\67\7g\2\2\67"+
		"8\7h\2\289\7c\2\29:\7w\2\2:;\7n\2\2;<\7v\2\2<\6\3\2\2\2=>\7q\2\2>?\7r"+
		"\2\2?@\7v\2\2@A\7k\2\2AB\7q\2\2BC\7p\2\2C\b\3\2\2\2DE\7=\2\2E\n\3\2\2"+
		"\2FJ\5\r\7\2GI\5\17\b\2HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2K\f\3\2"+
		"\2\2LJ\3\2\2\2MN\t\2\2\2N\16\3\2\2\2OP\t\3\2\2P\20\3\2\2\2QR\5\27\f\2"+
		"R\22\3\2\2\2ST\5\31\r\2T\24\3\2\2\2UV\5\33\16\2V\26\3\2\2\2W\\\7\62\2"+
		"\2XY\5!\21\2YZ\5\35\17\2Z\\\3\2\2\2[W\3\2\2\2[X\3\2\2\2\\\30\3\2\2\2]"+
		"^\7\62\2\2^_\t\4\2\2_`\5#\22\2`\32\3\2\2\2ab\7\62\2\2bc\t\4\2\2cd\5\'"+
		"\24\2d\34\3\2\2\2ei\5\37\20\2fh\5\37\20\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2"+
		"\2ij\3\2\2\2j\36\3\2\2\2ki\3\2\2\2lo\7\62\2\2mo\5!\21\2nl\3\2\2\2nm\3"+
		"\2\2\2o \3\2\2\2pq\t\5\2\2q\"\3\2\2\2rv\5%\23\2su\5%\23\2ts\3\2\2\2ux"+
		"\3\2\2\2vt\3\2\2\2vw\3\2\2\2w$\3\2\2\2xv\3\2\2\2yz\t\6\2\2z&\3\2\2\2{"+
		"\177\5)\25\2|~\5)\25\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080"+
		"\3\2\2\2\u0080(\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\7A\2\2\u0083*\3"+
		"\2\2\2\u0084\u0086\7/\2\2\u0085\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088,\3\2\2\2\u0089\u008b\t\7\2\2"+
		"\u008a\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d"+
		"\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\b\27\2\2\u008f.\3\2\2\2\u0090"+
		"\u0091\7\61\2\2\u0091\u0094\7\61\2\2\u0092\u0094\7%\2\2\u0093\u0090\3"+
		"\2\2\2\u0093\u0092\3\2\2\2\u0094\u0098\3\2\2\2\u0095\u0097\n\b\2\2\u0096"+
		"\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2"+
		"\2\2\u0099\60\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009f\7$\2\2\u009c\u009e"+
		"\n\t\2\2\u009d\u009c\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a3\7$"+
		"\2\2\u00a3\62\3\2\2\2\16\2J[inv\177\u0087\u008c\u0093\u0098\u009f\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}