package cz.cvut.run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.apache.log4j.Logger;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.classfile.constantpool.ConstStringInfo;
import cz.cvut.run.classfile.constantpool.ConstUtf8Info;
import cz.cvut.run.stack.ByteValue;
import cz.cvut.run.stack.CharValue;
import cz.cvut.run.stack.IntValue;
import cz.cvut.run.stack.Reference;
import cz.cvut.run.stack.StackElement;
import cz.cvut.run.stack.StackReference;
import cz.cvut.run.stack.StringBuilderReference;
import cz.cvut.run.stack.StringReference;

public class Core {
	private static final Logger log = Logger.getLogger(Core.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StackElement invokeCoreMethod(String clazzName, String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) {
		if (clazzName.equals("java/lang/String")) {
			return invokeString(clazzName, methodName, locals, constantPool);
		} else if (clazzName.equals("java/util/Stack")) {
			return invokeStack(methodName, locals);
		} else if (clazzName.equals("java/lang/Character")) {
			return invokeCharacter(methodName, locals);
		} else if (clazzName.equals("java/lang/StringBuilder")) {
			return invokeStringBuilder(methodName, locals, constantPool);

		} else if (clazzName.equals("java/io/PrintStream")) {
			return invokePrintStream(methodName, locals, constantPool);
		}
		return null;
	}

	private static StackElement invokePrintStream(String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) {
		if (methodName.equals("println")) {
			StackElement e = locals[0];
			ConstStringInfo csi = (ConstStringInfo) locals[1].getValue();
			if (constantPool.get(csi.getStringIndex() - 1) instanceof ConstUtf8Info) {
				ConstUtf8Info utf = (ConstUtf8Info) constantPool.get(csi.getStringIndex() - 1);
				System.out.println(utf.toString());
			}
		}
		return null;
	}

	private static StackElement invokeStringBuilder(String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) {
		if (methodName.equals("append")) {
			StackElement e = locals[0];
			StringBuilder sb = (StringBuilder) ((StringBuilderReference) e).getValue();
			if (locals[locals.length - 1] instanceof IntValue) {
				sb.append((char) (((IntValue) locals[locals.length - 1]).getIntValue()));
			} else if (locals[locals.length - 1] instanceof StringReference) {
				StringReference ref = (StringReference) locals[locals.length - 1];
				sb.append((String) ref.getValue());
			} else if (locals[locals.length - 1] instanceof Reference) {
				Reference ref = (Reference) locals[locals.length - 1];
				ConstStringInfo csi = (ConstStringInfo) ref.getValue();
				sb.append(constantPool.get(csi.getStringIndex() - 1));
			} else if (locals[locals.length - 1] instanceof CharValue) {
				sb.append(((CharValue) locals[locals.length - 1]).getValue());
			}
			return e;
		} else if (methodName.equals("toString")) {
			StackElement e = locals[0];
			StringBuilder sb = (StringBuilder) ((StringBuilderReference) e).getValue();
			return new StringReference(sb.toString());
		} else if (methodName.equals("<init>")) {
			StringBuilder sb = (StringBuilder) ((StringBuilderReference) locals[0]).getValue();
			if (locals.length == 2) {
				sb.append(((StringReference) locals[locals.length - 1]).getValue());
			}
		}
		return null;
	}

	private static StackElement invokeCharacter(String methodName, StackElement[] locals) {
		if (methodName.equals("isLetter")) {
			CharValue c = (CharValue) locals[locals.length - 1];
			Character cc = (Character) c.getValue();
			return new ByteValue(Character.isLetter(cc.charValue()));
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static StackElement invokeStack(String methodName, StackElement[] locals) {
		if (methodName.equals("pop")) {
			StackReference sr = (StackReference) locals[0];
			Stack srStack = (Stack) sr.getValue();
			return (StackElement) srStack.pop();
		} else if (methodName.equals("push")) {
			StackReference sr = (StackReference) locals[0];
			Stack srStack = (Stack) sr.getValue();
			srStack.push(locals[locals.length - 1]);
			return locals[locals.length - 1];
		}
		return null;
	}

	private static StackElement invokeString(String clazzName, String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) {
		if (methodName.equals("charAt")) {
			if (locals[0].getValue() instanceof ConstStringInfo) {
				ConstStringInfo csi = (ConstStringInfo) locals[0].getValue();
				String value = constantPool.get(csi.getStringIndex() - 1).toString();
				return new CharValue(new Character(value.charAt(((IntValue) locals[locals.length - 1]).getIntValue())));
			} else if (locals[0] instanceof StringReference) {
				StringReference sr = (StringReference) locals[0];
				String value = (String) sr.getValue();
				return new CharValue(new Character(value.charAt(((IntValue) locals[locals.length - 1]).getIntValue())));
			}
		} else if (methodName.equals("length")) {
			if (locals[0].getValue() instanceof ConstStringInfo) {
				ConstStringInfo csi = (ConstStringInfo) locals[0].getValue();
				String value = constantPool.get(csi.getStringIndex() - 1).toString();
				return new IntValue(value.length());
			} else if (locals[0] instanceof StringReference) {
				StringReference sr = (StringReference) locals[0];
				String value = (String) sr.getValue();
				return new IntValue(value.length());
			}

		} else if (methodName.equals("valueOf")) {
			if (locals[locals.length - 1] instanceof StringReference) {
				return new StringReference(locals[locals.length - 1].getValue());
			}
			ConstStringInfo csi = (ConstStringInfo) locals[locals.length - 1].getValue();
			if (constantPool.get(csi.getStringIndex() - 1) instanceof ConstUtf8Info) {
				ConstUtf8Info utf = (ConstUtf8Info) constantPool.get(csi.getStringIndex() - 1);
				return new StringReference(utf.toString());
			}

		}
		return null;
	}
}
