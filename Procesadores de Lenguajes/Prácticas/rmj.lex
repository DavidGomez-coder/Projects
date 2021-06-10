%%


%int

%xstate CAMBIAR_NOMBRE_CLASE
%xstate LEER_SYSTEM
%xstate LEE_SYSTEM
%xstate LEE_ENTRE_COMILLAS
%xstate LEE_VARIABLE
%xstate LEE_VALOR_VARIABLE
%xstate MAIN

%{
String system_out = "";
String nombre_variable = "";
String valorVariable = "";
boolean entreComillas = false;
%}

%%

"class" {
	System.out.print(yytext());
	yybegin(CAMBIAR_NOMBRE_CLASE);
	}

"public" {
	System.out.print("public");
	yybegin(MAIN);
	}

"System" {
	System.out.print(yytext());
	yybegin(LEER_SYSTEM);
	}

"String" {
	yybegin(LEE_VARIABLE); 
	}

[a-zA-Z_][a-zA-Z0-9_]* {
	if (!TablaSimbolos.get(yytext()).equals("")) {
		yybegin(LEE_VARIABLE);
	}
}

.|\n {System.out.print(yytext()); }


<LEE_VARIABLE> {

	"String" {}
	[a-zA-Z_][a-zA-Z0-9_]* {nombre_variable = yytext();}
	"=" {yybegin(LEE_VALOR_VARIABLE);}
	.|\n {}

}

<LEE_VALOR_VARIABLE> {

	"=" {
		valorVariable+=yytext();
	}

	";" {if (entreComillas) {
			valorVariable+=";";
		}else {
			TablaSimbolos.put(nombre_variable, valorVariable);  yybegin(YYINITIAL); valorVariable = "";
		}
	    }
		
	\" { if (entreComillas) {entreComillas = false; } else {entreComillas = true; }}
	
	\+ {	if (entreComillas) {
		  valorVariable+="+";
		}
	}
	
	[a-zA-Z_][a-zA-Z0-9_]*[^\ \;\"\\\+]? {
		if (!TablaSimbolos.get(yytext()).equals("")){
		 valorVariable+=TablaSimbolos.get(yytext());
		}else{
		 valorVariable+=yytext();
		}
	}
		

	\\[^] {valorVariable+=yytext(); }	
	

	


	.|\n {valorVariable+=yytext();}
}


<LEER_SYSTEM> {
	"System.out.println"[\ ]*"("|"System.out.print"[\ ]*"(" { System.out.print(yytext());}
		
	. { System.out.print(yytext()); 
	yybegin(LEE_SYSTEM);
	}
	
	
}

<LEE_ENTRE_COMILLAS> {
	\" {yybegin(LEE_SYSTEM); }

	\\[^] {system_out+=yytext(); }
	\  {system_out+=yytext(); }
	[^] {system_out+=yytext(); }


}

<LEE_SYSTEM> {
	
	"out.println"[\ ]*"("|"out.print"[\ ]*"(" {System.out.print(yytext());}
		
	\" {yybegin(LEE_ENTRE_COMILLAS);}
	

	
	\+ {}
	
	")" {
		if (valorVariable.equals("")){
			system_out = "\""+system_out+"\"";  
		}else{
			system_out = "\""+valorVariable+"\"";
		}
		System.out.print(system_out+")");
		system_out = "";
		yybegin(YYINITIAL); 
	}

	[a-zA-Z_][a-zA-Z0-9_]* { 
		if (!TablaSimbolos.get(yytext()).equals("")){
			system_out+=TablaSimbolos.get(yytext());
		}else{
			system_out+=yytext();
		}
	}
		

	. {}
	
}

<MAIN> {
	"{" {System.out.print("{"); yybegin(YYINITIAL); }
	.|\n {System.out.print(yytext()); }

}


<CAMBIAR_NOMBRE_CLASE> {
	"class" { }
	
	 [a-zA-Z_][a-zA-Z0-9_]* {
				String nombreClase = yytext();
				System.out.print(nombreClase+"_rmj"); 
				}
	\{  {
		System.out.print("{");
		yybegin(YYINITIAL);
	    }
	
	.|\n {System.out.print(yytext()); }
	

}

