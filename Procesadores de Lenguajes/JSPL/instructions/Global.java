package instructions;

import java.util.*;
/**
* Clase utilizada solo para mantener las diferentes variables globales que serán 
* utilizadas durante la ejecucion/compilacion de los programas.
* Todos los valores son estaticos y finales, por lo que no se podran cambiar su valor
* durante la ejecucion
*/
public class Global {

	//operadores 
	public static final String VALUE  = "Value";
	public static final String PLUS   = "PLUS";
	public static final String MINUS  = "MINUS";
	public static final String MUL    = "MUL";
	public static final String DIV    = "DIV";
	public static final String UMINUS = "UMINUS";
	public static final String MOD    = "MOD";

	//operadores de comparación
	public static final String LT     = "LT";
	public static final String LE 	  = "LE";
	public static final String GT     = "GT";
	public static final String GE     = "GE";
	public static final String EQ     = "EQ";
	public static final String NE     = "NE";

	//operadores booleanos
	public static final String NOT    = "NOT";
	public static final String AND    = "AND";
	public static final String OR     = "OR";

	//tipos de datos
	public static final String INTEGER   = "Int";
	public static final String DOUBLE    = "Double";
	public static final String OPERATION = "Operation";
	public static final String BOOL      = "Bool";
	public static final String ARRAY     = "Array";
	public static final String STRING    = "String";
	public static final String FUNCTION  = "Function";
	public static final String RETURN    = "Return";

	//datos booleanos
	public static final String TRUE      = "true";
	public static final String FALSE	 = "false";

	//variable utilizada para la obtención de variables
	public static final String VARIABLE     = "VARIABLE";
	public static final String ARRAY_ACCESS = "ARRAY_ACCESS";

	//tipos de variables
	public static final String CONST   = "CONST";
	public static final String VARIA   = "VARIA";

	//variables no definidas
	public static final String UNDEFINED = "undefined";

	//variables controladoras de bucles
	public static final String INDEX_VAR = "INDEX";
	public static final String NO_FOR_VARIABLE_DECLARE = "NO_FOR_VARIABLE_DECLARE";

	public static final String typeOf (Object val){
		if (val instanceof Integer){
			return INTEGER;
		}else if (val instanceof Double){
			return DOUBLE;
		}else if (val instanceof Boolean){
			return BOOL;
		}else if (val instanceof List){
			return ARRAY;
		}else if (val instanceof String){
			return STRING;
		}
		return UNDEFINED;
	}

}