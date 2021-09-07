package instructions;

import java.util.*;

public class Array implements Instruction {

	private List list;
	private String arrayType;

	public Array (List list){
		this.list = list;
	}

	public List getList(){
		return this.list;
	}

	@Override
	public Object run (TS symbolTable){
		List l = new ArrayList<>();
		for (Object o : list){
			Operation op = (Operation)o;
			l.add(op.run(symbolTable));
		}
		return l;
	}
}