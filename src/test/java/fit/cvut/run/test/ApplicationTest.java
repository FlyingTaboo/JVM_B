package fit.cvut.run.test;

import org.junit.Test;

import cz.cvut.run.JVM;
import cz.cvut.run.utils.Utils;
import junit.framework.Assert;

public class ApplicationTest {
	private static final String TEST_CLASSES_PATH = "\\target\\test-classes\\fit\\cvut\\run\\test";
	private static final String TEST_CLASSES_WRONG_PATH = "\\target\\maven-status\\maven-compiler-plugin\\compile\\default-compile";
	
	private static final String DELIMITER = "\\";
	
	private static final String TestClassFile001 = DELIMITER + "TestClassFile001.class";
	private static final String TestWrongFile = TEST_CLASSES_WRONG_PATH + DELIMITER + "createdFiles.lst";
	
    @Test
    public void testMain() throws Exception { // spuštìní prázdné aplikace projde bez vyjímky
        JVM.main(null);
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testDirectory() throws Exception{ // Spuštìní s odkazem na adresáø
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH});
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testWrongPath() throws Exception{ // Spuštìní s odkazem na neplatnou cestu
    	JVM.main(new String[] {"wrong path"});
    }
    
    @Test()
    public void testSimpleRightPath() throws Exception{ // Jednoduché puštìní s odkazem na platnou cestu
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH + TestClassFile001});
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testSimpleRightPathBadFile() throws Exception{ // Jednoduché puštìní s odkazem na platnou cestu ale chybný soubor
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+ TestWrongFile});
    }
    
    @Test
    public void testParseByteToInt(){
    	Assert.assertEquals(12345, Utils.parseByteToInt(new byte[] {48,57}));
    	Assert.assertEquals(1234512345, Utils.parseByteToInt(new byte[] {73,-107,41,-39}));
    }
}
