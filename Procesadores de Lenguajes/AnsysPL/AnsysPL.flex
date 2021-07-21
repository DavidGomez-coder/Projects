import java_cup.runtime.*;

%%
%cup

%xstate COMENTS

%%


	    
	    
	    
//special characters
"+"  { return new Symbol(sym.PLUS);  }
"-"  { return new Symbol(sym.MINUS); }
"/"  { return new Symbol(sym.DIV);   }
"*"  { return new Symbol(sym.MUL);   }
"**" { return new Symbol(sym.POW);   }
","  { return new Symbol(sym.COMMA); }
"="  { return new Symbol(sym.ASIG, yytext());  }
"!"  { yybegin(COMENTS); }
"("  { return new Symbol(sym.OB); }
")"  { return new Symbol(sym.CB); }


//reserved words
"*SET"    { return new Symbol (sym.SET); }
"*STATUS" { return new Symbol (sym.STATUS); }
"SIGN"	  { return new Symbol (sym.SIGN); }
"ABS"	  { return new Symbol (sym.ABS); }

"EQ"  	  { return new Symbol (sym.EQ); }
"NE"	  { return new Symbol (sym.NE); }
"LT"	  { return new Symbol (sym.LT); }
"GT"	  { return new Symbol (sym.GT); }
"LE"	  { return new Symbol (sym.LE); }
"GE"	  { return new Symbol (sym.GE); }
"ABLT"	  { return new Symbol (sym.ABLT); }
"ABGT"	  { return new Symbol (sym.ABGT); }


"*IF"     { return new Symbol (sym.IF); }
"THEN"    { return new Symbol (sym.THEN); }
"*ELSEIF" { return new Symbol (sym.ELSEIF); }
"*ELSE"   { return new Symbol (sym.ELSE); }
"*ENDIF"  { return new Symbol (sym.ENDIF); }


[-]?([0-9]+([.][0-9]*)?|[.][0-9]+)((e|E)(-)?[0-9]+)?
 { return new Symbol (sym.NUMBER, Double.parseDouble(yytext())); }

//variables declaration
[a-zA-Z0-9_]+   { return new Symbol (sym.NAME_VAR, yytext()); }



//strings (8 characters limitation) and numbers
"'"[^]*"'"$ { String text = yytext().substring(1, yytext().length()-1);
	     return new Symbol(sym.STRING, text); }
	     

[^] { }


<COMENTS> {

 \n|\r\n { yybegin(YYINITIAL); }
 [^]     {}

}
