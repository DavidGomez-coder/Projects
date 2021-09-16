import java_cup.runtime.*;
import instructions.*;

%%

%cup
%debug
%xstate STRING_VAL


lineTerm = [\r\n]
inputChar = [^\r\n]

%{
   static String string_val = "";
%};

EOLComment = "//"{inputChar}*{lineTerm}?
traditionalComment = "/**"{EOLComment}*[^*]~"*/" | "/**" "*"+ "/"
comments = {traditionalComment}|{EOLComment}

%%



//palabras reservadas
"console.log"   {return new Symbol (sym.PRINT); }
"var"           {return new Symbol (sym.VAR);   }
"const"         {return new Symbol (sym.CONST);   }
"true"          {return new Symbol (sym.TRUE);  }
"false"         {return new Symbol (sym.FALSE); }

"if"            {return new Symbol (sym.IF); }
"else"          {return new Symbol (sym.ELSE); }

"do"            {return new Symbol (sym.DO);    }
"while"         {return new Symbol (sym.WHILE); }

"for"           {return new Symbol (sym.FOR); }
"in"            {return new Symbol (sym.IN);  }

"length"        {return new Symbol (sym.LENGTH); }

"func"          {return new Symbol (sym.FUNC); }
"return"        {return new Symbol (sym.RETURN); }

//operadores
"+"     {return new Symbol (sym.PLUS);  }
"-"     {return new Symbol (sym.MINUS); }
"*"     {return new Symbol (sym.MUL);   }
"/"     {return new Symbol (sym.DIV);   }
"%"     {return new Symbol (sym.MOD);   }

"++"    {return new Symbol (sym.INC); }
"--"    {return new Symbol (sym.DEC); }

//operadores booleanos
"!"     {return new Symbol (sym.NOT);   }
"||"    {return new Symbol (sym.OR);    }
"&&"    {return new Symbol (sym.AND);   }


//operadores de asignación
"="		{ return new Symbol (sym.ASIG);    }

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
":"     {return new Symbol (sym.DP);  }
"("     {return new Symbol (sym.OB); }
")"     {return new Symbol (sym.CB); }
"{"     {return new Symbol (sym.OCC); }
"}"     {return new Symbol (sym.CCC); }
"["     {return new Symbol (sym.OK); }
"]"     {return new Symbol (sym.CK); }




//variables
[a-zA-Z][a-zA-Z0-9_]* { return new Symbol (sym.VAR_NAME, yytext()); }



//numbers
0|[1-9][0-9]*   { return new Symbol (sym.INTEGER, yytext()); }

([0-9]+([.][0-9]*)?|[.][0-9]+)((e|E)(-)?[0-9]+)?
 { return new Symbol (sym.DOUBLE, yytext()); }



//fin de línea
//;		{ return new Symbol (sym.EOL); } //END OF LINE
[\n\r]+		{ return new Symbol (sym.SK_LINE); }

//otros
"\'"    { string_val = ""; yybegin(STRING_VAL); }

//comentarios
{comments}  {}	

[^]	{}

<STRING_VAL> {
    "\'"    { yybegin(YYINITIAL); return new Symbol (sym.STRING, string_val); }
    [^]     { string_val+=yytext(); }
}







