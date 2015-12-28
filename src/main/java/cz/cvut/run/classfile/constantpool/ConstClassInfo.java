package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstClassInfo extends ConstantPoolElement {
	private int nameIndex;
	public ConstClassInfo(byte[] nameIndex){
		this.tag = Constants.TAG_CLASS;
		this.nameIndex = Utils.parseByteToInt(nameIndex);
	}
	
	public int getNameIndex(){
		return this.nameIndex;
	}
}
