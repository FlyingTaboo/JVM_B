package cz.cvut.run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.apache.log4j.Logger;

import cz.cvut.run.attributes.CodeAttribute;
import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.classfile.Method;
import cz.cvut.run.classfile.constantpool.ConstClassInfo;
import cz.cvut.run.classfile.constantpool.ConstFieldRefInfo;
import cz.cvut.run.classfile.constantpool.ConstMethodRefInfo;
import cz.cvut.run.classfile.constantpool.ConstNameAndTypeInfo;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.stack.ArrayReference;
import cz.cvut.run.stack.ByteValue;
import cz.cvut.run.stack.CharValue;
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
	public Stack<StackElement> operandStack = new Stack<StackElement>();
	private ArrayList<ConstantPoolElement> constantPool;
	private ArrayList<Byte> byteCode;
	private CodeAttribute codeAttribute;
	@SuppressWarnings("unused")
	private Method method;
	private Heap heap;
	private int codeIndex = 0;
	private ClassFile cf;
	public Frame parent;
	ArrayList<ClassLoader> classes;
	
	public Frame(Method m, ClassFile cf, ArrayList<ClassLoader> classes, Heap heap, StackElement[] locals, Frame parent) throws Exception {
		this.method = m;
		codeIndex = cf.getCodeIndex();
		cf.getLineNumberTableIndex();
		byteCode = m.getCode(codeIndex);
		this.cf = cf;
		this.constantPool = cf.getConstantPool();
		this.heap = heap;
		this.codeAttribute = m.getCodeAttribute(codeIndex);
		localVariablesArray = new StackElement[codeAttribute.getMaxLocals()+1];
		for (int i=0; locals!=null && i<locals.length; i++){
			localVariablesArray[i] = locals[i];
		}
		this.parent = parent;
		this.classes = classes;
	}


	public Stack<StackElement> getStackResult(){
		return this.operandStack;
	}
	

	public void setStackResult(Stack<StackElement> input){
		if (input != null && input.size() > 0){
			this.operandStack = input;
		}
	}
	
	
	@SuppressWarnings("unused")
	public StackElement execute() throws Exception{

    	int pc = 0;
    	while (byteCode.size() > pc){
    		byte instruction = byteCode.get(pc);
    		pc++;
    		Utils.getInstructionName(instruction);
    		log.info(pc-1 + ": Instruction: " + Utils.getInstructionName(instruction) );
    		//log.info(operandStack);
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
					StackElement e = operandStack.pop();
					ArrayReference array = (ArrayReference) e;
					operandStack.push(array.getLength());
					break;
				}
				case Constants.INSTRUCTION_astore: {
					byte index = byteCode.get(pc++);
					localVariablesArray[index] = operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_astore_0:{
					localVariablesArray[0] = operandStack.pop();
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
					
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();
					IntValue index = (IntValue) e2;
					ArrayReference array = (ArrayReference) e1;
					Object o = array.getValue(index.getIntValue());
					operandStack.push(new ByteValue(o));
					break;
				}
				case Constants.INSTRUCTION_bastore: {
					// nebere zadne atributy z bytecode
					StackElement e3 = operandStack.pop();
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();
					if(e3 instanceof IntValue) e3 = new ByteValue(e3.getValue());
					ByteValue value = (ByteValue) e3;
					IntValue index = (IntValue) e2;
					ArrayReference array = (ArrayReference) e1;
					array.setValue(index.getIntValue(), ((Integer) value.getValue()) == 1 ? true: false);
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
					short s = (short)(short) (((index1&0xFF) << 8) | (index2&0xFF));
					ConstantPoolElement e = this.constantPool.get(s);
					operandStack.push(new StackElement(e));
					break;
				}
				case Constants.INSTRUCTION_goto: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					pc = pc-3 + s;
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
					operandStack.push(new CharValue((char) value.getIntValue()));
					break;
				}
				
				case Constants.INSTRUCTION_iadd: {
					IntValue value2 = (IntValue) operandStack.pop();
					IntValue value1 = (IntValue) operandStack.pop();
					
					operandStack.push(new IntValue(value1.getIntValue() + value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_iand: {
					// nebere zadne atributy z bytecode
					IntValue value2 = (IntValue) operandStack.pop();
					IntValue value1 = (IntValue) operandStack.pop();
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
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();
					IntValue value1 = null;
					IntValue value2 = null;
					if (e2 instanceof IntValue){
						value2 = (IntValue) e2;
					}else if (e2 instanceof CharValue){
						char abc = (Character) ((CharValue) e2).getValue();
						value2 = new IntValue((int) abc);
					}
					if (e1 instanceof IntValue){
						value1 = (IntValue) e1;
					}else if (e1 instanceof CharValue){
						char abc = (Character) ((CharValue) e1).getValue();
						value1 = new IntValue((int) abc);
					}
					if (value1.getIntValue() == value2.getIntValue()){
						pc = pc-3 + s;
					}
				
					break;
				}
				case Constants.INSTRUCTION_if_icmpge: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();
					if (e1 instanceof IntValue && e2 instanceof IntValue){
						IntValue value2 = (IntValue) e2;
						IntValue value1 = (IntValue) e1;
						if (value1.getIntValue() >= value2.getIntValue()){
								pc = pc-3 + s;
						}
					}else{
						pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_if_icmplt: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();
					if (e1 instanceof IntValue && e2 instanceof IntValue){
						IntValue value1 = (IntValue) e1;
						IntValue value2 = (IntValue) e2;
						if (value1.getIntValue() < value2.getIntValue()){
								pc = pc-3 + s;
						}
					}else{
						pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_if_icmpne: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();

					IntValue value1 = null;
					IntValue value2 = null;
					if (e2 instanceof IntValue){
						value2 = (IntValue) e2;
					}else if (e2 instanceof CharValue){
						char abc = (Character) ((CharValue) e2).getValue();
						value2 = new IntValue((int) abc);
					}
					if (e1 instanceof IntValue){
						value1 = (IntValue) e1;
					}else if (e1 instanceof CharValue){
						char abc = (Character) ((CharValue) e1).getValue();
						value1 = new IntValue((int) abc);
					}
					if (value1.getIntValue() != value2.getIntValue()){
							pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_ifeq: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					StackElement e1 = operandStack.pop();
					if (e1 instanceof IntValue){
						IntValue value = (IntValue) e1;
						if (value.getIntValue() == 0){
							pc = pc-3 + s;
						}
					}else if (e1 instanceof ByteValue){
						if (((Boolean) e1.getValue()) == false){
							pc = pc-3 + s;
						}
					}
					break;
				}
				case Constants.INSTRUCTION_ifle: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					StackElement se = operandStack.pop();
					IntValue value = null;
					if (se instanceof IntValue){
						value = (IntValue) se;
					}else if (se instanceof CharValue){
						char abc = (Character) ((CharValue) se).getValue();
						value = new IntValue((int) abc);
					}
					if (value.getIntValue() <= 0){
						pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_ifge: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));				
					StackElement se = operandStack.pop();
					IntValue value = null;
					if (se instanceof IntValue){
						value = (IntValue) se;
					}else if (se instanceof CharValue){
						char abc = (Character) ((CharValue) se).getValue();
						value = new IntValue((int) abc);
					}
					if (value.getIntValue() >= 0){
						pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_iflt: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));					
					StackElement se = operandStack.pop();
					IntValue value = null;
					if (se instanceof IntValue){
						value = (IntValue) se;
					}else if (se instanceof CharValue){
						char abc = (Character) ((CharValue) se).getValue();
						value = new IntValue((int) abc);
					}
					if (value.getIntValue() < 0){
						pc = pc-3 + s;
					}
					break;
				}
				case Constants.INSTRUCTION_ifne: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));				
					StackElement se = operandStack.pop();
					IntValue value = null;
					if (se instanceof IntValue){
						value = (IntValue) se;
					}else if (se instanceof CharValue){
						char abc = (Character) ((CharValue) se).getValue();
						value = new IntValue((int) abc);
					}else if (se instanceof ByteValue){
						boolean b = (Boolean) ((ByteValue) se).getValue();
						value = new IntValue(b == false? 0 :1);
					}
					if (value.getIntValue() != 0){
						pc = pc-3 + s;
					}
					
					break;
				}
				case Constants.INSTRUCTION_ifnull: {
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					short s = (short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));					
					StackElement value = operandStack.pop();
					if (value instanceof Null){
						pc = pc-3 + s;
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
					IntValue value2 = (IntValue) operandStack.pop();
					IntValue value1 = (IntValue) operandStack.pop();
					operandStack.push(new IntValue(value1.getIntValue() * value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_invokespecial: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					int methodIndex = (short) (((index1&0xFF) << 8) | (index2&0xFF));	
					StackElement e = invokeSpecial(methodIndex);
					if (e!=null){
						operandStack.push(e);
					}
					break;
				}
				case Constants.INSTRUCTION_invokestatic: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					int methodIndex =(short) (((index1&0xFF) << 8) | (index2&0xFF));

					StackElement e = invokeStatic(methodIndex);
					if (e!=null){
						operandStack.push(e);
					}
					
					break;
				}
				case Constants.INSTRUCTION_invokevirtual: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					int methodIndex =(short) (((index1&0xFF) << 8) | (index2&0xFF));
					
					StackElement e = invokeVirtual(methodIndex);
					if (e != null){
						operandStack.push(e);
					}
					break;
				}
				case Constants.INSTRUCTION_ireturn: {
					// neni nic treba brat z bytecode
					log.info(operandStack);
			    	return operandStack.pop();
				}
				case Constants.INSTRUCTION_ishl: {
					IntValue value2 = (IntValue) operandStack.pop();
					IntValue value1 = (IntValue) operandStack.pop();
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
					StackElement e2 = operandStack.pop();
					StackElement e1 = operandStack.pop();
					IntValue value1=null;
					IntValue value2=null;
					if (e2 instanceof IntValue){
						value2 = (IntValue) e2;
					}else if (e2 instanceof CharValue){
						char abc = (Character) ((CharValue) e2).getValue();
						value2 = new IntValue((int) abc);
					}
					if (e1 instanceof IntValue){
						value1 = (IntValue) e1;
					}else if (e1 instanceof CharValue){
						char abc = (Character) ((CharValue) e1).getValue();
						value1 = new IntValue((int) abc);
					}	
					operandStack.push(new IntValue(value1.getIntValue() - value2.getIntValue()));
					break;
				}
				case Constants.INSTRUCTION_ldc: {
					byte index = byteCode.get(pc++);
					operandStack.push(new Reference(constantPool.get(index-1)));
					break;
				}
				case Constants.INSTRUCTION_new: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					int index =(short) (((index1&0xFF) << 8) | (index2&0xFF))-1;
					ConstClassInfo clazz = (ConstClassInfo)constantPool.get(index); 
					operandStack.push(createNewObject(constantPool.get(clazz.getNameIndex()-1).toString()));
					break;
				}
				case Constants.INSTRUCTION_newarray: {
					byte type = byteCode.get(pc++);
					StackElement countE = operandStack.pop();
					
					if(type==4){
						boolean[] array = new boolean[((IntValue) countE).getIntValue()];
						ArrayReference ar = new ArrayReference(array);
						heap.addToHeap(ar);
						operandStack.push(ar);
					}else if (type==5){
						char[] array = new char[((IntValue) countE).getIntValue()];
						ArrayReference ar = new ArrayReference(array);
						heap.addToHeap(ar);
						operandStack.push(ar);
					}else{
						throw new Exception("Type: " + type);
					}
					break;
				}
				case Constants.INSTRUCTION_pop: {
					operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_putfield: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					StackElement value = operandStack.pop();
					ObjectReference objRef = (ObjectReference) operandStack.pop();
					int index =(short) (((index1&0xFF) << 8) | (index2&0xFF))-1;
					ConstFieldRefInfo field = (ConstFieldRefInfo) constantPool.get(index);
					ConstNameAndTypeInfo nati = (ConstNameAndTypeInfo) constantPool.get(field.getNameAndTypeIndex()-1);
					
					String name = constantPool.get(nati.getNameIndex()-1).toString(); 
					objRef.setField(name, value);
					break;
				}
				case Constants.INSTRUCTION_getfield: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					ObjectReference objRef = (ObjectReference) operandStack.pop();
					int index =(short) (((index1&0xFF) << 8) | (index2&0xFF))-1;
					ConstFieldRefInfo field = (ConstFieldRefInfo) constantPool.get(index);
					ConstNameAndTypeInfo nati = (ConstNameAndTypeInfo) constantPool.get(field.getNameAndTypeIndex()-1);
					
					String name = constantPool.get(nati.getNameIndex()-1).toString(); 
					operandStack.push((StackElement) objRef.getField(name));
					break;
				}
				case Constants.INSTRUCTION_nop: {
					// neni nic treba brat z bytecode
					
					break;
				}
				case Constants.INSTRUCTION_return: {
					// neni nic treba brat z bytecode
					operandStack.clear();
					return null;
					//break;
				}
				case Constants.INSTRUCTION_monitorenter:{
					operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_monitorexit:{
					operandStack.pop();
					break;
				}
				case Constants.INSTRUCTION_sipush:{
					byte branchbyte1 = byteCode.get(pc++);
					byte branchbyte2 = byteCode.get(pc++);
					int index =(short) (((branchbyte1&0xFF) << 8) | (branchbyte2&0xFF));
					operandStack.push(new IntValue(index));
					break;
				}
				default:{
					log.error("Unsupported instruction: " + Utils.getHexa(instruction));
				}
			}
    		heap.runGC(this);
    	}
    	
    	log.info(operandStack);
    	if (!operandStack.empty()){
    		return operandStack.pop();
    	}else{
    		return null;
    	}
    	
    	
    }
	
	private StackElement invokeStatic(int methodIndex) throws Exception {
		ConstMethodRefInfo m = (ConstMethodRefInfo) constantPool.get(methodIndex-1);
		ConstClassInfo clazz = (ConstClassInfo) constantPool.get(m.getClassIndex()-1);
		
		int indexName = m.getNameAndTypeIndex()-1;
		ConstNameAndTypeInfo methodNameType = (ConstNameAndTypeInfo) constantPool.get(indexName);
		
		int methodDescIndex = methodNameType.getDescriptorIndex()-1;
		int methodNameIndex = methodNameType.getNameIndex()-1;
		String methodName = constantPool.get(methodNameIndex).toString();
		String clazzName = constantPool.get(clazz.getNameIndex()-1).toString();
		log.debug("Invoke class: " + clazzName + ", " + methodName);
		int methodAttributesCount =  Utils.getMethodAttributesCount(constantPool.get(methodDescIndex));
		StackElement[] locals = new StackElement[methodAttributesCount];
		for(int i=0; i<methodAttributesCount; i++){
			locals[methodAttributesCount-i-1] = operandStack.pop();
		}
		log.debug("Invoke method: " + methodName + " " + Arrays.toString(locals));
		if (isCoreMethod(clazzName)){
			return Core.invokeCoreMethod(clazzName, methodName, locals, constantPool);
		}else{
			Method method = cf.getMethodByName(methodName);
			Frame f = new Frame(method, this.cf, this.classes, heap, locals, this);
			return f.execute();
		}
		
	}


	private StackElement invokeSpecial(int methodIndex) throws Exception {
		ConstMethodRefInfo m = (ConstMethodRefInfo) constantPool.get(methodIndex-1);
		ConstClassInfo clazz = (ConstClassInfo) constantPool.get(m.getClassIndex()-1);
		
		int indexName = m.getNameAndTypeIndex()-1;
		ConstNameAndTypeInfo methodNameType = (ConstNameAndTypeInfo) constantPool.get(indexName);
		
		int methodDescIndex = methodNameType.getDescriptorIndex()-1;
		int methodNameIndex = methodNameType.getNameIndex()-1;
		String methodName = constantPool.get(methodNameIndex).toString();
		String clazzName = constantPool.get(clazz.getNameIndex()-1).toString();
		
		int methodAttributesCount =  Utils.getMethodAttributesCount(constantPool.get(methodDescIndex));
		
		StackElement[] locals = new StackElement[methodAttributesCount+1];
		for(int i=0; i<methodAttributesCount; i++){
			locals[methodAttributesCount-i] = operandStack.pop();
		}
		StackElement e = operandStack.pop();
		locals[0] = e;
		
		if (isCoreMethod(clazzName)){
			StackElement e1 = Core.invokeCoreMethod(clazzName, methodName, locals, constantPool);
			return e1;
		}else{
			if (methodName.equals("<init>")) return null;
			ClassFile cf = Utils.getSuperClassFile(clazzName, e, classes);
			Method method = cf.getMethodByName(methodName);
			Frame f = new Frame(method, cf, this.classes, heap, locals, this);
			return f.execute();
		}
	}


	private StackElement invokeVirtual(int methodIndex) throws Exception {
		ConstMethodRefInfo m = (ConstMethodRefInfo) constantPool.get(methodIndex-1);
		ConstClassInfo clazz = (ConstClassInfo) constantPool.get(m.getClassIndex()-1);
		
		int indexName = m.getNameAndTypeIndex()-1;
		ConstNameAndTypeInfo methodNameType = (ConstNameAndTypeInfo) constantPool.get(indexName);
		
		int methodDescIndex = methodNameType.getDescriptorIndex()-1;
		int methodNameIndex = methodNameType.getNameIndex()-1;
		String methodName = constantPool.get(methodNameIndex).toString();
		String clazzName = constantPool.get(clazz.getNameIndex()-1).toString();
		log.debug("Invoke class: " + clazzName + ", " + methodName);
		log.debug(constantPool.get(methodDescIndex) + " " + methodName + " " + constantPool.get(indexName) + " " + clazzName);
		int methodAttributesCount =  Utils.getMethodAttributesCount(constantPool.get(methodDescIndex));
		log.debug("Invoke method: " + methodName + " " + operandStack);
		
		StackElement[] locals = new StackElement[methodAttributesCount+1];
		for(int i=0; i<methodAttributesCount; i++){
			locals[methodAttributesCount-i] = operandStack.pop();
		}
		StackElement e = operandStack.pop();
		locals[0] = e;
		if (isCoreMethod(clazzName)){
			return Core.invokeCoreMethod(clazzName, methodName, locals, constantPool);
		}else{
			ClassFile cf = Utils.getClassFileByClassAndMethodName(clazzName, this.classes, methodName, e);
			Method method = cf.getMethodByName(methodName);
			Frame f = new Frame(method, cf, this.classes, heap, locals, this);
			return f.execute();
		}

	}


	


	private boolean isCoreMethod(String clazz) {
		for(int i=0; i<this.classes.size(); i++){
			if (classes.get(i).getClassFile().getName().equals(clazz)){
				return false;
			}
		}
		return true;
	}


	private Reference createNewObject(String clazz){
		Reference r = createObject(clazz);
		heap.addToHeap(r);
		return r;
	}
	@SuppressWarnings("rawtypes")
	private Reference createObject(String clazz){
		Object o = new Object();
		if (clazz.equals("java/util/Stack")){
			return new StackReference(new Stack());
		}else if (clazz.equals("java/lang/StringBuilder")){
			return new StringBuilderReference(new StringBuilder());
		}else if (clazz.equals("java/lang/Exception")){
			return new ExceptionReference(new Exception());
		}else if (clazz.equals("java/lang/String")){
			return new StringReference(new String());
		}else if (clazz.equals("java/lang/Character")){
			return new CharacterReference(new Character(' '));
		}
		return new ObjectReference(o, clazz);
	}

}


