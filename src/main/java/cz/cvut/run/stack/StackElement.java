package cz.cvut.run.stack;

public class StackElement {
	Object value;
	
	public StackElement(Object o){
		this.value = o;
	}
	
	public Object getValue(){
		return this.value;
	}
}
