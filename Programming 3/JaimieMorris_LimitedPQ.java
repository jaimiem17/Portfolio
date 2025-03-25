import java.util.*;


public class JaimieMorris_LimitedPQ<E extends Comparable <E>> {

	private ListNode front;
	private ListNode end;
	private int numElements;
	private int MAX_ELEMENTS;


	public JaimieMorris_LimitedPQ(int m){

		MAX_ELEMENTS = m;
		numElements = 0;

	}

	//if no elements
	public boolean isEmpty() {

		return numElements == 0;

	}

	//adds in sorted linked list
	public E add(E item) {

		if(isEmpty())
			front = new ListNode(null, item, null);


		//if full replaces item w lowest priority
		else if(numElements>=MAX_ELEMENTS) {

			E toRet = end.data;

			//if the priority of the item is worse than the current end
			if (toRet.compareTo(item)>=0) {
				return item;
			}
			//else returns end
			ListNode node = new ListNode(end.previous, item, null);
			end.previous.next =  node;
			end.previous = null;
			end = node;

			return toRet;


		}
		
		//finds index to add and adds
		else {
			ListNode current = front;

			//while current data comes before the item
			while((current.data).compareTo(item)<0) 
				current=current.next;

			ListNode node = new ListNode(current.previous, item, current);
			current.previous.next =  node;
			current.previous = node;
		}

		numElements++; 
		return null;
	}

	//removes front item (highest pri)
	public E remove() {
		if(isEmpty())
			throw new NoSuchElementException("nothing to remove");

		//if only 1 element than whole list becomes null
		if(numElements==1) {
			front=null;
			end=null;
		}

		ListNode toRem = front;
		front = front.next;
		toRem.next.previous = null;
		toRem.next = null;

		numElements--; 

		return toRem.data;

	}

	//returns front item
	public E peek() {
		return front.data;
	}

	public class ListNode{

		private ListNode previous;
		private E data;
		private ListNode next;


		public ListNode(ListNode p, E d, ListNode n) {
			previous = p;
			data = d;
			next = n;

		}
	}

}
