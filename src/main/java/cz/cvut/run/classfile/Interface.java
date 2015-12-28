package cz.cvut.run.classfile;

public class Interface extends ClassElement {
	private byte[] bytes;
	public Interface(byte[] bytes){
		this.bytes = bytes;
	}
	
	public byte[] getBytes(){
		return this.bytes;
	}

}
