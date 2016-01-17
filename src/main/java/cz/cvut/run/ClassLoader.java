package cz.cvut.run;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cz.cvut.run.attributes.SourceFileAttribute;
import cz.cvut.run.attributes.CodeAttribute;
import cz.cvut.run.attributes.ExceptionsAttribute;
import cz.cvut.run.attributes.ConstantValueAttribute;
import cz.cvut.run.classfile.Attribute;
import cz.cvut.run.classfile.ConstantPoolElement;
import cz.cvut.run.classfile.Field;
import cz.cvut.run.classfile.Interface;
import cz.cvut.run.classfile.Method;
import cz.cvut.run.classfile.constantpool.ConstClassInfo;
import cz.cvut.run.classfile.constantpool.ConstDoubleInfo;
import cz.cvut.run.classfile.constantpool.ConstFieldRefInfo;
import cz.cvut.run.classfile.constantpool.ConstFloatInfo;
import cz.cvut.run.classfile.constantpool.ConstIntegerInfo;
import cz.cvut.run.classfile.constantpool.ConstInterfaceMethodRefInfo;
import cz.cvut.run.classfile.constantpool.ConstLongInfo;
import cz.cvut.run.classfile.constantpool.ConstMethodRefInfo;
import cz.cvut.run.classfile.constantpool.ConstNameAndTypeInfo;
import cz.cvut.run.classfile.constantpool.ConstStringInfo;
import cz.cvut.run.classfile.constantpool.ConstUtf8Info;
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
	
	private byte[] access_flags = new byte[2];
	private byte[] this_class = new byte[2];
	private byte[] super_class = new byte[2];
	private byte[] interface_count = new byte[2];
	
	private byte[] attributes_count = new byte[2];
	private byte[] fields_count = new byte[2];	
	private byte[] methods_count = new byte[2];

	
	Object[] constantPool;
	Object[] fields;
	Object[] methods;
	Object[] attributes;
	protected ClassFile cf = new ClassFile();
	private int localVariableTableIndex;
	private int linesTableIndex;
	
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
	
	public ClassFile getClassFile(){
		return this.cf;
	}
	private void readFile() throws Exception{
		fis = new FileInputStream(file);
		
		fis.read(magic, 0, 4);
		if (!String.format("0x%02X%02X%02X%02X", magic[0], magic[1], magic[2], magic[3]).equals(Constants.JAVA_MAGIC)){
			log.error("Bad file format: " + file.getAbsolutePath());
			throw new Exception("Bad file format!");
		}
		
		//
		// Read Versions
		//
		fis.read(minor_version, 0, 2);
		fis.read(major_version, 0, 2);
		
		cf.setMinorVersion(Utils.parseByteToInt(minor_version));
		cf.setMajorVersion(Utils.parseByteToInt(major_version));
		
		log.trace("Minor version: \t\t" + Utils.getHexa(minor_version));
		log.trace("Major version: \t\t" + Utils.getHexa(major_version));
		
		
		//
		// Read constant pool
		//
		fis.read(constant_pool_count, 0, 2);
		
		cf.setConstantPoolCount(Utils.parseByteToInt(constant_pool_count));
		
		log.trace("Constant pool count: \t" + Utils.getHexa(constant_pool_count));
		
		readConstants();
		
		
		// 
		//Read access flags and class
		//
		fis.read(access_flags, 0, 2);
		fis.read(this_class, 0, 2);
		fis.read(super_class, 0, 2);
		cf.setSuperClass(Utils.parseByteToInt(super_class));
		cf.setAccessFlags(access_flags);
		cf.setThisClass(this_class);
		
		log.trace("Access flags: \t\t" + Utils.getHexa(access_flags));
		log.trace("This class: \t\t" + Utils.getHexa(this_class));
		log.trace("Super class: \t\t" + Utils.getHexa(super_class));
		
		
		
		//
		// Read interfaces
		//
		fis.read(interface_count, 0, 2);
		cf.setInterfaceCount(Utils.parseByteToInt(interface_count));
		log.trace("Interface count: \t\t" + Utils.getHexa(interface_count));
		
		readInterfaces();
		
		
		//
		// Read fields
		//
		fis.read(fields_count, 0, 2);
		cf.setFieldCount(Utils.parseByteToInt(fields_count));
		log.trace("Fields count: \t\t" + Utils.getHexa(fields_count));
		
		readFields();

		//
		// Read methods
		//
		fis.read(methods_count, 0, 2);
		cf.setMethodsCount(Utils.parseByteToInt(methods_count));
		log.trace("Methods count: \t\t" + Utils.getHexa(methods_count));
		readMethods();
		
		//
		// Read attributes
		//
		fis.read(attributes_count, 0, 2);
		cf.setAttributesCount(Utils.parseByteToInt(attributes_count));
		log.trace("Attributes count: \t\t" + Utils.getHexa(attributes_count));
		readAttributes();		
	}

	private void readAttributes() throws Exception {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		for (int i=0; i<cf.getAttributesCount(); i++){
			attributes.add(readAttribute());
		}
		cf.setAttributes(attributes);
	}

	private void readMethods() throws Exception {
		ArrayList<Method> methods = new ArrayList<Method>();
		
		for (int i=0; i<cf.getMethodsCount(); i++){
			byte[] access_flags = new byte[2];
			byte[] name_index = new byte[2];
			byte[] descriptor_index = new byte[2];
			byte[] attributes_count = new byte[2];
			
			fis.read(access_flags, 0, 2);
			fis.read(name_index, 0, 2);
			fis.read(descriptor_index, 0, 2);
			fis.read(attributes_count, 0, 2);
			
			log.trace("METH: Access flags: \t" + Utils.getHexa(access_flags));
			log.trace("METH: Name index: \t\t" + Utils.getHexa(name_index));
			log.trace("METH: Descriptor index: \t" + Utils.getHexa(descriptor_index));
			log.trace("METH: Attribures count: \t" + Utils.getHexa(attributes_count));
			
			Method m = new Method(access_flags, name_index, descriptor_index, attributes_count);
			log.trace("METH: name: " + cf.getConstantPool().get(m.getName_index()-1));
			log.trace("METH: descriptor: " + cf.getConstantPool().get(m.getDescriptor_index()-1));
			
			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			for(int j=0; j<m.getAttributesCount(); j++){
				attributes.add(readAttribute());
			}
			m.setAttributes_info(attributes);
			
			methods.add(m);
		}
		cf.setMethods(methods);
	}

	private void readFields() throws Exception {
		ArrayList<Field> fields = new ArrayList<Field>();
		
		for (int i=0; i<cf.getFieldCount(); i++){
			byte[] access_flags = new byte[2];
			byte[] name_index = new byte[2];
			byte[] descriptor_index = new byte[2];
			byte[] attributes_count = new byte[2];
			fis.read(access_flags, 0, 2);
			fis.read(name_index, 0, 2);
			fis.read(descriptor_index, 0, 2);
			fis.read(attributes_count, 0, 2);
			
			log.trace("FIELD: Access flags: \t" + Utils.getHexa(access_flags));
			log.trace("FIELD: Name index: \t\t" + Utils.getHexa(name_index));
			log.trace("FIELD: Descriptor index: \t" + Utils.getHexa(descriptor_index));
			log.trace("FIELD: Attribures count: \t" + Utils.getHexa(attributes_count));
			
			Field f = new Field(access_flags, name_index, descriptor_index, attributes_count);
			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			for(int j=0; j<f.getAttributesCount(); j++){
				attributes.add(readAttribute());
			}
			f.setAttributesInfo(attributes);
			fields.add(f);
		}
		cf.setFields(fields);
	}

	/**
	 * Metoda načte všechny interfejsi - nejsou žádné, proto teď nic nenačítá
	 * @throws Exception
	 */
	private void readInterfaces() throws Exception{
		ArrayList<Interface> interfaces = new ArrayList<Interface>();
		byte[] interfaceRef = new byte[2];
		for(int i=0; i<cf.getInterfaceCount(); i++){
			fis.read(interfaceRef, 0, 2);
			log.trace("Interface reference: \t\t" + Utils.getHexa(interfaceRef));
			interfaces.add(new Interface(interfaceRef));
		}
		cf.settInterfaces(interfaces);
	}
	
	/**
	 * Metoda načte všechny prvky z Constant poolu
	 * @throws Exception
	 */
	private void readConstants() throws Exception {
		byte[] tagArr = new byte[1];
		ArrayList<ConstantPoolElement> constantPool = new ArrayList<ConstantPoolElement>();
		
		for (int i=0; i<cf.getConstantPoolCount()-1; i++){
			fis.read(tagArr, 0, 1);
			
			byte tag = tagArr[0];
			switch(tag){
				case TAG_INTEGER:{					// 4 - uvádí počet bytu za tagem
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool.add(new ConstIntegerInfo(value));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(value));
					break;
				}
				case TAG_FLOAT:{
					// 4
					byte[] value = new byte[4]; 
					fis.read(value, 0, 4);
					constantPool.add(new ConstFloatInfo(value));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(value));
					break;
				}
				case TAG_LONG:{
					// 4 high  + 4 low
					byte[] low = new byte[4];
					byte[] high = new byte[4];
					fis.read(low, 0, 4);
					fis.read(high, 0, 4);
					constantPool.add(new ConstLongInfo(low, high));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(low)+ " " + Utils.getHexa(high));
					break;
				}
				case TAG_DOUBLE:{
					// 4 high + 4 low
					byte[] low = new byte[4];
					byte[] high = new byte[4];
					fis.read(low, 0, 4);
					fis.read(high, 0, 4);
					constantPool.add(new ConstDoubleInfo(low, high));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(low)+ " " + Utils.getHexa(high));
					break;
				}
				case TAG_UTF8:{
					// 2 length + length
					byte[] length = new byte[2];
					fis.read(length, 0, 2);
					byte[] value = new byte[Utils.parseByteToInt(length)];
					fis.read(value, 0, value.length);
					ConstUtf8Info utf8 = new ConstUtf8Info(length, value);
					
					constantPool.add(utf8);
					if (utf8.toString().equals(Constants.LOCAL_VARIABLE_TABLE)){
						this.localVariableTableIndex = constantPool.size();
					}else if (utf8.toString().equals(Constants.LOCAL_VARIABLE_TABLE)){
						this.linesTableIndex= constantPool.size();
					}
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(value));
					break;
				}
				case TAG_STRING:{
					// 2
					byte[] value = new byte[2]; 
					fis.read(value, 0, 2);
					constantPool.add(new ConstStringInfo(value));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(value));
					break;
				}
				case TAG_CLASS:{
					// 2
					byte[] value = new byte[2]; 
					fis.read(value, 0, 2);
					constantPool.add(new ConstClassInfo(value));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(value));
					break;
				}
				case TAG_FIELDREF:{
					// 2 class index + 2 name and type index
					byte[] class_index = new byte[2]; 
					byte[] name_and_type_index = new byte[2]; 
					fis.read(class_index, 0, 2);
					fis.read(name_and_type_index, 0, 2);
					
					constantPool.add(new ConstFieldRefInfo(class_index, name_and_type_index));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(class_index)+ "" + Utils.getHexa(name_and_type_index));
					break;
				}
				case TAG_METHODREF:{
					// 2 class index 2 name and type index
					byte[] class_index = new byte[2]; 
					byte[] name_and_type_index = new byte[2]; 
					fis.read(class_index, 0, 2);
					fis.read(name_and_type_index, 0, 2);
					
					constantPool.add(new ConstMethodRefInfo(class_index, name_and_type_index));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(class_index)+ "" + Utils.getHexa(name_and_type_index));
					break;
				}
				case TAG_INTERFACE_METHODREF:{
					// 2 class index + 2 name and type index
					byte[] class_index = new byte[2]; 
					byte[] name_and_type_index = new byte[2]; 
					fis.read(class_index, 0, 2);
					fis.read(name_and_type_index, 0, 2);
					
					constantPool.add(new ConstInterfaceMethodRefInfo(class_index, name_and_type_index));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(class_index)+ "" + Utils.getHexa(name_and_type_index));
					break;
				}
				case TAG_NAME_AND_TYPE:{
					// 2 name index 2 descriptor index
					byte[] class_index = new byte[2]; 
					byte[] descriptor_index = new byte[2]; 
					fis.read(class_index, 0, 2);
					fis.read(descriptor_index, 0, 2);
					
					constantPool.add(new ConstNameAndTypeInfo(class_index, descriptor_index));
					log.trace("Constant pool value: \t" + Utils.getHexa(tagArr) + "\t" + Utils.getHexa(class_index)+ "" + Utils.getHexa(descriptor_index));
					break;
				}
				default:
					log.error("Unsupported tag in constant pool!");
					throw new Exception("Unsupported tag in constant pool!");
			}
		}
		cf.setConstantPool(constantPool);
	}
	
	
	private Attribute readAttribute() throws Exception{
		byte[] attribute_name_index = new byte[2];
		byte[] attribute_length = new byte[4];
		byte[] attribute_info;
		
		fis.read(attribute_name_index, 0, 2);
		fis.read(attribute_length, 0, 4);
		
		log.trace("ATTR: Attribute name index: " + Utils.getHexa(attribute_name_index));
		String type = cf.getConstantPool().get(Utils.parseByteToInt(attribute_name_index)-1).toString();
		log.trace("ATTR: Attribute name: " + type);
		log.trace("ATTR: Attribute length: \t" + Utils.getHexa(attribute_length) + " " +Utils.parseByteToInt(attribute_length));
		Attribute result = null;
		if (type.equals("SourceFile")){
			result = new SourceFileAttribute(attribute_name_index, attribute_length);
		}else if(type.equals("Code")){
			result = new CodeAttribute(attribute_name_index, attribute_length, localVariableTableIndex, linesTableIndex);
		}else if(type.equals("Exceptions")){
			result = new ExceptionsAttribute(attribute_name_index, attribute_length);
		}else if(type.equals("ConstantValue")){
			result = new ConstantValueAttribute(attribute_name_index, attribute_length);
		}
		
		attribute_info = new byte[result.getAttributeLength()];
		
		for (int i=0; i<result.getAttributeLength(); i++){
			byte[] temp = new byte[1];
			fis.read(temp, 0, 1);
			log.trace("ATTR: Attribute info: \t" + Utils.getHexa(temp));
			attribute_info[i] = temp[0];
		}
		result.setAttributeInfo(attribute_info);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
