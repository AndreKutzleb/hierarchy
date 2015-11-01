grammar Topics;


@header {
package de.andre_kutzleb.hierarchy.builder.parser.antlr4;
}

topics 		: (topicLine | option | LINE_COMMENT)* EOF;
topicLine  	: indent=INDENT? topicName=Identifier '=' assigned=assign comment=LINE_COMMENT?;
assign 		: (HexIntegerLiteral | custom) ;
custom 		: CustomHexIntegerLiteral ('default' '=' HexIntegerLiteral)? ;
option      : 'option' optionName=Identifier '=' optionValue=STRING ';' ;

Identifier : Letter LetterOrDigit* ;

fragment Letter : [a-zA-Z_];
fragment LetterOrDigit : [a-zA-Z0-9_];
   
DecimalIntegerLiteral   : DecimalNumeral   ;
HexIntegerLiteral       : HexNumeral       ;
CustomHexIntegerLiteral : CustomHexNumeral ;

fragment DecimalNumeral        : '0' | NonZeroDigit Digits ;
fragment HexNumeral            : '0' [xX] HexDigits ;
fragment CustomHexNumeral      : '0' [xX] CustomHexDigits ;

fragment Digits                : Digit (Digit)* ;
fragment Digit                 : '0' | NonZeroDigit ;
fragment NonZeroDigit          : [1-9] ;

fragment HexDigits             : HexDigit HexDigit* ;
fragment HexDigit              : [0-9a-fA-F] ;

fragment CustomHexDigits       : CustomHexDigit CustomHexDigit* ;
fragment CustomHexDigit         : '?' ;

INDENT : '-'+;

WS  :  [ \t\r\n]+ -> skip
    ;

LINE_COMMENT
    :   ('//'|'#') ~[\r\n]*
    ;

STRING
    :  '"' ~["]* '"'
    ;

