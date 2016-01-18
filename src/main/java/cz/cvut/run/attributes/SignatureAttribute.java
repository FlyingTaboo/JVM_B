package cz.cvut.run.attributes;

import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class SignatureAttribute extends Attribute {

	private int signatureValueInfo;
	
	public int getConstantValueIndex() {
		return signatureValueInfo;
	}

	public SignatureAttribute(byte[] attributeNameIndex, byte[] attributeLength) {
		super(attributeNameIndex, attributeLength);
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		this.signatureValueInfo = Utils.parseByteToInt(attributeInfo);
	}

}
