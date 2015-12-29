package cz.cvut.run.stack;

public class ArrayReference extends Reference {

	public ArrayReference(Object o) {
		super(o);
	}

	public Object getValue(int index){
		boolean[] val = (boolean[]) this.value;
		return val[index];
	}
	
	public void setValue(int index, Object value){
		boolean[] val = (boolean[]) this.value;
		val[index] = (Boolean) value;
	}
	
	public IntValue getLength(){
		boolean[] val = (boolean[]) this.value;
		return new IntValue(val.length);
	}
}
