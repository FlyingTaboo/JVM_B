package cz.cvut.run.classfile.constantpool;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ConstNameAndTypeInfo extends ConstantPoolElement {
	private int classIndex;
	private int descriptorIndex;
	
	public ConstNameAndTypeInfo(byte[] classIndex, byte[] descriptorIndex){
		this.tag = Constants.TAG_NAME_AND_TYPE;
		this.classIndex = Utils.parseByteToInt(classIndex);
		this.descriptorIndex = Utils.parseByteToInt(descriptorIndex);
	}

	public int getClassIndex(){
		return this.classIndex;
	}
	
	public int getDescriptorIndex(){
		return this.descriptorIndex;
	}
}
