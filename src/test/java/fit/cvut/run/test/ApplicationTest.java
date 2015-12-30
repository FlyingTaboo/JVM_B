package fit.cvut.run.test;

import org.junit.Test;

import cz.cvut.run.JVM;
import cz.cvut.run.utils.Utils;
import junit.framework.Assert;

public class ApplicationTest {
	private static final String TEST_CLASSES_PATH = "\\target\\test-classes\\fit\\cvut\\run\\test";
	private static final String TEST_CLASSES_WRONG_PATH = "\\target\\maven-status\\maven-compiler-plugin\\compile\\default-compile";
	
	private static final String DELIMITER = "\\";
	
	private static final String TestWrongFile = TEST_CLASSES_WRONG_PATH + DELIMITER + "createdFiles.lst";
	
	private static final String SatClassFile = DELIMITER + "Sat.class";
	
    @Test
    public void testMain() throws Exception { 
        JVM.main(null);
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testDirectory() throws Exception{ 
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH});
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testWrongPath() throws Exception{ 
    	JVM.main(new String[] {"wrong path"});
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testSimpleRightPathBadFile() throws Exception{ 
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+ TestWrongFile});
    }
	
    @Test
    public void testSatRightPath1() throws Exception{
    	String input = new String("abc&&");
    	String result = JVM.runJVM(new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH + SatClassFile, input);
    	Assert.assertEquals(result, "abc\n111");
    }
   
    @Test
    public void testSatRightPath2() throws Exception{
    	String input = new String("abc!d&|&");
    	String result = JVM.runJVM(new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH + SatClassFile, input);
    	Assert.assertEquals(result, "abcd\n1100");
    }
    
    @Test
    public void testSatRightPath3() throws Exception{ 
    	String input = new String("abcdef&|&&&");
    	String result = JVM.runJVM(new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH + SatClassFile, input);
    	Assert.assertEquals(result, "abcdef\n111100");
    }
    
    @Test
    public void testParseByteToInt(){
    	Assert.assertEquals(12345, Utils.parseByteToInt(new byte[] {48,57}));
    	Assert.assertEquals(1234512345, Utils.parseByteToInt(new byte[] {73,-107,41,-39}));
    }
}
