package fit.cvut.run.test;

public class TestClassFile001 {
	private static int privateInt;
	private static String privateString;
	private static int abc = 1;
	
	
	public static void main(String[] args) {
		privateInt = 123;
		privateString = "testovaci retezec";
		privateMethod();
		System.out.println(privateInt);
		System.out.println(privateString);
		System.out.println(publicMethod());
		System.out.println(abc);
	}
	
	private static void privateMethod(){
		privateInt = 456;
	}
	
	public static String publicMethod(){
		return "retezec 123456";
	}
}
