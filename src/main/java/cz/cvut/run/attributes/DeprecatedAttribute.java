package cz.cvut.run.attributes;

import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class DeprecatedAttribute extends Attribute {

	private int deprecatedAttributeInfo;
	
	public int getConstantValueIndex() {
		return deprecatedAttributeInfo;
	}

	public DeprecatedAttribute(byte[] attributeNameIndex, byte[] attributeLength) {
		super(attributeNameIndex, attributeLength);
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		this.deprecatedAttributeInfo = Utils.parseByteToInt(attributeInfo);
	}

}
