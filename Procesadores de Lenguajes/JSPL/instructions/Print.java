package instructions;

/**
 *	Esta clase implementa la instruccion Print. Se encarga de mostrar una instrucción 
 * (una operacion, un string, el valor de una variable, un array, ...) El resultado se mostrara
 * dependiendo de la implementación del metodo @toString de cada una de las instrucciones que se 
 * invoquen
*/
public class Print implements Instruction{

	private final Instruction ins;

	public Print (Instruction ins){
		this.ins = ins;
	}

	@Override
	public Object run (TS symbolTable){
			System.out.println(ins.run(symbolTable).toString());
			return null;
	}
}