package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstLongInfo extends ConstantPoolElement {
	private int lowBytes;
	private int highBytes;
	
	public ConstLongInfo(byte[] lowBytes, byte[] highBytes){
		this.tag = Constants.TAG_LONG;
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
