package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstIntegerInfo extends ConstantPoolElement {
	private int bytes;
	
	public ConstIntegerInfo(byte[] bytes){
		this.tag = Constants.TAG_INTEGER;
		this.bytes = Utils.parseByteToInt(bytes);
	}
	
	public int getBytes(){
		return this.bytes;
	}
}
