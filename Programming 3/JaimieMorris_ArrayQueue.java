/*
 * Jaimie Morris
 * Implements the functions of a queue from the queue interface
 */
import java.util.*;
public class JaimieMorris_ArrayQueue <E>  {

	private E[] data = (E[]) new Object [10];
	private int frontLoc=0;
	private int addLoc=0;
	private int numElements=0;

	public boolean isEmpty() {
		return numElements==0;
	}

	//adds item to the array
	public void add(E item) {
		
		//if the array is full
		if((addLoc==frontLoc&&!isEmpty())||addLoc==data.length) 
			data = unwrapDub(data);

		System.out.println(data.length);
		data[addLoc]=item;

		if(addLoc==data.length)
			addLoc=0;
		else
			addLoc++;

		numElements++;

	}

	public E peek() {
		
		//returns the front data
		if(!isEmpty())
			return data[frontLoc];

		throw new NoSuchElementException();
	}

	public E remove() {
		if(!isEmpty()) {
			E toRem = data[frontLoc];
			
			//if the front is at the end
			if(frontLoc==data.length-1)
				frontLoc=0;
			else
				frontLoc++;
			numElements--;
			return toRem;
		}
		throw new NoSuchElementException();
	}

	//helper that unwraps and doubles the array
	private E[] unwrapDub(E[] data) {
		E[] newQ = (E[]) new Object [numElements*2];
		int j=frontLoc;

		//loops thru all live elements and adds them to new array
		while(j<numElements) {
			for(int i=0;i<data.length;i++) {

				
				//if j is at the end of the array it loops to the front until all live elements are in the new array
				if(j==data.length&&j<numElements) 
					j=0;

				newQ[i]=data[j];
				j++;
			}
		}
		frontLoc=0;
		addLoc=numElements;
		return newQ;
	}
	
	
	
}
