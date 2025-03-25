/*
 * Jaimie Morris
 * Hash table class that resolves collisions thru chaining
 */
import java.util.*;
import static java.lang.Math.*;

public class JaimieMorris_HashChaining<E> {

	private LinkedList<E>[] table;

	public JaimieMorris_HashChaining(int tblSize){

		table = new LinkedList[tblSize];
	}

	//adds item to hash table
	public boolean add(E item) {

		int index = Math.abs(item.hashCode()) % table.length;

		//if current spot is null create new linked list
		if(table[index]==null)
			table[index]=new LinkedList<E>();
		
		table[index].add(item);

		return true;
	}

	public boolean contains(E item) {
//iterates thru list and calls the linkedList class contain function
		for(int i = 0; i< table.length; i++) {
			if(table[i]!=null) {

				if(table[i].contains(item));
				return true;
			}

		}
		return false;


	}

	public boolean remove(E item) {
		//iterates thru list and calls the linkedList class remove function
		for(int i = 0; i< table.length; i++) {
			if(table[i]!=null) {

				if(table[i].remove(item));
				return true;
			}

		}
		return false;


	}


//adds all elements to a string to print
	public String toString() {
		String all = "";


		for(int i = 0; i< table.length; i++) {
			if(table[i]!=null) 
				all+=table[i];


			else
				all+="[null]";

		}

		return all;
	}

}

