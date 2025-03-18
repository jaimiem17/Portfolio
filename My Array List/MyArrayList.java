/*
 * Jaimie Morris
 * This program provides methods that change the array for the client
 */
package programming2;

public class MyArrayList {
	private int[] set;
	private int total;

	public MyArrayList() {
		set = new int[10];
	}
	public MyArrayList(int length) {
		set = new int[length];
	}
	//copy constructor w/ deep copy
	public MyArrayList(MyArrayList other) {
		other= new MyArrayList(set.length);
		for(int i=0;i<set.length;i++) {
			other.set[i]=set[i];
		}
	}//take in the number they want to add and add it on the end of the active nums unless there is no space then double the array
	public void add(int element){
		if (total==set.length) {
			int[] temp=new int[set.length*2];
			for(int i=0;i<set.length;i++)
				temp[i]=set[i];
			set=temp;
		}
		set[total]=element;
		total++;
	}
	//creates a new array and adds the num they want at the index they want it then continues the rest of the other array
	public void add(int index, int element){
		if (total==set.length) {
			int[] temp=new int[set.length*2];
			for(int i=0;i<set.length;i++)
				temp[i]=set[i];
			set=temp;
		}
		for(int i=total;i>index;i--) {
			set[i]=set[i-1];
		}
		set[index]=element;
		total++;
	}
	//goes thru the array and returns false if there is any non0 vbles inside
	public boolean isEmpty() {
		for (int i=0;i<set.length;i++) {
			if(set[i]==0) 
				return false;
		}
		return true;
	}//new array adds everything from the other array except from the index they chose
	public int indexOf(int element) {
		for (int i=0;i<total;i++) {
			if(set[i]==element)
				return i;
		}
		return -1;
	}
	public int removeIndex(int index) {
		validIndex(index);
		int eleRemoved=set[index];
		for(int i=index;i<total;i++) {
			set[i]=set[i+1];
		}
		total--;
		set[total]=0;
		return eleRemoved;
	}

	//finds index of element, new array adds everything from the other array except the index of that element
	public boolean removeElement(int element) {
		int index=indexOf(element);
		if(index==-1)
			return false;

		int ele=removeIndex(index);
		if(ele==element)
			return true;

		return false;
	}//sets index to the element the client wants
	public int set(int index, int element) {
		validIndex(index);
		int orig=set[index];
		set[index]=element;
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
			if(set[i]!=0)
				fSet+=set[i]+",";
		}
		int l=fSet.length();
		if(fSet.substring(l-1,l).equals(",")) {
			fSet=fSet.substring(0,l-1);
		}
		return fSet+"}";
	}
}


