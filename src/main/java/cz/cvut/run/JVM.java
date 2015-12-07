package cz.cvut.run;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import cz.cvut.run.attributes.CodeAttribute;
import cz.cvut.run.classfile.Method;
import cz.cvut.run.constants.Constants;
import cz.cvut.run.utils.Utils;

import org.apache.log4j.Logger;


public class JVM {
    private static ArrayList<ClassLoader> classes = new ArrayList<ClassLoader>();
    private static final Logger log = Logger.getLogger(JVM.class);

    public static void main(String[] args) throws Exception {
        log.debug("=============== START of JVM  ==============");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                classes.add(new ClassLoader(new File(args[i])));
               
            }
            ClassFile cf = classes.get(0).getClassFile();
            int codeIndex = cf.getCodeIndex();
            Method initMethod = cf.getInitMethod();
        	Method mainMethod = cf.getMainMethod();
        	Heap heap = new Heap();
        	Frame init = new Frame(initMethod, cf.getConstantPool(), heap, codeIndex);
        	
        	Stack<Byte> initResult = init.getStackResult();
        	init.execute();
        	Frame main = new Frame(mainMethod, cf.getConstantPool(), heap, codeIndex);
        	main.setStackResult(initResult);
            main.execute();
        } else {
            log.error("Please specify Class files in argumets!");
        }


        log.debug("================ END of JVM ================");
    }
    
    
    
    
    private static void insertToStack(Stack<Byte> stack, byte[] input){
    	for(int i=input.length-1; i>=0; i--){
    		stack.push(input[i]);
    	}
    }
    
    
    
    
    
}
