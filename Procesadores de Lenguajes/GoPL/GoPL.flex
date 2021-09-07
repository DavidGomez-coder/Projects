import java_cup.runtime.*;
import instructions.*;

%%

%cup
//%debug

%xstate STRING_STATE

lineTerm = [\r\n]
inputChar = [^\r\n]

%{
   static String devString = "";
%};

EOLComment = "//"{inputChar}*{lineTerm}?
traditionalComment = "/*"{EOLComment}*[^*]~"*/" | "/*" "*"+ "/"
comments = {traditionalComment}|{EOLComment}

%%



//palabras reservadas
"package"   { return new Symbol (sym.PACKAGE); }
"main"      { return new Symbol (sym.MAIN); }
"import"    { return new Symbol (sym.IMPORT); }
"func"      { return new Symbol (sym.FUNC); }
"\"fmt\""   { return new Symbol (sym.FMT_PACKAGE); }

"fmt.Print"   { return new Symbol (sym.FMT_PRINT); }
"fmt.Println" { return new Symbol (sym.FMT_PRINTLN); }
"fmt.Printf"  { return new Symbol (sym.FMT_PRINTF); }

"int"       { return new Symbol (sym.INT_TYPE);}
"bool"      { return new Symbol (sym.BOOL_TYPE);}
"string"    { return new Symbol (sym.STRING_TYPE);}

"false"     { return new Symbol (sym.FALSE); }
"true"      { return new Symbol (sym.TRUE); }

"var"       { return new Symbol (sym.VAR); }
"const"     { return new Symbol (sym.CONST); }

"for"       { return new Symbol (sym.FOR); }

"\""    { devString = ""; yybegin (STRING_STATE); }

//operadores
"+"     {return new Symbol (sym.PLUS);  }
"-"     {return new Symbol (sym.MINUS); }
"*"     {return new Symbol (sym.MUL);   }
"/"     {return new Symbol (sym.DIV);   }

"++"    { return new Symbol (sym.INC); }
"--"    { return new Symbol (sym.DEC); }

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
","     {return new Symbol (sym.COMMA); }
";"     {return new Symbol (sym.PYC); }
"("     {return new Symbol (sym.OB); }
")"     {return new Symbol (sym.CB); }
"{"     {return new Symbol (sym.OCC); }
"}"     {return new Symbol (sym.CCC); }
"["     {return new Symbol (sym.OK); }
"]"     {return new Symbol (sym.CK); }


//operadores de asignación
"="		{ return new Symbol (sym.ASIG);    }
":="    { return new Symbol (sym.ASIG2);   }


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

<STRING_STATE> {
    "\""    { yybegin (YYINITIAL); return new Symbol(sym.STRING, devString); }
    [^] { devString+=yytext(); }   
}






