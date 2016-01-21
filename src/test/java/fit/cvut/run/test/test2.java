package fit.cvut.run.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class test2 {
	public static void main(String[] args) throws Exception {
		write();
		read();
	}
	
	private static void write() throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("D:\\the-file-name.txt");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
	}
	
	private static void read() throws IOException{
		FileReader reader = new FileReader("D:\\the-file-name.txt");
		char[] buffer = new char[32];
		reader.read(buffer);
		reader.close();
		System.out.println(buffer);
	}
}




