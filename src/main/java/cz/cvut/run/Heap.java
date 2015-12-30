package cz.cvut.run;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import cz.cvut.run.stack.Reference;
import cz.cvut.run.stack.StackElement;
import java.util.UUID;

import org.apache.log4j.Logger;

public class Heap {
	private static final Logger log = Logger.getLogger(Heap.class);
	private Set<Reference> heap = new HashSet<Reference>();
	private int counter = 0;
	private static final int MAX_RUN = 100;
	
	public void addToHeap(Reference o){
		log.debug("Added to heap: " + o.toString());
		heap.add(o);
	}
	
	public void removeFromHeap(Reference o){
		log.debug("Remove from heap: " + o.toString());
		heap.remove(o);
	}
	
	public void runGC(Frame f){
		String uuid = UUID.randomUUID().toString();
		counter++;
		if (counter == MAX_RUN){
			counter = 0;
			log.info("GC is running!");
			while (f!= null){
				Stack<StackElement> os = f.operandStack;
				for(int i=0; i<os.size(); i++){
					StackElement s = os.get(i);
					if (s instanceof Reference){
						((Reference) s).mark = uuid;
					}
				}
				f = f.parent;
			}
			
			Object[] arr = (Object[]) heap.toArray();
			for(int i=0; i<arr.length; i++){
				Reference r = (Reference) arr[i];
				if (r.mark == null || !r.mark.equals(uuid)){
					heap.remove(r);
				}
			}
		}
	}
	
	public int getSize(){
		return heap.size();
	}
	
	
}
