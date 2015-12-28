package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstDoubleInfo extends ConstantPoolElement {
	private int lowBytes;
	private int highBytes;
	public ConstDoubleInfo(byte[] lowBytes, byte[]highBytes){
		this.tag = Constants.TAG_DOUBLE;
		this.lowBytes = Utils.parseByteToInt(lowBytes);
		this.highBytes = Utils.parseByteToInt(highBytes);
	}
	
	public int getLowBytes(){
		return this.lowBytes;
	}
	
	public int getHighBytes(){
		return this.highBytes;
	}
}
