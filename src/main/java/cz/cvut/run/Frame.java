package cz.cvut.run;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import cz.cvut.run.attributes.CodeAttribute;
import cz.cvut.run.attributes.LocalVariable;
import cz.cvut.run.attributes.LocalVariableTableAttribute;
import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.classfile.Method;
import cz.cvut.run.classfile.constantpool.ConstClassInfo;
import cz.cvut.run.classfile.constantpool.ConstFieldRefInfo;
import cz.cvut.run.classfile.constantpool.ConstMethodRefInfo;
import cz.cvut.run.classfile.constantpool.ConstNameAndTypeInfo;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.stack.ArrayReference;
import cz.cvut.run.stack.ByteValue;
import cz.cvut.run.stack.CharacterReference;
import cz.cvut.run.stack.ExceptionReference;
import cz.cvut.run.stack.IntValue;
import cz.cvut.run.stack.Null;
import cz.cvut.run.stack.ObjectReference;
import cz.cvut.run.stack.Reference;
import cz.cvut.run.stack.StackElement;
import cz.cvut.run.stack.StackReference;
import cz.cvut.run.stack.StringBuilderReference;
import cz.cvut.run.stack.StringReference;
import cz.cvut.run.utils.Utils;

public class Frame {
    private static final Logger log = Logger.getLogger(Frame.class);
	private StackElement[] localVariablesArray;
	private Stack<StackElement> operandStack = new Stack<StackElement>();
	private ArrayList<ConstantPoolElement> constantPool;
	private ArrayList<Byte> byteCode;
	private CodeAttribute codeAttribute;
	@SuppressWarnings("unused")
	private Method method;
	private Heap heap;
	@SuppressWarnings("unused")
	private int codeIndex = 0;
	int lineNumberTableIndex;
	private ClassFile cf;
	
	public Frame(Method m, ClassFile cf, Heap heap, int codeIndex, int lineNumberTableIndex, StackElement[] locals, Stack<StackElement> operandStack) throws Exception {
		this.method = m;
		byteCode = m.getCode(codeIndex);
		this.cf = cf;
		this.constantPool = cf.getConstantPool();
		this.heap = heap;
		this.codeIndex = codeIndex;
		this.lineNumberTableIndex = lineNumberTableIndex;
		this.codeAttribute = m.getCodeAttribute(codeIndex);
		localVariablesArray = new StackElement[codeAttribute.getMaxLocals()];
		for (int i=0; locals!=null && i<locals.length; i++){
			localVariablesArray[i] = locals[i];
		}
		if (operandStack != null) {
			this.operandStack = operandStack;
		}
	}


	public Stack<StackElement> getStackResult(){
		return this.operandStack;
	}
	

	public void setStackResult(Stack<StackElement> input){
		if (input != null && input.size() > 0){
			this.operandStack = input;
		}
	}
	
	
	public StackElement execute() throws Exception{

    	int pc = 0;
    	while (byteCode.size() > pc){
    		byte instruction = byteCode.get(pc);
    		pc++;
    		Utils.getInstructionName(instruction);
    		log.info("Instruction: " + Utils.getInstructionName(instruction));
    		switch (instruction){
				case Constants.INSTRUCTION_aconst_null: {
					// neber zadne atributy z bytecode
					operandStack.push(new Null());
					break;
				}
				case Constants.INSTRUCTION_aload: {
					byte index = byteCode.get(pc++);
					operandStack.push(localVariablesArray[index]);
					break;
				}
				case Constants.INSTRUCTION_aload_0: {
					operandStack.push(localVariablesArray[0]);
					
					break;
				}
				case Constants.INSTRUCTION_aload_1: {
					operandStack.push(localVariablesArray[1]);
					break;
				}
				case Constants.INSTRUCTION_aload_2: {
					operandStack.push(localVariablesArray[2]);
					break;
				}
				case Constants.INSTRUCTION_aload_3: {
					operandStack.push(localVariablesArray[3]);
					break;
				}
				case Constants.INSTRUCTION_areturn: {
					// nebere zadne atributy z bytecode
					log.info(operandStack);
			    	return operandStack.pop();
				}
				case Constants.INSTRUCTION_arraylength: {
					// nebere zadne atributy z bytecode
					
					break;
				}
				case Constants.INSTRUCTION_astore: {
					byte index = byteCode.get(pc++);
					localVariablesArray[index] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_astore_1: {
					localVariablesArray[1] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_astore_2: {
					localVariablesArray[2] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_astore_3: {
					localVariablesArray[3] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_baload: {
					// nebere zadne atributy z bytecode
					StackElement e1 = operandStack.pop();
					StackElement e2 = operandStack.pop();
					IntValue index = (IntValue) e1;
					ArrayReference array = (ArrayReference) e2;
					Object o = array.getValue(index.getIntValue());
					operandStack.push(new ByteValue(o));
					break;
				}
				case Constants.INSTRUCTION_bastore: {
					// nebere zadne atributy z bytecode
					StackElement e1 = operandStack.pop();
					StackElement e2 = operandStack.pop();
					StackElement e3 = operandStack.pop();
					ByteValue value = (ByteValue) e1;
					IntValue index = (IntValue) e2;
					ArrayReference array = (ArrayReference) e3;
					array.setValue(index.getIntValue(), value);
					break;
				}
				case Constants.INSTRUCTION_bipush: {
					int _byte = byteCode.get(pc++);
					operandStack.push(new IntValue(_byte));
					break;
				}
				case Constants.INSTRUCTION_dup: {
					StackElement e = operandStack.peek();
					operandStack.push(e);
					break;
				}
				case Constants.INSTRUCTION_getstatic: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					short s = (short) ((index1 << 8) | index2);
					ConstantPoolElement e = this.constantPool.get(s);
					operandStack.push(new StackElement(e)); //TODO - vklada se tam pouze fieldRef
					break;
				}
				case Constants.INSTRUCTION_goto: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);
					//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					break;
				}
				case Constants.INSTRUCTION_checkcast: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					StackElement e = operandStack.pop();
					if (e instanceof Null){
						operandStack.push(new Null());
					}else if (e instanceof Reference){
						if(true){	//TODO
							operandStack.push(e);
						}else{
							throw new ClassCastException();
						}
					}
					break;
				}
				case Constants.INSTRUCTION_i2c: {
					IntValue value = (IntValue) operandStack.pop();
					IntValue result = new IntValue(value.getIntValue() & 0xFF00);
					operandStack.push(result);
					break;
				}
				
				case Constants.INSTRUCTION_iadd: {
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					operandStack.push(new IntValue(value1.getIntValue() + value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_iand: {
					// nebere zadne atributy z bytecode
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					operandStack.push(new IntValue(value1.getIntValue() & value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_iconst_0: {
					operandStack.push(new IntValue(0));
					break;
				}
				case Constants.INSTRUCTION_iconst_1: {
					operandStack.push(new IntValue(1));
					break;
				}
				case Constants.INSTRUCTION_iconst_2: {
					operandStack.push(new IntValue(2));
					break;
				}
				case Constants.INSTRUCTION_iconst_3: {
					operandStack.push(new IntValue(3));
					break;
				}
				case Constants.INSTRUCTION_if_icmpeq: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					if (value1.getIntValue() == value2.getIntValue()){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_if_icmpge: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					if (value1.getIntValue() >= value2.getIntValue()){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_if_icmpne: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					if (value1.getIntValue() != value2.getIntValue()){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_ifeq: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);
					IntValue value = (IntValue) operandStack.pop();
					if (value.getIntValue() == 0){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_ifle: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);					
					IntValue value = (IntValue) operandStack.pop();
					if (value.getIntValue() <= 0){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_iflt: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);					
					IntValue value = (IntValue) operandStack.pop();
					if (value.getIntValue() < 0){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_ifne: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);					
					IntValue value = (IntValue) operandStack.pop();
					if (value.getIntValue() != 0){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					
					break;
				}
				case Constants.INSTRUCTION_ifnull: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) ((branchbyte1 << 8) | branchbyte2);					
					StackElement value = operandStack.pop();
					if (value instanceof Null){
						//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_iinc: {
					byte index = byteCode.get(pc++);
					byte _const = byteCode.get(pc++);
					localVariablesArray[index] = new IntValue(((IntValue) localVariablesArray[index]).getIntValue() + _const);
					break;
				}
				case Constants.INSTRUCTION_iload: {
					int index = byteCode.get(pc++);
					operandStack.push(localVariablesArray[index]);
					break;
				}
				case Constants.INSTRUCTION_iload_1: {
					operandStack.push(localVariablesArray[1]);
					break;
				}
				case Constants.INSTRUCTION_iload_2: {
					operandStack.push(localVariablesArray[2]);
					break;
				}
				case Constants.INSTRUCTION_iload_3: {
					operandStack.push(localVariablesArray[3]);
					break;
				}
				case Constants.INSTRUCTION_imul: {
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					operandStack.push(new IntValue(value1.getIntValue() * value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_invokespecial: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					int methodIndex = ((index1 << 8) | index2);
					ConstMethodRefInfo m = (ConstMethodRefInfo) constantPool.get(methodIndex-1);
					
					int indexName = m.getNameAndTypeIndex()-1;
					ConstNameAndTypeInfo methodNameType = (ConstNameAndTypeInfo) constantPool.get(indexName);
					
					int methodDescIndex = methodNameType.getDescriptorIndex()-1;
					int methodNameIndex = methodNameType.getNameIndex()-1;
					
					if (constantPool.get(methodNameIndex).toString().equals("<init>")) break;
					int methodAttributesCount =  Utils.getMethodAttributesCount(constantPool.get(methodDescIndex));
					Stack<StackElement> stack = new Stack<StackElement>();
					stack.push(operandStack.pop());
					StackElement[] locals = new StackElement[methodAttributesCount];
					for(int i=0; i<methodAttributesCount; i++){
						locals[i] = operandStack.pop();
					}
					Method method = cf.getMethodByName(constantPool.get(methodNameIndex).toString());
					log.info("Invoke method: " + constantPool.get(methodNameIndex).toString());
					Frame f = new Frame(method, this.cf, heap, this.codeIndex, this.lineNumberTableIndex, locals, stack);
					operandStack.push(f.execute());
					break;
				}
				case Constants.INSTRUCTION_invokestatic: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					int methodIndex = ((index1 << 8) | index2);
					ConstMethodRefInfo m = (ConstMethodRefInfo) constantPool.get(methodIndex-1);
					
					int indexName = m.getNameAndTypeIndex()-1;
					ConstNameAndTypeInfo methodNameType = (ConstNameAndTypeInfo) constantPool.get(indexName);
					
					int methodDescIndex = methodNameType.getDescriptorIndex()-1;
					int methodNameIndex = methodNameType.getNameIndex()-1;
					
					int methodAttributesCount =  Utils.getMethodAttributesCount(constantPool.get(methodDescIndex));
					StackElement[] locals = new StackElement[methodAttributesCount];
					for(int i=0; i<methodAttributesCount; i++){
						locals[i] = operandStack.pop();
					}
					Method method = cf.getMethodByName(constantPool.get(methodNameIndex).toString());
					log.info("Invoke method: " + constantPool.get(methodNameIndex).toString());
					Frame f = new Frame(method, this.cf, heap, this.codeIndex, this.lineNumberTableIndex, locals, null);
					operandStack.push(f.execute());
					break;
				}
				case Constants.INSTRUCTION_invokevirtual: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					int methodIndex = ((index1 << 8) | index2);
					ConstMethodRefInfo m = (ConstMethodRefInfo) constantPool.get(methodIndex-1);
					
					int indexName = m.getNameAndTypeIndex()-1;
					ConstNameAndTypeInfo methodNameType = (ConstNameAndTypeInfo) constantPool.get(indexName);
					
					int methodDescIndex = methodNameType.getDescriptorIndex()-1;
					int methodNameIndex = methodNameType.getNameIndex()-1;
					
					int methodAttributesCount =  Utils.getMethodAttributesCount(constantPool.get(methodDescIndex));
					Stack<StackElement> stack = new Stack<StackElement>();
					stack.push(operandStack.pop());
					StackElement[] locals = new StackElement[methodAttributesCount];
					for(int i=0; i<methodAttributesCount; i++){
						locals[i] = operandStack.pop();
					}
					Method method = cf.getMethodByName(constantPool.get(methodNameIndex).toString());
					log.info("Invoke method: " + constantPool.get(methodNameIndex).toString());
					Frame f = new Frame(method, this.cf, heap, this.codeIndex, this.lineNumberTableIndex, locals, stack);
					operandStack.push(f.execute());
					break;
				}
				case Constants.INSTRUCTION_ireturn: {
					// neni nic treba brat z bytecode
					log.info(operandStack);
			    	return operandStack.pop();
				}
				case Constants.INSTRUCTION_ishl: {
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					operandStack.push(new IntValue(value1.getIntValue() << value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_istore: {
					byte index = byteCode.get(pc++);
					localVariablesArray[index] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_istore_2: {
					localVariablesArray[2] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_istore_3: {
					localVariablesArray[3] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_isub: {
					IntValue value1 = (IntValue) operandStack.pop();
					IntValue value2 = (IntValue) operandStack.pop();
					operandStack.push(new IntValue(value1.getIntValue() - value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_ldc: {
					byte index = byteCode.get(pc++);
					operandStack.push(new Reference(constantPool.get(index-1)));// Are you sure??? 
					break;
				}
				case Constants.INSTRUCTION_new: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					int index = ((index1 << 8) | index2)-1;
					ConstClassInfo clazz = (ConstClassInfo)constantPool.get(index); 
					operandStack.push(createNewObject(constantPool.get(clazz.getNameIndex()-1).toString()));
					break;
				}
				case Constants.INSTRUCTION_newarray: {
					byte type = byteCode.get(pc++);
					break;
				}
				case Constants.INSTRUCTION_pop: {
					operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_putfield: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					break;
				}
				case Constants.INSTRUCTION_nop: {
					// neni nic treba brat z bytecode
					
					break;
				}
				case Constants.INSTRUCTION_return: {
					// neni nic treba brat z bytecode
					operandStack.clear();
					break;
				}
				default:{
					log.error("Unsupported instruction: " + Utils.getHexa(instruction));
				}
			}
    	}
    	
    	log.info(operandStack);
    	return operandStack.pop();
    	
    	
    }
	
	private Reference createNewObject(String clazz){
		Object o = null;
		if (clazz.equals("java\\util\\Stack")){
			return new StackReference(new Stack());
		}else if (clazz.equals("java\\lang\\StringBuilder")){
			return new StringBuilderReference(new StringBuilder());
		}else if (clazz.equals("java\\lang\\Exception")){
			return new ExceptionReference(new Exception());
		}else if (clazz.equals("java\\lang\\String")){
			return new StringReference(new String());
		}else if (clazz.equals("java\\lang\\Character")){
			return new CharacterReference(new Character(' '));
		}
		
		return new ObjectReference(o);
	}
}


