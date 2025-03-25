/*
 * Jaimie Morris
 * creates an array based priority queue and add, removes, peeks following the influence of a heap
 */
import java.util.*;

public class JaimieMorris_ArrayHeap<E extends Comparable<E>> implements PriorityQueue<E>{
	private E[] data;
	private int numElements;

	public JaimieMorris_ArrayHeap(){

		data = (E[])new Comparable[10];
		numElements = 0;
	}

	//returns the highest priority element or throws exception
	public E peek() {

		if(numElements==0)
			throw new NoSuchElementException ("No element");

		return data[0];
	}

	//returns if array is empty
	public boolean isEmpty() {

		return numElements==0;

	}

	public void add(E item) {

		//if full
		if(numElements>data.length)
			doubleArray();

		
		//if empty just add to front
		if(isEmpty()) {
			data[0]=item;
			numElements++;	
		}

		//sets the last index to the item then checks its parent
		else {
			data[numElements]=item;
			int curInd = numElements;
			numElements++;

			//find parent
			int parentInd= (numElements/2)-1;

			//while the parent is greater than they are they swap
			while((item).compareTo(data[parentInd])<0) {
				data[curInd]=data[parentInd];
				data[parentInd]=item;

				parentInd= (parentInd/2);	

			}

		}




	}


	//helper doubles array if full
	private void doubleArray(){
		E[] doubleData = (E[])new Comparable[(data.length)*2];

		int i=0;
		for(E items: data) {
			doubleData[i]=items;
			i++;
		}

		data=doubleData;
	}
	
	
//removes element with highest pri
	public E remove() {
		if(numElements==0)
			throw new NoSuchElementException ("Empty");
		E toRemove = data[0];
		int current=0;
		int smaller;

		//until the current last element is moved (bottom left or right)
		while(data[numElements-1]!=null||data[numElements-2]!=null) {

			//finds the smallest child and swaps it with that--> continues to swap until no children left

			//if left is null checks if right is as well
			if(data[current*2+1]==null) {
				if(data[current*2+2]==null) {
					data[current]=null;
					numElements--;
					return toRemove;
				}
				//if right isnt then right becomes the smallest
				smaller = current*2+2;
			}

			//else left becomes the smallest
			else if(data[current*2+2]==null) {
				smaller = current*2+1;
			}

			//if both are not null then compares to find smallest
			else if(data[current*2+1].compareTo(data[current*2+2])<0) 
				smaller=current*2+1;
			else
				smaller=current*2+2;

			//swaps removeable data w the item smaller then sets to null
			data[current]=data[smaller];
			data[smaller]=null;

			current = smaller;
		}
		
		return toRemove;
	}

}
