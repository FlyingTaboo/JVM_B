package cz.cvut.run.attributes;

import java.util.ArrayList;

import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class LocalVariableTableAttribute extends Attribute{
	private int localVariableTableLength;
	private ArrayList<LocalVariable> localVariables = new ArrayList<LocalVariable>();
	
	public LocalVariableTableAttribute(byte[] attributeNameIndex, byte[] attributeLength) {
		super(attributeNameIndex, attributeLength);
		
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		int p =0;
		this.localVariableTableLength = Utils.parseByteToInt(new byte[]{attributeInfo[p++], attributeInfo[p++]});
		for(int i=0; i<this.localVariableTableLength; i++){
			byte[] tmp = new byte[10];
			for(int j=0; j<10;j++){
				tmp[j] = attributeInfo[p++];
			}
			localVariables.add(new LocalVariable(tmp));
		}
	}
	
	public LocalVariable getLocalVariable(int index){
		for(int i=0; i<localVariables.size(); i++){
			if (localVariables.get(i).getIndex() == index){
				return localVariables.get(i);
			}
		}
		return null;
	}

	public void setLocalVariable(int index, LocalVariable l){
		localVariables.set(index, l);
	}
}
