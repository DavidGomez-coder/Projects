
%%

%int

%{
StringBuffer valorVariable = new StringBuffer();
String nombreVariable = "";
%}

%xstate VALOR
%xstate VALOR_COMILLAS
%xstate VARIABLE_ENTRE_COMILLAS
%xstate ECHO_ENTRE_COMILLAS

%%

[a-zA-Z_][a-zA-Z0-9_]*/= {
			nombreVariable = yytext();
			yybegin(VALOR);
			}


[a-zA-Z_][a-zA-Z0-9_]*/=\" {
                        nombreVariable = yytext();
                        yybegin(VALOR_COMILLAS);
                        }


\$[a-zA-Z_][a-zA-Z0-9_]* {
			System.out.print(TablaSimbolos.get(yytext()));
			}
\" {
   System.out.print(yytext());
   yybegin(ECHO_ENTRE_COMILLAS);
}

[^]|\n {
	System.out.print(yytext());	
     }


<ECHO_ENTRE_COMILLAS> {

\" = {}

[a-zA-Z_][a-zA-Z0-9_]* {

	System.out.print(yytext());
}

\$[a-zA-Z_][a-zA-Z0-9_]* {
                        System.out.print(TablaSimbolos.get(yytext()));
                        }


[^]|\n { System.out.print (yytext()); }

}

<VALOR> {

\= {}

\$[a-zA-Z_][a-zA-Z0-9_]* {
	valorVariable.append(TablaSimbolos.get(yytext()));
}

\$[a-zA-Z_][a-zA-Z0-9_]*\= {
        int i = 0;
	String aux = yytext();
	String res = "";
	while (aux.charAt(i) != '='){
		res+=aux.charAt(i);
		i++;
	}
	valorVariable.append(TablaSimbolos.get(res)).append("=");
}


[^\t\r\n\ |;] {
		valorVariable.append(yytext());
	      }

\\[trn|;\ \"] {
	 valorVariable.append(yytext());
}


[\t\r\n|;\ ] {
		TablaSimbolos.put(nombreVariable,valorVariable.toString());
		valorVariable = new StringBuffer();
		nombreVariable = "";
		yybegin(YYINITIAL);
	    }

}


<VALOR_COMILLAS> {

\=\" {}

\$[a-zA-Z_][a-zA-Z0-9_]* {
        valorVariable.append(TablaSimbolos.get(yytext()));
   }



\" {
	TablaSimbolos.put(nombreVariable, valorVariable.toString());
	valorVariable = new StringBuffer();
	nombreVariable= "";
	yybegin(VALOR);
}

\\\" { valorVariable.append(yytext()); }

\\\$ { valorVariable.append(yytext()); }

[^\"=]  {
	valorVariable.append(yytext());
	}

}

