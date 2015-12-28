package cz.cvut.run.classfile;

import java.util.ArrayList;

import cz.cvut.run.utils.Utils;

public class Field extends ClassElement {
	private byte[] accessFlags;
	private int nameIndex;
	private int descriptorIndex;
	private int attributesCount;
	private ArrayList<Attribute> attributesInfo;
	



	
	public Field(byte[] access_flags, byte[] name_index, byte[] descriptor_index, byte[] attributes_count){
		this.accessFlags = access_flags;
		this.nameIndex = Utils.parseByteToInt(name_index);
		this.descriptorIndex = Utils.parseByteToInt(descriptor_index);
		this.attributesCount = Utils.parseByteToInt(attributes_count);
	}
	
	
	
	
	public byte[] getAccessFlags() {
		return accessFlags;
	}

	public void setAccessFlags(byte[] accessFlags) {
		this.accessFlags = accessFlags;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	public void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	public int getAttributesCount() {
		return attributesCount;
	}

	public void setAttributesCount(int attributesCount) {
		this.attributesCount = attributesCount;
	}

	public ArrayList<Attribute> getAttributesInfo() {
		return attributesInfo;
	}

	public void setAttributesInfo(ArrayList<Attribute> attributesInfo) {
		this.attributesInfo = attributesInfo;
	}
}
