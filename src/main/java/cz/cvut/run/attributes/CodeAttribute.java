package cz.cvut.run.attributes;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cz.cvut.run.ClassLoader;
import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.utils.Utils;

public class CodeAttribute extends Attribute {
	private static final Logger log = Logger.getLogger(CodeAttribute.class);
	private int maxStack;
	private int maxLocals;
	private int codeLength;
	private ArrayList<Byte> code;
	private int exceptionTableLength;
	private int attributesCount;
	private int localVariableTableIndex;
	private int linesNumberIndex;
	
	private ArrayList<ExceptionTableElement> exceptionsTable = new ArrayList<ExceptionTableElement>();
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	
	public CodeAttribute(byte[] attributeNameIndex, byte[] attributeLength, int localVariableTableIndex, int linesNumberIndex) {
		super(attributeNameIndex, attributeLength);
		this.localVariableTableIndex = localVariableTableIndex;
		this.linesNumberIndex = linesNumberIndex;
	}
	
	@Override
	public void setAttributeInfo(byte[] attributeInfo) {
		maxStack = Utils.parseByteToInt(new byte[]{attributeInfo[0], attributeInfo[1]});
		maxLocals = Utils.parseByteToInt(new byte[]{attributeInfo[2], attributeInfo[3]});
		codeLength = Utils.parseByteToInt(new byte[]{attributeInfo[4], attributeInfo[5], attributeInfo[6], attributeInfo[7]});
		code = new ArrayList<Byte>(codeLength);
		for(int i=0; i<codeLength; i++){
			code.add(i, attributeInfo[i+8]);
		}
		log.debug(Utils.getHexa(code));
		exceptionTableLength = Utils.parseByteToInt(new byte[]{attributeInfo[codeLength+8], attributeInfo[codeLength+9]});
		
		int p = codeLength+10;
		
		for(int i=0; i<exceptionTableLength; i++){
			
			byte[] tmp = new byte[8];
			tmp[0] = attributeInfo[p];
			tmp[1] = attributeInfo[p + 1];
			tmp[2] = attributeInfo[p + 2];
			tmp[3] = attributeInfo[p + 3];
			tmp[4] = attributeInfo[p + 4];
			tmp[5] = attributeInfo[p + 5];
			tmp[6] = attributeInfo[p + 6];
			tmp[7] = attributeInfo[p + 7];							
			exceptionsTable.add(new ExceptionTableElement(tmp));
			p += 8;
		}
		
		attributesCount = Utils.parseByteToInt(new byte[]{attributeInfo[p], attributeInfo[++p]});
		
		for(int i=0; i<attributesCount; i++){
			byte[] attributeNameIndex = new byte[]{attributeInfo[++p], attributeInfo[++p]};
			byte[] attributeLength = new byte[]{attributeInfo[++p], attributeInfo[++p], attributeInfo[++p], attributeInfo[++p]};
			int nameIndex = Utils.parseByteToInt(attributeNameIndex);
			Attribute a;
			if (nameIndex == this.localVariableTableIndex){
				a = new LocalVariableTableAttribute(attributeNameIndex, attributeLength);
			}else if(nameIndex == this.linesNumberIndex){
				a = new LineNumberTableAttribute(attributeNameIndex, attributeLength);
			}else{
				a = new Attribute(attributeNameIndex, attributeLength);
			}
			
			byte[] tmpAttributeInfo = new byte[Utils.parseByteToInt(attributeLength)];
			for(int j=0; j<Utils.parseByteToInt(attributeLength); j++){
				tmpAttributeInfo[j] = attributeInfo[++p];
			}
			a.setAttributeInfo(tmpAttributeInfo);
			attributes.add(a);
			//p = p + Utils.parseByteToInt(attributeLength);
		}
	}
	
	
	public int getMaxStack() {
		return maxStack;
	}

	public int getMaxLocals() {
		return maxLocals;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public ArrayList<Byte> getCode() {
		return code;
	}

	public int getExceptionTableLength() {
		return exceptionTableLength;
	}

	public int getAttributesCount() {
		return attributesCount;
	}

	public ArrayList<ExceptionTableElement> getExceptionsTable() {
		return exceptionsTable;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}
	
	public LocalVariableTableAttribute getLocalVariableTableAttribute(int index) throws Exception{
		for(Attribute attr: attributes){
			int nameIndex = attr.getAttributeNameIndex();
			
			if(nameIndex == index+1){
				LocalVariableTableAttribute lvta = (LocalVariableTableAttribute) attr;
				return lvta;
			}
		}
		log.error("Method doesnt have LocalVariableTable attribute!");
		throw new Exception("Method doesnt have LocalVariableTable attribute!");
	}
	
	public LineNumberTableAttribute getLineNumberTableAttribute(int index) throws Exception{
		for(Attribute attr: attributes){
			int nameIndex = attr.getAttributeNameIndex();
			
			if(nameIndex == index+1){
				LineNumberTableAttribute lnta = (LineNumberTableAttribute) attr;
				return lnta;
			}
		}
		log.error("Method doesnt have LineNumberTable attribute!");
		throw new Exception("Method doesnt have LineNumberTable attribute!");
	}
	
	private class ExceptionTableElement{
		private byte[] start_pc = new byte[2];
		private byte[] end_pc = new byte[2];
		private byte[] handler_pc = new byte[2];
		private byte[] catch_type = new byte[2];
		
		ExceptionTableElement(byte[] input){
			start_pc[0] = input[0];
			start_pc[1] = input[1];
			end_pc[0] = input[2];
			end_pc[1] = input[3];
			handler_pc[0] = input[4];
			handler_pc[1] = input[5];
			catch_type[0] = input[6];
			catch_type[1] = input[7];
		}
	}
}
