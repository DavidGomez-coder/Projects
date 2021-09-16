package instructions;

import java.util.*;

/**
 * Esta clase contiene la tabla de simbolos (conjunto de variables, funciones) que se utilizan para 
 * la ejecucion/compilación de un programa. Consta de una lista de variables con diferentes metodos
 * para operar sobre ella. 
*/
public class TS {

    private List<Var> variables;
    public Map<String,Function> functions;

    public TS (){
        this.variables = new ArrayList<>();
        this.functions = new HashMap<>();
    }

    public List<Var> getVariables (){
        return this.variables;
    }

    public void addVar(Var v){
        variables.add(v);
    }

    public boolean isDefined (Var v){
        boolean ok = false;
        int i = variables.size()-1;
        while (i>=0 && !ok){
            Var var = variables.get(i);
            if (var.getName().equals(v.getName())){
                ok = true;
            }
            i--;
        }

        return ok;
    }

    public Var getVar(String varName){
        Var res = null;
        int i=variables.size()-1;
        boolean find = false;
        while (i>=0 && !find){
            Var v = variables.get(i);
            if (v.getName().equals(varName)){
                res = v;
                find = true;
            }
            i--;
        }

        return res;
    }

    public void updateVar (String varName, Object value){
        int i = variables.size()-1;
        boolean find = false;
        while (i>=0 && !find){
            Var v = variables.get(i);
            if (v.getName().equals(varName)){
                v.setValue(value);
                v.setType(Global.typeOf(value));
                find = true;
            }
            i--;
        }
    }

    
    public void addAll (List<Var> v, Map functions){
        this.variables.addAll(v);
        this.functions.putAll(functions);
    }

    public void showVariables(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n---------- TABLA DE SÍMBOLOS ----------");
        sb.append("\nVARIABLES{\n");
        for (Var v : variables){
            sb.append("\t" + v);
            sb.append(",\n");
        }
        sb.append("}\n");

        sb.append("\nFUNCIONES{\n");
        for (String v : functions.keySet()){
            sb.append("\t" + v + " : " + functions.get(v));
            sb.append(",\n");

        }
        sb.append("}\n");
        System.out.println(sb.toString());
    }
}