package cz.cvut.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import cz.cvut.run.classfile.Method;
import cz.cvut.run.stack.StackElement;
import cz.cvut.run.stack.StringReference;

import org.apache.log4j.Logger;


public class JVM {
    private static ArrayList<ClassLoader> classes = new ArrayList<ClassLoader>();
    private static final Logger log = Logger.getLogger(JVM.class);

    public static void main(String[] args) throws Exception{
    	if (args != null && args.length > 0){
    		for(int i=0; i<args.length; i++){
					readFileAndrunJVM(args[i]);
    		}
    	}
    }

	public static String runJVM(ArrayList<String> pathClasses, String input) throws Exception{
    	String result = "";
    	log.debug("=============== START of JVM  ==============");
    	classes.clear();
    	for(int i=0; i<pathClasses.size();i++){
    		classes.add(new ClassLoader(new File(pathClasses.get(i))));
    	}
        
        ClassFile cf = classes.get(0).getClassFile();
        int codeIndex = cf.getCodeIndex();
        int lineNumberTableIndex = cf.getLineNumberTableIndex();
        Method mainMethod = cf.getMainMethod();
    	Heap heap = new Heap();

    	
    	Frame main = new Frame(mainMethod, cf, classes, heap, codeIndex, lineNumberTableIndex, new StackElement[] {new StringReference(input)}, null, null);
        StackElement e = main.execute();
        if (e instanceof StringReference){
    		StringReference s = (StringReference) e;
    		result = s.getValue().toString();
    	}
        log.info(result);
        log.debug("================ END of JVM ================");
        return result;
    }
    
    
    private static void readFileAndrunJVM(String file) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	
    	try {
    	    String input = br.readLine();
    	    String path = br.readLine();
    	    ArrayList<String> paths = new ArrayList<String>();
    	    paths.add(path);
    	    try{
    	    	String path1 = br.readLine();
    	    	paths.add(path1);
    	    	String path2 = br.readLine();
    	    	paths.add(path2);
    	    }catch (Exception e){}
    	    
    	    appendToOutputFile(runJVM(paths, input));
    	} finally {
    	    br.close();
    	}
		
	}
    
    private static void appendToOutputFile(String input){
    	String delimiter = "========================================\n";
    	try {
    		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("OUTPUTFILE", true)));
    		out.println(new Date());
    		out.println(input);
    	    out.println(delimiter);
    	}catch (IOException e) {
    	}
    }

    
}
