package cz.cvut.run.attributes;

import cz.cvut.run.utils.Utils;

public class LocalVariable {
	private int startPc;
	private int length;
	private int nameIndex;
	private int descriptionIndex;
	private int index;
	
	LocalVariable(byte[] input){
		byte[] _startpc = new byte[2];
		byte[] _length = new byte[2];
		byte[] _nameIndex = new byte[2];
		byte[] _descriptionIndex = new byte[2];
		byte[] _index = new byte[2];
		
		_startpc[0] = input[0];
		_startpc[1] = input[1];
		
		_length[0] = input[2];
		_length[1] = input[3];
	
		_nameIndex[0] = input[4];
		_nameIndex[1] = input[5];
		
		_descriptionIndex[0] = input[6];
		_descriptionIndex[1] = input[7];
		
		_index[0] = input[8];
		_index[1] = input[9];
		this.startPc = Utils.parseByteToInt(_startpc);
		this.length = Utils.parseByteToInt(_length);
		this.nameIndex = Utils.parseByteToInt(_nameIndex);
		this.descriptionIndex = Utils.parseByteToInt(_descriptionIndex);
		this.index = Utils.parseByteToInt(_index);
	}
	
	public int getStartPc() {
		return startPc;
	}

	public int getLength() {
		return length;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public int getDescriptionIndex() {
		return descriptionIndex;
	}

	public int getIndex() {
		return index;
	}		
}
