%%
%{
String valorVariable=""; //inicializamos las cadenas a vacia
String nombreVariable="";
%}


%xstate VALOR

%%
<YYINITIAL> {

[a-zA-Z_][a-zA-Z0-9_]*/= {
			nombreVariable = yytext();
			yybegin(VALOR); //iniciamos otro estado para leer el valor de la variable
			}


[a-zA-Z_][a-zA-Z0-9_]* {
                        System.out.println(TablaSimbolos.get(yytext()));
                        }


.|\n  {
     System.out.println(yytext());
}

}


<VALOR> {
 \= {}

 [^\t\r\n\ |;] {
	valorVariable+=yytext();
	}
	
 [\t\r\n|;\ ]+ {
	//System.out.println("<"+nombreVariable+","+valorVariable+">");
	TablaSimbolos.put(nombreVariable,valorVariable);
	valorVariable=""; //reiniciamos el valor de la variable
	nombreVariable=""; //reiniciamos el valor del nombre de la variable
	yybegin(YYINITIAL); //volvemos a leer nombres de variables
	}

}
