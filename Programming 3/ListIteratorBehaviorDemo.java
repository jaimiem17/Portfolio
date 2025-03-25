import java.util.*;

//all Collections have an Iterator
//all Lists have a ListIterator
public class ListIteratorBehaviorDemo {

	public static void main(String[] args){

		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(4);
		list.add(8);
		list.add(15);
		list.add(16);

		//next and hasNext will work exactly the same as
		//the Iterator your wrote
		ListIterator<Integer> iter = list.listIterator();	
		iter.add(23);
		System.out.println(iter.next());        //prints 4
		System.out.println(iter.previous());     // also prints 4
		System.out.println(iter.hasPrevious());  //prints false b/c at start of list

		iter.set(5000);
		System.out.println(iter.next());   
	//	System.out.println(iter.previous());  
		iter.remove();
		System.out.println(iter.hasNext());
		
		//-------------- Example 2 -----------------
		/*run all the way off the list
		while(iter.hasNext())
			iter.next();

		System.out.println(iter.previous()); //hops back on the list and prints 16

		// ------------ Example 3 ------------------
		// ASSUME NEXT hasn't been called yet (or an list is empty)
		System.out.println(iter.previous());  // throws NoSuchElement

		//------------ Example 4 -------------------
		//Remove still deletes a node but the node to delete depends
		//on whether or not next or previous was called


		//----------- Example 5 ------------------
		//Set changes the data of a node.  Node changed depends
		//on whether or not next or previous was called.  Same rules as remove
		
		iter.set(5000);

		//---------- Example 6 ------------------
		//add does not care if next/prev was called.  Add just cares
		//about the position of iterLoc.  Add inserts BEFORE the position
		// of iterLoc

		//Assume back at the beginning of the list
		iter.next();
		iter.add(1000);   //adds node in between 4 and 8

		// ---------- Example 7 --------------------
		Assume back at the beginning of the list
		iter.add(1000);   //1000 is the new front
		iter.add(2000);   //2000 is in between 1000 and 4

		//--------- Example 8 --------------------
		//Assume an EMPTY list and try calling iter.add
		//this will work, however iterLoc will still be null

		//-------- Example 9 ---------------------
		iter.next();
		iter.add(1000);
		iter.remove();   //throws illegal state exception. add "clears out"
		//any knowledge of a next/previous call
		*/
		System.out.println(list);
	}
}
