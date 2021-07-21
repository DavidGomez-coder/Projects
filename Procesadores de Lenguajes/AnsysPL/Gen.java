import java.util.*;

public class Gen {

	//regex to validate params name
	private static final String name_Vars_regex     = "[a-zA-Z0-9_]+";
	private static final String start_with_number   = "[0-9]"+name_Vars_regex;
	private static final String warning_vars_regex  = "_"+name_Vars_regex;
	private static final String warning_vars_regex2 = "ARG[1-9]";
	private static final String warning_vars_regex3 = "AR[1-9][0-9]";
	
	//conditions
	public static final String ABGT = "ABGT";
	public static final String ABLT = "ABLT";
	public static final String LE   = "LE";
	public static final String GE   = "GE";
	public static final String GT   = "GT";
	public static final String LT   = "LT";
	public static final String NE   = "NE";
	public static final String EQ   = "EQ";
	
	
	//method to validate if a name of a parameter is valid or not
	public static boolean nameVarIsNotValid (String str){
		return !str.matches(name_Vars_regex) || str.length() > 32 || str.matches(start_with_number); 
	}
	
	//warning advise of parameter's name
	public static boolean nameVarIsWarning (String str){
		return str.matches(warning_vars_regex) || str.matches(warning_vars_regex2) 
		       || str.matches(warning_vars_regex3);
	}
	
	//print method
	public static String print(String var){
		String res = "";
		res += "PARAMETER STATUS\n";
		res += "(" + TS.variables.size() + " PARAMETERS DEFINED)\n";
		res +=  "\tNAME \t VALUE \t TYPE\n";
		if (var == null){ //print all variables
			for (String str: TS.variables.keySet()){
				res+="\t" + str + "\t" + TS.variables.get(str).getValueVar() + "\t" + 					TS.variables.get(str).getTypeVar() + "\n";
			}
		}else{ //print only defined variables
			res+=var + "\t" + TS.variables.get(var).getValueVar() + "\t" + 				     TS.variables.get(var).getTypeVar() + "\n";		
		}
		System.out.println(res);
		return res;
	}
	
	//condition value
	public static boolean resCondition (Var v1, String c, Var v2){
		Double value1 = Double.parseDouble(v1.getValueVar());
		Double value2 = Double.parseDouble(v2.getValueVar());
		boolean res = false;
		
		if (c == Gen.ABGT){
			return Math.abs(value1) > Math.abs(value2);
		}else if (c == Gen.ABLT){
			return Math.abs(value1) < Math.abs(value2);
		}else if (c == Gen.LE){
			return value1 <= value2;
		}else if (c == Gen.GE){
			return value1 >= value2;
		}else if (c == Gen.GT){
			return value1 > value2;
		}else if (c == Gen.LT){	
			return value1 < value2;
		}else if (c == Gen.NE){
			return value1 != value2;
		}else if (c == Gen.EQ){
			return value1 == value2;
		}
		
		return res;
	}
}
