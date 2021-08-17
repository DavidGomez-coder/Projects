import java_cup.runtime.*;

%%
//%debug
%cup

Int = 0|[1-9][0-9]*
Rexp = [eE][+-]?[0-9]+
Real = (0|[0-9])+\.[0-9]*{Rexp}?|[0-9]*\.(0|[0-9])+{Rexp}?|[0-9]+{Rexp}

//terminadores de línea
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

//comentarios
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent = ( [^*] | \*+ [^/*] )*
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
%%

//palabras reservadas
"int"       { return new Symbol (sym.INT); }

"print"     { return new Symbol (sym.PRINT); }
"if"        { return new Symbol (sym.IF); }
"else"      { return new Symbol (sym.ELSE); }

"for"       { return new Symbol (sym.FOR); }
"to"        { return new Symbol (sym.TO); }
"downto"    { return new Symbol (sym.DOWNTO); }
"do"        { return new Symbol (sym.DO); }
"step"      { return new Symbol (sym.STEP); }

"while"     { return new Symbol (sym.WHILE); }


//incremento y decremento
"++"    { return new Symbol (sym.INC); }
"--"    { return new Symbol (sym.DEC); }


//operadores
"+"     { return new Symbol (sym.PLUS); }
"-"     { return new Symbol (sym.MINUS); }
"*"     { return new Symbol (sym.MUL); }
"/"     { return new Symbol (sym.DIV); }
"%"     { return new Symbol (sym.MOD); }
"="     { return new Symbol (sym.ASIG); } 


//operadores de comparación
"=="    { return new Symbol (sym.EQ); }
"!="    { return new Symbol (sym.NE); }
"<"     { return new Symbol (sym.LT); }
"<="    { return new Symbol (sym.LE); }
">"     { return new Symbol (sym.GT); }
">="    { return new Symbol (sym.GE); }
"!"     { return new Symbol (sym.NOT); }
"&&"    { return new Symbol (sym.AND); }
"||"    { return new Symbol (sym.OR); }

//otros caracteres
";"     { return new Symbol (sym.PYC); }
","     { return new Symbol (sym.COMMA); }


//parátesis
"("     { return new Symbol (sym.OB); }
")"     { return new Symbol (sym.CB); }
"{"     { return new Symbol (sym.OCB); }
"}"     { return new Symbol (sym.CCB); }

//numeros

{Int} { return new Symbol(sym.INT_NUM, yytext()); }
{Real} { return new Symbol(sym.FLOAT_NUM, yytext());}

//variables
[a-zA-Z_][a-zA-Z0-9_]* { return new Symbol(sym.VAR_NAME, yytext()); }

//otros
{Comment} {}
[^] {}
