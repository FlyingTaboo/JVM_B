package cz.cvut.run;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.apache.log4j.Logger;

import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.classfile.constantpool.ConstStringInfo;
import cz.cvut.run.classfile.constantpool.ConstUtf8Info;
import cz.cvut.run.stack.ArrayReference;
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
			ArrayList<ConstantPoolElement> constantPool) throws Exception {
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
		}else if (clazzName.equals("java/io/PrintWriter")){
			return invokePrintWriter(methodName, locals, constantPool);
		}else if (clazzName.equals("java/io/FileReader")){
			return invokeFileReader(methodName, locals, constantPool);
		}
		return null;
	}

	private static StackElement invokeFileReader(String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) throws Exception {

		if (methodName.equals("<init>")) {
			if(locals[1].getValue() instanceof ConstStringInfo){
				ConstStringInfo csi = (ConstStringInfo) locals[1].getValue();
				if (constantPool.get(csi.getStringIndex() - 1) instanceof ConstUtf8Info) {
					ConstUtf8Info utf = (ConstUtf8Info) constantPool.get(csi.getStringIndex() - 1);
					FileReader fr = new FileReader(utf.toString());
					return new Reference(fr);
				}
			}
			throw new Exception("Classname: fileReader , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}else if (methodName.equals("read")){
			if(locals[0] instanceof Reference){
				Reference r = (Reference) locals[0];
				FileReader fr = (FileReader) r.getValue();
				if (locals[1] instanceof ArrayReference){
					ArrayReference r4 = (ArrayReference) locals[1];
					int lenght = r4.getLength().getIntValue();
					char[] readed = new char[lenght];
					fr.read(readed);
					for(int i=0; i<lenght;i++){
						r4.setValue(i,readed[i]);
					}
					return null;
				}
			}
			throw new Exception("Classname: fileReader , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}else if (methodName.equals("close")){
			if(locals[0] instanceof Reference){
				Reference r = (Reference) locals[0];
				FileReader fw = (FileReader) r.getValue();
				fw.close();
			}
			return null;
		}else{
			throw new Exception("Classname: fileReader , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}
	}
	
	
	private static StackElement invokePrintWriter(String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) throws Exception {
		
		if (methodName.equals("<init>")) {
			if(locals[1].getValue() instanceof ConstStringInfo){
				ConstStringInfo csi = (ConstStringInfo) locals[1].getValue();
				if (constantPool.get(csi.getStringIndex() - 1) instanceof ConstUtf8Info) {
					ConstUtf8Info utf = (ConstUtf8Info) constantPool.get(csi.getStringIndex() - 1);
					PrintWriter pw = new PrintWriter(utf.toString());
					return new Reference(pw);
				}
			}
			throw new Exception("Classname: PrintWriter , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}else if (methodName.equals("println")){
			if(locals[0] instanceof Reference){
				Reference r = (Reference) locals[0];
				PrintWriter pw = (PrintWriter) r.getValue();
				if (locals[1] instanceof Reference){
					Reference r4 = (Reference) locals[1];
					ConstStringInfo csi = (ConstStringInfo) r4.getValue();
					if (constantPool.get(csi.getStringIndex() - 1) instanceof ConstUtf8Info) {
						ConstUtf8Info utf = (ConstUtf8Info) constantPool.get(csi.getStringIndex() - 1);
						pw.println(utf.toString());
					}
				}
				
			}
			return null;
		}else if (methodName.equals("close")){
			if(locals[0] instanceof Reference){
				Reference r = (Reference) locals[0];
				PrintWriter pw = (PrintWriter) r.getValue();
				pw.close();
			}
			return null;
		}else{
			throw new Exception("Classname: PrintWriter , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}
		
	}

	private static StackElement invokePrintStream(String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) {
		if (methodName.equals("println")) {
			StackElement e = locals[0];
			if (locals[1].getValue() instanceof ConstStringInfo){
				ConstStringInfo csi = (ConstStringInfo) locals[1].getValue();
				if (constantPool.get(csi.getStringIndex() - 1) instanceof ConstUtf8Info) {
					ConstUtf8Info utf = (ConstUtf8Info) constantPool.get(csi.getStringIndex() - 1);
					System.out.println(utf.toString());
				}
			}else if(locals[1] instanceof ArrayReference){
				ArrayReference ar = (ArrayReference) locals[1];
				for(int i=0;i<ar.getLength().getIntValue(); i++){
					Object o = ar.getValue(i);
					System.out.print(o);
				}
			}
		}
		return null;
	}

	private static StackElement invokeStringBuilder(String methodName, StackElement[] locals,
			ArrayList<ConstantPoolElement> constantPool) throws Exception {
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
			}else{
				throw new Exception("Classname: invokeStringBuilder , methodname: " + methodName + " locals" + Arrays.asList(locals));
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
			}else{
				throw new Exception("Classname: invokeStringBuilder , methodname: " + methodName + " locals" + Arrays.asList(locals));
			}
		}else{
			throw new Exception("Classname: invokeStringBuilder , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}
		return null;
	}

	private static StackElement invokeCharacter(String methodName, StackElement[] locals) throws Exception {
		if (methodName.equals("isLetter")) {
			CharValue c = (CharValue) locals[locals.length - 1];
			Character cc = (Character) c.getValue();
			return new ByteValue(Character.isLetter(cc.charValue()));
		}else{
			throw new Exception("Classname: invokeCharacter , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}
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
			ArrayList<ConstantPoolElement> constantPool) throws Exception {
		if (methodName.equals("charAt")) {
			if (locals[0].getValue() instanceof ConstStringInfo) {
				ConstStringInfo csi = (ConstStringInfo) locals[0].getValue();
				String value = constantPool.get(csi.getStringIndex() - 1).toString();
				return new CharValue(new Character(value.charAt(((IntValue) locals[locals.length - 1]).getIntValue())));
			} else if (locals[0] instanceof StringReference) {
				StringReference sr = (StringReference) locals[0];
				String value = (String) sr.getValue();
				return new CharValue(new Character(value.charAt(((IntValue) locals[locals.length - 1]).getIntValue())));
			}else{
				throw new Exception("Classname: invokeString , methodname: " + methodName + " locals" + Arrays.asList(locals));
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
			}else{
				throw new Exception("Classname: invokeString , methodname: " + methodName + " locals" + Arrays.asList(locals));
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
			throw new Exception("Classname: invokeString , methodname: " + methodName + " locals" + Arrays.asList(locals));

		}else{
			throw new Exception("Classname: invokeString , methodname: " + methodName + " locals" + Arrays.asList(locals));
		}
	}
}
