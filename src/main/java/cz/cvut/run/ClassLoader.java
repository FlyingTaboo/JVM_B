package cz.cvut.run;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

public class ClassLoader {
	private static final Logger log = Logger.getLogger(ClassLoader.class);
	private static final byte TAG_INTEGER = 3;
	private static final byte TAG_FLOAT = 4;
	private static final byte TAG_LONG = 5;
	private static final byte TAG_DOUBLE = 6;
	private static final byte TAG_UTF8 = 1;
	private static final byte TAG_STRING = 8;
	private static final byte TAG_CLASS = 7;
	private static final byte TAG_FIELDREF = 9;
	private static final byte TAG_METHODREF = 10;
	private static final byte TAG_INTERFACE_METHODREF = 11;
	private static final byte TAG_NAME_AND_TYPE = 12;
	
	
	private File file;
	private FileInputStream fis; 
	private byte[] magic = new byte[4];
	private byte[] minor_version = new byte[2];
	private byte[] major_version = new byte[2];
	private byte[] constant_pool_count = new byte[2];
	private int constantPoolCount =0;
	
	private byte[] access_flags = new byte[2];
	private byte[] this_class = new byte[2];
	private byte[] super_class = new byte[2];
	private byte[] interface_count = new byte[2];
	private int interfaceCount = 0;
	
	private byte[] attributes_count = new byte[2];
	private int attributesCount = 0;
	
	private byte[] fields_count = new byte[2];
	private int fieldsCount = 0;
	
	private byte[] methods_count = new byte[2];
	private int methodsCount = 0;
	
	Object[] constantPool;
	Object[] fields;
	
	
	ClassLoader(File file) throws Exception{
		this.file = file;
		if (this.file.exists() && !this.file.isDirectory()){
			readFile();
			fis.close();
		}else{
			log.error("Unsupported file " + this.file.getAbsolutePath() + "!");
			throw new Exception("Unsupported file " + this.file.getAbsolutePath() + "!");
		}
	}
	
	private void readFile() throws Exception{
		fis = new FileInputStream(file);
		fis.read(magic, 0, 4);
		if (!String.format("0x%02X%02X%02X%02X", magic[0], magic[1], magic[2], magic[3]).equals(Constants.JAVA_MAGIC)){
			log.error("Bad file format: " + file.getAbsolutePath());
			throw new Exception("Bad file format!");
		}
		fis.read(minor_version, 0, 2);

		fis.read(major_version, 0, 2);
		
		fis.read(constant_pool_count, 0, 2);
		constantPoolCount = Utils.parseByteToInt(constant_pool_count);
		readConstants();
		
		fis.read(access_flags, 0, 2);
		fis.read(this_class, 0, 2);
		fis.read(super_class, 0, 2);
		
		fis.read(interface_count, 0, 2);
		interfaceCount = Utils.parseByteToInt(interface_count);
		readInterfaces();
		
		fis.read(fields_count, 0, 2);
		fieldsCount = Utils.parseByteToInt(fields_count);
		readFields();
		
		fis.read(methods_count, 0, 2);
		methodsCount = Utils.parseByteToInt(methods_count);
		readMethods();
		
		fis.read(attributes_count, 0, 2);
		attributesCount = Utils.parseByteToInt(attributes_count);
		readAttributes();
		
		log.info(constantPoolCount + " " + interfaceCount +" " + fieldsCount + " " + methodsCount + " " + attributesCount);
	}

	private void readAttributes() {
		// TODO Auto-generated method stub
		
	}

	private void readMethods() {
		// TODO Auto-generated method stub
		
	}

	private void readFields() throws Exception {
		fields = new Object[fieldsCount];
		for (int i=0; i<fieldsCount; i++){
			fields[i] = new fieldObject();
		}
	}

	/**
	 * Metoda naète všechny interfejsi - nejsou žádné, proto teï nic nenaèítá
	 * @throws Exception
	 */
	private void readInterfaces() throws Exception{
		byte[] interfaceRef = new byte[2];
		for(int i=0; i<interfaceCount; i++){
			fis.read(interfaceRef, 0, 2);
		}
		
	}
	
	/**
	 * Metoda naète všechny prvky z Constant poolu
	 * @throws Exception
	 */
	private void readConstants() throws Exception {
		byte[] tagArr = new byte[1];
		constantPool = new Object[constantPoolCount];
		
		for (int i=0; i<constantPoolCount-1; i++){
			fis.read(tagArr, 0, 1);
			byte tag = tagArr[0];
			switch(tag){
				case TAG_INTEGER:{					// 4 - uvádí poèet bytu za tagem
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_FLOAT:{
					// 4
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_LONG:{
					// 4 high  + 4 low
					byte[] value = new byte[8]; 
					fis.read(value, 0, 8);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_DOUBLE:{
					// 4 high + 4 low
					byte[] value = new byte[8]; 
					fis.read(value, 0, 8);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_UTF8:{
					// 2 length + length
					byte[] length = new byte[2];
					fis.read(length, 0, 2);
					byte[] value = new byte[Utils.parseByteToInt(length)];
					fis.read(value, 0, value.length);
					byte[] finalValue = new byte[2 + value.length];
					finalValue[0] = length[0];
					for(int j=2; j<finalValue.length; j++){
						finalValue[j] = value[j-2];
					}
					constantPool[i] = new constPoolObject(tag, finalValue);
					break;
				}
				case TAG_STRING:{
					// 2
					byte[] value = new byte[2]; 
					fis.read(value, 0, 2);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_CLASS:{
					// 2
					byte[] value = new byte[2]; 
					fis.read(value, 0, 2);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_FIELDREF:{
					// 2 class index + 2 name and type index
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_METHODREF:{
					// 2 class index 2 name and type index
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_INTERFACE_METHODREF:{
					// 2 class index + 2 name and type index
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				case TAG_NAME_AND_TYPE:{
					// 2 name index 2 descriptor index
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool[i] = new constPoolObject(tag, value);
					break;
				}
				default:
					log.error("Unsupported tag in constant pool!");
					throw new Exception("Unsupported tag in constant pool!");
			}
			
		}
	}
	
	/**
	 * Tøída která reprezentuje jednu hodnotu z constatnt poolu
	 * @author Jeff
	 *
	 */
	private class constPoolObject{
		private byte tag;
		private byte[] value;
		constPoolObject(byte tag, byte[] value){
			this.tag = tag;
			this.value = value;
		}
		
		public byte getTag(){
			return this.tag;
		}
		public byte[] getValue(){
			return this.value;
		}
	}
	
	private class fieldObject{
		private byte[] access_flags = new byte[2];
		private byte[] name_index = new byte[2];
		private byte[] descriptor_index = new byte[2];
		private byte[] attributes_count = new byte[2];
		private Object[] attributes_info;
		fieldObject() throws Exception{
			fis.read(access_flags, 0, 2);
			fis.read(name_index, 0, 2);
			fis.read(descriptor_index, 0, 2);
			fis.read(attributes_count, 0, 2);
			attributes_info = new Object[Utils.parseByteToInt(attributes_count)];
			for(int i=0; i<Utils.parseByteToInt(attributes_count); i++){
				attributes_info[i] = new attributeInfo();
			}
		}
	}
	
	private class attributeInfo{
		private byte[] attribute_name_index = new byte[2];
		private byte[] attribute_length = new byte[4];
		private Object[] attribute_info;
		
		attributeInfo() throws Exception{
			fis.read(attribute_name_index, 0, 2);
			fis.read(attribute_length, 0, 4);
			attribute_info = new Object[Utils.parseByteToInt(attribute_length)];
			for (int i=0; i<Utils.parseByteToInt(attribute_length); i++){
				byte[] temp = new byte[2];
				fis.read(temp, 0, 2);
				attribute_info[i] = temp;
			}
		}
	}

}
