package cz.cvut.run.stack;

import java.util.HashMap;

public class ObjectReference extends Reference {
	private String className = "";
	HashMap<String, Object> fields = new HashMap<String, Object>();
	public ObjectReference(Object o, String className) {
		super(o);
		this.className = className;
		// TODO Auto-generated constructor stub
	}

	public void setField(String name, StackElement value) {
		fields.put(name, value);
		
	}
	public Object getField(String name) {
		return fields.get(name);
	}
	public String getClassName(){
		return this.className;
	}

}
