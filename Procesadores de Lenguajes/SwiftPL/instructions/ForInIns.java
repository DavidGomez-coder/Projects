package instructions;

import java.util.*;

/*
	Esta clase se encarga de construir la sentencia ForIn
*/
public class ForInIns implements Instruction {

	private String varName;
	private Operation range;
	private List<Instruction> sentences;

	/*
	*	Constructor para bucles de la forma 
	*		for [varName] in [range] {
	*			sentences
	*		}
	*	@varName   nombre de la variable de control
	*	@range     rango que toma el parámetro varName (es de tipo array)
	*	@sentences lista de sentencias que se ejecutan en el cuerpo del bucle
	*/
	public ForInIns (String varName, Operation range, List<Instruction> sentences){
		this.varName = varName;
		this.range = range;
		this.sentences = sentences;
	}

	private Operation i1;
	private Operation i2;
	/*
	*	Constructor para bucles de la forma
	*		for [varName] in [i1]...[i2] {
	*			sentences
	*		}
	*	@varName   nombre de la variable de control
	*	@i1	       límite inferior del rango
	*	@i2		   límite superior del rango
	*	@sentences lista de sentencias que se ejecutan en el cuerpo del bucle
	*/
	public ForInIns (String varName, Operation i1, Operation i2, List<Instruction> sentences){
		this.varName = varName;
		this.sentences = sentences;
		this.i1 = i1;
		this.i2 = i2;
	}

	@Override
	public Object run (TS symbolTable){
		TS localSymbolTable = new TS();
		localSymbolTable.addAll(symbolTable.getVariables());

		List l_range;

		if (range == null){ //no inicializado (dado por la expresion [i1]...[i2])
			Integer l_inf = (Integer)i1.run(symbolTable);
			Integer l_sup = (Integer)i2.run(symbolTable);
			if (l_inf>l_sup){
				Err err = new Err("error (limites no declarados correctamente)");
				err.run(localSymbolTable);
				return err;
			}
			l_range = new ArrayList<>();
			for (int i = l_inf; i<=l_sup;i++){
				l_range.add(i);
			}
		}else{
			l_range = (List)range.run(symbolTable);
		}

		if (!this.varName.equals(Global.NO_FOR_VARIABLE_DECLARE)){
			Var index = new Var(varName, Global.VARIA, null);
			localSymbolTable.addVar(index);
		}
		for (Object o : l_range){
			//actualizamos indice del bucle
			if (!this.varName.equals(Global.NO_FOR_VARIABLE_DECLARE)){
				localSymbolTable.updateVar(varName, o);
			}
			for (Instruction in : sentences){
				if (in!=null){
					in.run(localSymbolTable);
				}
			}

		}

		return null;
	}
}