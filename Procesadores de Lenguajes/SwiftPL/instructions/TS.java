package instructions;

import java.util.*;

/**
 * Esta clase contiene la tabla de símbolos (conjunto de variables) que se utilizan para 
 * la ejecución/compilación de un programa. Consta de una lista de variables con diferentes métodos
 * para operar sobre ella. 
*/
public class TS {

    private List<Var> variables;

    public TS (){
        this.variables = new ArrayList<>();
    }

    public List<Var> getVariables (){
        return this.variables;
    }

    /*  Añadir nueva variable a la tabla de símbolos */
    public void addVar(Var v){
        variables.add(v);
    }

    /*   Indica si una variable está o no definida */
    public boolean isDefined (Var v){
        boolean ok = false;
        int i = 0;
        while (i<variables.size() && !ok){
            Var var = variables.get(i);
            if (var.getName().equals(v.getName())){
                ok = true;
            }
            i++;
        }

        return ok;
    }

    /*  Devuelve una variable dado su nombre. En caso de que no exista, retornamos null */
    public Var getVar(String varName){
        Var res = null;
        int i=0;
        boolean find = false;
        while (i<variables.size() && !find){
            Var v = variables.get(i);
            if (v.getName().equals(varName)){
                res = v;
                find = true;
            }
            i++;
        }

        return res;
    }

    /*   Modifica el valor de una variable en la tabla de símbolos */
    public void updateVar (String varName, Object value){
        int i = 0;
        boolean find = false;
        while (i<variables.size() && !find){
            Var v = variables.get(i);
            if (v.getName().equals(varName)){
                v.setValue(value);
                find = true;
            }
            i++;
        }
    }

    /*  Añade una lista de variables a esta tabla de símbolos. Este método
        se utiliza para las tablas de símbolos locales (bucles, ifs, ...) */
    public void addAll (List<Var> v){
        this.variables.addAll(v);
    }

    /*  Muestra por pantalla las variables almacenadas  */
    public void showVariables(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n{\n");
        for (Var v : variables){
            sb.append(v);
            sb.append(",\n");
        }
        sb.append("}\n");
        System.out.println(sb.toString());
    }
}