package instructions;
/**
*	Esta clase es utilizada para el manejo de los errores
*/
public class Err implements Instruction{

	private String msg;

	public Err (String msg){
		this.msg = msg;
	}

	public void report_error (String err, Object obj){
		System.out.println(err);
		System.exit(0);
	}

	@Override
	public Object run(TS symbolTable){
		report_error(this.msg, null);
		return null;
	}
}