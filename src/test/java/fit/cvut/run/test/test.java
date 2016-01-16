package fit.cvut.run.test;

public class test {
	public static void main(String[] args) throws Exception {
		A a = new B();
		a.run();
	}
}

class A {
	public void foo(){
		System.out.println("foo"); 
		bar();
	}
	public void bar(){
		System.out.println("bar");
	}
	public void run(){
		System.out.println("bug");
	}
}

class B extends A{
	public void foo(){
		System.out.println("foo2");
		bar();
	}
	public void bar(){
		System.out.println("bar2");
	}
	public void run(){
		super.foo();
	}
}




