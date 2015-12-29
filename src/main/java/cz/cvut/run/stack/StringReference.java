package cz.cvut.run.stack;

public class StringReference extends Reference {

	public StringReference(Object o) {
		super(o);
		// TODO Auto-generated constructor stub
	}
	
	public Character charAt(int position){
		return value.toString().charAt(position);
	}

}
