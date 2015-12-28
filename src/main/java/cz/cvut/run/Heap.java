package cz.cvut.run;

import java.util.HashSet;
import java.util.Set;

public class Heap {
	
	private Set<Object> heap = new HashSet<Object>();
	
	public void addToHeap(Object o){
		heap.add(o);
	}
	
	public void removeFromHeap(Object o){
		heap.remove(o);
	}
	
	public boolean isPresent(Object o){
		return heap.contains(o);
	}
	
	public void runGC(){
		//TODO
	}
	
}
