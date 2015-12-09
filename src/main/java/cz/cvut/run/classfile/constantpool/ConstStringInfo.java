package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstStringInfo extends ConstantPoolElement {
	private int stringIndex;
	
	public ConstStringInfo(byte[] stringIndex){
		this.tag = Constants.TAG_STRING;
		this.stringIndex = Utils.parseByteToInt(stringIndex);
	}
	
	public int getStringIndex(){
		return this.stringIndex;
	}
}
