package fit.cvut.run.test;

import org.junit.Test;

import cz.cvut.run.JVM;

public class ApplicationTest {
	private static final String TEST_CLASSES_PATH = "\\target\\test-classes\\fit\\cvut\\run\\test";
	
	private static final String DELIMITER = "\\";
	
	private static final String TestClassFile001 = DELIMITER + "TestClassFile001.class";
	
    @Test
    public void testMain() throws Exception { // spu�t�n� pr�zdn� aplikace projde bez vyj�mky
        JVM.main(null);
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testDirectory() throws Exception{ // Spu�t�n� s odkazem na adres��
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH});
    }
    
    @Test(expected=java.lang.Exception.class)
    public void testWrongPath() throws Exception{ // Spu�t�n� s odkazem na neplatnou cestu
    	JVM.main(new String[] {"wrong path"});
    }
    
    @Test()
    public void testSimpleRightPath() throws Exception{ // Jednoduch� pu�t�n� s odkazem na platnou cestu
    	JVM.main(new String[] {new java.io.File( "." ).getCanonicalPath()+TEST_CLASSES_PATH + TestClassFile001});
    }
}
