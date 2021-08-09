import java_cup.runtime.*;
import instructions.*;

%%

%cup
%debug


lineTerm = [\r\n]
inputChar = [^\r\n]

%{
   
%};

EOLComment = "//"{inputChar}*{lineTerm}?
traditionalComment = "/*"{EOLComment}*[^*]~"*/" | "/*" "*"+ "/"
comments = {traditionalComment}|{EOLComment}

%%



//palabras reservadas
"print" {return new Symbol (sym.PRINT); }
"var"   {return new Symbol (sym.VAR);   }
"let"   {return new Symbol (sym.LET);   }
"true"  {return new Symbol (sym.TRUE);  }
"false" {return new Symbol (sym.FALSE); }
"while" {return new Symbol (sym.WHILE); }
"if"    {return new Symbol (sym.IF);    }
"else"  {return new Symbol (sym.ELSE);  }
"for"   {return new Symbol (sym.FOR);   }
"in"    {return new Symbol (sym.IN);    }


//tipos
"Double"    {return new Symbol (sym.DOUBLE_TYPE); }
"Int"       {return new Symbol (sym.INTEGER_TYPE);}
"Bool"      {return new Symbol (sym.BOOLEAN_TYPE);}

//operadores
"+"     {return new Symbol (sym.PLUS);  }
"-"     {return new Symbol (sym.MINUS); }
"*"     {return new Symbol (sym.MUL);   }
"/"     {return new Symbol (sym.DIV);   }

//operadores booleanos
"!"     {return new Symbol (sym.NOT);   }
"||"    {return new Symbol (sym.OR);    }
"&&"    {return new Symbol (sym.AND);   }

//operadores de comparacion
"<"     {return new Symbol (sym.LT); }
"<="    {return new Symbol (sym.LE); }
">"     {return new Symbol (sym.GT); }
">="    {return new Symbol (sym.GE); }
"=="    {return new Symbol (sym.EQ); }
"!="    {return new Symbol (sym.NE); }

//otros caracteres
":"     {return new Symbol (sym.DECL_TYPE); }
","     {return new Symbol (sym.COMMA); }
";"     {return new Symbol (sym.PYC); }
"("     {return new Symbol (sym.OB); }
")"     {return new Symbol (sym.CB); }
"{"     {return new Symbol (sym.OCC); }
"}"     {return new Symbol (sym.CCC); }
"["     {return new Symbol (sym.OK); }
"]"     {return new Symbol (sym.CK); }
"_"     {return new Symbol (sym.FOR_NO_VAR); }
"..."     {return new Symbol (sym.FOR_SEQ); }


//operadores de asignación
"="		{ return new Symbol (sym.ASIG2);    }
\s+"="\s+	{ return new Symbol (sym.ASIG); }


//variables
[a-zA-Z][a-zA-Z0-9_]* { return new Symbol (sym.VAR_NAME, yytext()); }



//numbers
0|[-]?[1-9][0-9]*   { return new Symbol (sym.INTEGER, yytext()); }

[-]?([0-9]+([.][0-9]*)?|[.][0-9]+)((e|E)(-)?[0-9]+)?
 { return new Symbol (sym.DOUBLE, yytext()); }



//fin de línea
;[\n\r]*		{ return new Symbol (sym.EOL); } //END OF LINE
[\n\r]+		{ return new Symbol (sym.SK_LINE); }


//comentarios
{comments}  {}	

[^]	{}







