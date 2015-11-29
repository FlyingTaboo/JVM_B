package cz.cvut.run;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class JVM {
    private static ArrayList<ClassLoader> classes = new ArrayList<ClassLoader>();
    private static final Logger log = Logger.getLogger(JVM.class);
    
	public static void main(String[] args) throws Exception {
        log.debug("=============== START of JVM  ==============");
        if(args != null && args.length > 0){
        	for(int i=0; i< args.length; i++){
        		classes.add(new ClassLoader(new File(args[i])));
        	}
        }else{
        	log.error("Please specify Class files in argumets!");
        }
        
        
        
        
        log.debug("================ END of JVM ================");
    }
}
