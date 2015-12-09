package cz.cvut.run.classfile;

import cz.cvut.run.utils.Utils;

public class Attribute extends ClassElement {
	private int attributeNameIndex;
	private int attributeLength;
	private byte[] attributeInfo;
	
	
	public Attribute(byte[] attributeNameIndex, byte[] attributeLength){
		this.attributeNameIndex = Utils.parseByteToInt(attributeNameIndex);
		this.attributeLength = Utils.parseByteToInt(attributeLength);
	}
	
	public int getAttributeNameIndex() {
		return attributeNameIndex;
	}


	public void setAttributeNameIndex(int attributeNameIndex) {
		this.attributeNameIndex = attributeNameIndex;
	}


	public int getAttributeLength() {
		return attributeLength;
	}


	public void setAttributeLength(int attributeLength) {
		this.attributeLength = attributeLength;
	}


	public byte[] getAttributeInfo() {
		return attributeInfo;
	}


	public void setAttributeInfo(byte[] attributeInfo) {
		this.attributeInfo = attributeInfo;
	}

}
