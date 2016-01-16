package cz.cvut.run.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cz.cvut.run.ClassFile;
import cz.cvut.run.ClassLoader;
import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.constants.Constants;

public class Utils {

	public static String getString(byte[] input){
		if (input == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(input.length);
		for (int i=0; i<input.length; i++){
			sb.append(input[i] + " ");
		}
		return sb.toString();
	}

	public static int parseByteToInt(byte[] input) {
		if (input == null || input.length == 0){
			return 0;
		}
		int result = 0;
		for (int i= 0; i<input.length; i++){
			result += unsignedToBytes(input[i]) * Math.pow(256, input.length-i-1);
		}
		return result;
	}
	
	
	public static int unsignedToBytes(byte b) {
		 return b & 0xFF;
	}
	
	public static byte getUnsignedByte(byte b) {
		 return (byte) (b & 0xFF);
	}
	public static String getHexa(byte[] input){
		String format = "";
		Object [] inputObject = new Object[input.length];
		for(int i=0; i<input.length; i++){
			format += "%02X ";
			inputObject[i] = input[i];
		}
		
		return String.format(format, inputObject);
	}
	
	public static String getHexa(byte input){
		return String.format("%02X", input);
	}
	
	public static String getInstructionName(byte instruction) throws IllegalArgumentException, IllegalAccessException{
		Constants c = new Constants();
		for(Field f: c.getClass().getFields()){
			if (f.getName().startsWith("INSTRUCTION_")){
				byte tmp = 0;
				tmp = f.getByte(c);
				if (instruction == tmp){
					return f.getName();
				}
			}
		}
		return "";
	}

	public static Object getHexa(ArrayList<Byte> code) {
		String format = "";
		Object [] inputObject = new Object[code.size()];
		for(int i=0; i<code.size(); i++){
			format += "%02X ";
			inputObject[i] = code.get(i);
		}
		
		return String.format(format, inputObject);
	}

	public static int getMethodAttributesCount(ConstantPoolElement constantPoolElement) {
		String descriptor = constantPoolElement.toString();
		descriptor =descriptor.substring(1, descriptor.indexOf(")"));
		String[] attrs = descriptor.split(";");
		if (attrs[attrs.length-1].isEmpty()){
			return attrs.length-1;
		}else{
			return attrs.length;
		}
		
	}

	public static ClassFile getClassFileByName(String clazzName, ArrayList<ClassLoader> classes) {
		for(int i=0; i<classes.size(); i++){
			ClassLoader cl = classes.get(i);
			ClassFile cf = cl.getClassFile();
			if (cf.getName().equals(clazzName)){
				return cf;
			}
		}
		return null;
	}

	
	
	
	
}
