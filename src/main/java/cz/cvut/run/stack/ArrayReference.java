package cz.cvut.run.stack;

public class ArrayReference extends Reference {

	public ArrayReference(Object o) {
		super(o);
	}

	public Object getValue(int index){
		if(this.value instanceof boolean[]){
			boolean[] val = (boolean[]) this.value;
			return val[index];
		}else if (this.value instanceof char[]){
			char[] val = (char[]) this.value;
			return val[index];
		}
		return null;
	}
	
	public void setValue(int index, Object value){
		if(this.value instanceof boolean[]){
			boolean[] val = (boolean[]) this.value;
			val[index] = (boolean) value;
		}else if (this.value instanceof char[]){
			char[] val = (char[]) this.value;
			val[index] = (char) value;
		}
	}
	
	public IntValue getLength(){
		if(this.value instanceof boolean[]){
			boolean[] val = (boolean[]) this.value;
			return new IntValue(val.length);	
		}else if (this.value instanceof char[]){
			char[] val = (char[]) this.value;
			return new IntValue(val.length);
		}
		return new IntValue(-1);
		
	}
}
