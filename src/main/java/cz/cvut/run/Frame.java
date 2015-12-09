package cz.cvut.run;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import cz.cvut.run.attributes.CodeAttribute;
import cz.cvut.run.attributes.LocalVariable;
import cz.cvut.run.attributes.LocalVariableTableAttribute;
import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.classfile.Method;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.stack.IntValue;
import cz.cvut.run.stack.Null;
import cz.cvut.run.stack.Reference;
import cz.cvut.run.stack.StackElement;
import cz.cvut.run.utils.Utils;

public class Frame {
    private static final Logger log = Logger.getLogger(Frame.class);
	private LocalVariableTableAttribute localVariables;
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
	int localVariableTableIndex;
	
	Frame(Method m, ArrayList<ConstantPoolElement> cp, Heap heap, int codeIndex, int localVariableTableIndex, int lineNumberTableIndex) throws Exception{
		this.method = m;
		
		byteCode = m.getCode(codeIndex);
		
		this.constantPool = cp;
		this.heap = heap;
		this.codeIndex = codeIndex;
		this.lineNumberTableIndex = lineNumberTableIndex;
		this.localVariableTableIndex = localVariableTableIndex;
		this.codeAttribute = m.getCodeAttribute(codeIndex);
		this.localVariables = this.codeAttribute.getLocalVariableTableAttribute(localVariableTableIndex);
		localVariablesArray = new StackElement[codeAttribute.getMaxLocals()];
	}
	
	
	public Stack<StackElement> getStackResult(){
		return this.operandStack;
	}
	

	public void setStackResult(Stack<StackElement> input){
		if (input != null && input.size() > 0){
			this.operandStack = input;
		}
	}
	
	
	public void execute() throws Exception{

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
					
					break;
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
					
					break;
				}
				case Constants.INSTRUCTION_bastore: {
					// nebere zadne atributy z bytecode
					
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
				case Constants.INSTRUCTION_getfield: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					break;
				}
				case Constants.INSTRUCTION_getstatic: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
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
					byte arg0 = byteCode.get(pc++);
					byte arg1 = byteCode.get(pc++);
					log.info("invokespecial args: " + Utils.getHexa(arg0) + " " + Utils.getHexa(arg1));
					
					//operandStack.push()
					//Instructions.invokeSpecial(stack, arg0, arg1, cf);
					break;
				}
				case Constants.INSTRUCTION_invokestatic: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					break;
				}
				case Constants.INSTRUCTION_invokevirtual: {
					byte index1 = byteCode.get(pc++);
					byte index2 = byteCode.get(pc++);
					
					break;
				}
				case Constants.INSTRUCTION_ireturn: {
					// neni nic treba brat z bytecode
					
					break;
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
					//TODO operandStack.clear();
					break;
				}
				default:{
					log.error("Unsupported instruction: " + Utils.getHexa(instruction));
				}
			}
    	}
    	
    	log.info(operandStack);
    	
    	
    	
    }
}
