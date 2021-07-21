import java.util.*;

public class TS {

		
	
	public static HashMap<String, Var> variables = new HashMap<>();
	
	
	//variables management
	public static void setVariable(String nameVar, Var v){
		variables.put(nameVar, v);
	}
	
	public static Var getVariable(String nameVar){
		return variables.get(nameVar);
	}
	
	
	
	//help methods
	public static void showTS(){
		for (String str : variables.keySet()){
			System.out.println(str + variables.get(str).toString());
		}
	}

}
