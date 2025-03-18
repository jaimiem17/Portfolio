package programming2;

import java.util.*;
/*
 * Jaimie Morris
 * Array List 2 makes arrays instead of ints it can edit any type of Object
 * Result must be datatype object in order for it to compile because all types point to it
 */

public class MyArrayList2<E> extends MyArrayList implements Iterable<E> {
	private E[] set;
	private int total;

	public MyArrayList2() {
		super();
		set=(E[])new Object[10];
	}
	public MyArrayList2(int capacity) {
		super(capacity);
		set=(E[])new Object[capacity];
	}
	public MyArrayList2(MyArrayList2 other) {
		super(other);
		other= new MyArrayList2(set.length);
		for(int i=0;i<set.length;i++) {
			other.set[i]=set[i];
		}
	}//take in the number they want to add and add it on the end of the active nums unless there is no space then double the array
	public void add(Object element){

		if (total==set.length) {
			Object[] temp=(E[])new Object[set.length*2];
			for(int i=0;i<set.length;i++)
				temp[i]=set[i];
			set= (E[]) temp;
		}

		set[total]=(E) element;
		total++;

	}
	//creates a new array and adds the num they want at the index they want it then continues the rest of the other array
	public void add(int index, Object element){

		if (total==set.length) {
			Object[] temp=new Object[set.length*2];

			for(int i=0;i<set.length;i++)
				temp[i]=set[i];
			set=(E[]) temp;

		}

		for(int i=total;i>index;i--) {
			set[i]=set[i-1];

		}
		set[index]=(E) element;
		total++;
	}
	//goes thru the array and returns false if there is any non0 vbles inside
	public boolean isEmpty() {
		for (int i=0;i<set.length;i++) {
			if(set[i].equals(null)) 
				return false;
		}
		return true;
	}//new array adds everything from the other array except from the index they chose
	public int indexOf(Object element) {
		for (int i=0;i<total;i++) {
			if(set[i].equals(element))
				return i;
		}
		return -1;
	}
	public Object removeIndex(int index) {
		validIndex(index);
		Object eleRemoved=set[index];
		for(int i=index;i<total;i++) {
			set[i]=set[i+1];
		}
		total--;
		set[total]=0;
		return eleRemoved;
	}

	//finds index of element, new array adds everything from the other array except the index of that element
	public boolean removeElement(Object element) {
		int index=indexOf(element);
		if(index==-1)
			return false;

		Object ele=removeIndex(index);
		if(ele==element)
			return true;

		return false;
	}//sets index to the element the client wants
	public Object set(int index, Object element) {
		validIndex(index);
		Object orig=set[index];
		set[index]=(E) element;
		return orig;
	}
	public int size() {
		return total;
	}
	private void validIndex(int index) {
		if(index>set.length-1||index<0) 
			throw new IndexOutOfBoundsException("Invalid Index");

	}
	public String toString() {
		String fSet="{";
		for (int i=0;i<set.length;i++) {
			if(!set[i].equals(null))
				fSet+=set[i]+",";
		}
		int l=fSet.length();
		if(fSet.substring(l-1,l).equals(",")) {
			fSet=fSet.substring(0,l-1);
		}
		return fSet+"}";
	}

	public Iterator<E> iterator() {
		return new Iterator<E>(){
			int nextLoc = 0;

			boolean removeOK = false;

			public boolean hasNext() {
				nextLoc++;
				return nextLoc<total;
			}
			
			public E next() {
				if(nextLoc>total) 
					throw new NoSuchElementException("Element Does Not Exist");
				
				else {
					nextLoc++;
					return set[nextLoc];
					
				}	
	
			}

			public void remove(){
				if(removeOK==false) 
					throw new IllegalStateException("must call next before calling remove again");
				removeElement(next());
				removeOK=true;
			}

		};

	}
	
	
}




