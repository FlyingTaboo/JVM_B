package cz.cvut.run.test;

import cz.cvut.run.Application;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ApplicationTest {

    @Test
    public void testMain() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Application.main(null);
        assertEquals("Hello", outContent.toString());
        System.setOut(null);
    }

    @Test
    public void testAdd() throws Exception {
        Application a = new Application();
        assertEquals(3, a.add(1, 2));
    }
}
