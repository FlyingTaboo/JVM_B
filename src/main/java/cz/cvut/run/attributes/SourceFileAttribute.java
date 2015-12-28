package cz.cvut.run.attributes;

import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class SourceFileAttribute extends Attribute {
	private int sourceFileIndex;
	
	public int getSourceFileIndex() {
		return sourceFileIndex;
	}

	public SourceFileAttribute(byte[] attributeNameIndex, byte[] attributeLength) {
		super(attributeNameIndex, attributeLength);
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		this.sourceFileIndex = Utils.parseByteToInt(attributeInfo);
	}

}
