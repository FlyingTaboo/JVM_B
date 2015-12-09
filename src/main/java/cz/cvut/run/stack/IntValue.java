package cz.cvut.run.stack;

public class IntValue extends Value {
	
	public IntValue(Integer i) {
		super(i);
	}
	
	public int getIntValue(){
		return ((Integer) this.value).intValue();
	}

	@Override
	public String toString(){
		return "IntValue: " +getIntValue();
	}

}
