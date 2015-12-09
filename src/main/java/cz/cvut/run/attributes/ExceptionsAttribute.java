package cz.cvut.run.attributes;

import java.util.ArrayList;

import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class ExceptionsAttribute extends Attribute {
	
	private int numberOfExceptions;
	private ArrayList<Integer> exceptionsIndexes = new ArrayList<Integer>();

	public ExceptionsAttribute(byte[] attributeNameIndex, byte[] attributeLength) {
		super(attributeNameIndex, attributeLength);
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		byte[] number = new byte[2];
		number[0] = attributeInfo[0];
		number[1] = attributeInfo[1];
		this.numberOfExceptions = Utils.parseByteToInt(number);
		for(int i=0; i<numberOfExceptions; i++){
			byte[] tmp = new byte[2];
			tmp[0] = attributeInfo[2+i*2];
			tmp[1] = attributeInfo[3+i*2];
			exceptionsIndexes.add(Utils.parseByteToInt(tmp));
		}
	}
	
	public ArrayList<Integer> getExceptionsIndexes() {
		return exceptionsIndexes;
	}

	public int getNumberOfExceptions() {
		return numberOfExceptions;
	}

}
