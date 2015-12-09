package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstFieldRefInfo extends ConstantPoolElement {
	private int classIndex;
	private int nameAndTypeIndex;
	
	public ConstFieldRefInfo(byte[] classIndex, byte[] nameAndTypeIndex){
		this.tag = Constants.TAG_FIELDREF;
		this.classIndex = Utils.parseByteToInt(classIndex);
		this.nameAndTypeIndex = Utils.parseByteToInt(nameAndTypeIndex);
	}
	
	public int getClassIndex(){
		return this.classIndex;
	}
	
	public int getNameAndTypeIndex(){
		return this.nameAndTypeIndex;
	}
}
