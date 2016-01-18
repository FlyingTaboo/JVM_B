package cz.cvut.run;
//singleton
public class Runtime {
	private Runtime runtime = null;
	private Runtime(){
	}
	
	public Runtime getInstance(){
		if (this.runtime == null){
			this.runtime = new Runtime();
		}
		return this.runtime;
	}
}
