package cz.cvut.run.stack;

import java.util.HashMap;

public class ObjectReference extends Reference {

	HashMap<String, Object> fields = new HashMap<String, Object>();
	public ObjectReference(Object o) {
		super(o);
		// TODO Auto-generated constructor stub
	}

	public void setField(String name, StackElement value) {
		fields.put(name, value);
		
	}
	public Object getField(String name) {
		return fields.get(name);
	}

}
