package cz.cvut.run.classfile.constantpool;

import java.nio.charset.StandardCharsets;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstUtf8Info extends ConstantPoolElement {
	private int length;
	private byte[] bytes;
	
	public ConstUtf8Info(byte[] length, byte[] bytes){
		this.tag = Constants.TAG_UTF8;
		this.length = Utils.parseByteToInt(length);
		this.bytes = bytes;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public byte[] getBytes(){
		return this.bytes;
	}
	
	@Override
	public String toString(){
		String str = new String(bytes, StandardCharsets.UTF_8);
		return str;
	}
	
}
