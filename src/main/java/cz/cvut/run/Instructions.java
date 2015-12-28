package cz.cvut.run;

import java.util.Stack;

import cz.cvut.run.classfile.constantpool.ConstMethodRefInfo;
import cz.cvut.run.stack.StackElement;
import cz.cvut.run.utils.Utils;

public class Instructions {

	public static void invokeSpecial(Stack<StackElement> stack, byte arg0, byte arg1, ClassFile cf){
		int index = Utils.parseByteToInt(new byte[]{arg0, arg1})-1;
		ConstMethodRefInfo methodRef = (ConstMethodRefInfo) cf.getConstantPool().get(index);
		System.out.println(methodRef.getClassIndex() + " " +methodRef.getNameAndTypeIndex());
		for(int i=0; i<cf.getMethodsCount(); i++){
			int index2 = cf.getMethods().get(i).getName_index() -1;
			System.out.println(cf.getConstantPool().get(index2).toString());
		}
	}
	
	
}
