package cz.cvut.run.classfile;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cz.cvut.run.attributes.CodeAttribute;
import cz.cvut.run.utils.Utils;

public class Method extends ClassElement {
	private static final Logger log = Logger.getLogger(ClassElement.class);
	private byte[] access_flags;
	private int name_index;
	private int descriptor_index;
	private int attributes_count;
	private ArrayList<Attribute> attributes_info;
	
	public Method(byte[] access_flags, byte[] name_index, byte[] descriptor_index, byte[] attributes_count){
		this.access_flags = access_flags;
		this.name_index = Utils.parseByteToInt(name_index);
		this.descriptor_index = Utils.parseByteToInt(descriptor_index);
		this.attributes_count = Utils.parseByteToInt(attributes_count);
	}

	
	public byte[] getAccess_flags() {
		return access_flags;
	}

	public void setAccess_flags(byte[] access_flags) {
		this.access_flags = access_flags;
	}

	public int getName_index() {
		return name_index;
	}

	public void setName_index(int name_index) {
		this.name_index = name_index;
	}

	public int getDescriptor_index() {
		return descriptor_index;
	}

	public void setDescriptor_index(int descriptor_index) {
		this.descriptor_index = descriptor_index;
	}

	public int getAttributesCount() {
		return attributes_count;
	}

	public void setAttributesCount(int attributes_count) {
		this.attributes_count = attributes_count;
	}

	public ArrayList<Attribute> getAttributes_info() {
		return attributes_info;
	}

	public void setAttributes_info(ArrayList<Attribute> attributes_info) {
		this.attributes_info = attributes_info;
	}
	
	public ArrayList<Byte> getCode(int index) throws Exception{
		return getCodeAttribute(index).getCode();
	}
	
	public CodeAttribute getCodeAttribute(int index) throws Exception{
		for(Attribute attr: attributes_info){
			int nameIndex = attr.getAttributeNameIndex();
			
			if(nameIndex == index+1){
				CodeAttribute ca = (CodeAttribute) attr;
				return ca;
			}
		}
		log.error("Method doesnt have CODE attribute!");
		throw new Exception("Method doesnt have CODE attribute!");
	}


	public boolean isNative() {
		return (access_flags[0] & 0x01) == 0x01;
	}
	
	

}
