/*
 * Jaimie Morris
 * Hash table class that resolves collisions thru probing
 */
import java.util.*;
import static java.lang.Math.*;

public class JaimieMorris_HashProbing<E> {

	private E[] table;
	private Probeable probeFunction;

	public JaimieMorris_HashProbing(int tblSize, Probeable p){

		table = (E[])new Object[tblSize];
		probeFunction = p;
	}
	
	//adds items to the hash table
	public boolean add(E item) {

		int index = Math.abs(item.hashCode()) % table.length;
		
		//if null sets index to item if not finds a new index that is null
		if(table[index]==null) {
			table[index] = item;
		}
		else {
			while(table[index]!=null)
				index= probeFunction.probe(index);

			table[index] = item;
		}
		
		return true;
		
	}
	//returns true if the hashtable has the item
	public boolean contains(E item) {
		//iterates thru whole list and returns true if found else false
		for(int i = 0; i< table.length; i++) {
			if(table[i]==item)
				return true;
		}
		
		return false;
	}
	
	//removes item from hash table
	public boolean remove(E item) {
		//iterates thru table and if found sets it to null if not found removes it
		for(int i = 0; i< table.length; i++) {
			if(table[i]==item) {
				table[i] = null;
				return true;
			}
		}
		
		return false;
	}
	
	//adds all elements to a string to print
	public String toString() {
		String all = "["+table[0];
		
		for(int i = 1; i< table.length; i++) {
			all+=", "+table[i];
		}
		
		return all+"]";
	}


}


