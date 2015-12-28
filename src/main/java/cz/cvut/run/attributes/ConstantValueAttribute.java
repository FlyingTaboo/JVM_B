package cz.cvut.run.attributes;

import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class ConstantValueAttribute extends Attribute {

	private int constantValueIndex;
	
	public int getConstantValueIndex() {
		return constantValueIndex;
	}

	public ConstantValueAttribute(byte[] attributeNameIndex, byte[] attributeLength) {
		super(attributeNameIndex, attributeLength);
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		this.constantValueIndex = Utils.parseByteToInt(attributeInfo);
	}

}
