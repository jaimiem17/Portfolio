import java.util.*;
/*
 * Jaimie Morris 9/1/21 pd 4
 * Doubly Linked List project creates a linked list with multiple functions 
 * Add on  project creates a list iterator that iterates the list and manipulates it when the client calls different methods
 */
public class JaimieMorris_DoublyLinkedList<E> {

	private enum STATUS {NONE, NEXT, PREVIOUS};

	private ListNode front;
	private ListNode end;
	private int numElements;

	public boolean isEmpty() {

		//if front is null than the whole list is empty
		return front == null;

	}

	public void addFront(E item){
		//adds one to the total elements
		numElements++; 

		//adds a new node to the front before checking to see if there are more in the list already
		front = new ListNode(null, item, front);
		//if there is only one node in the list it sets both front and end to itself and if there is more than 1 it adds the node by setting front equal to it and the old front's previous to it
		if(size()==1)
			end=front;
		else
			front.next.previous=front;


	}

	public void addAfter(int index, E item) {

		//if the index is greater than the amount of elements inside the list
		if(index>=size()||index<0) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}

		//if it wants to add to the last place than call the addLast function
		if (index==size()-1)
			addLast(item);

		else {
			//temp node to loop through without adjusting the list permenantly
			ListNode current=front;

			//loops thru list until it reaches where to add
			for(int i=0;i<index;i++) {
				current=current.next;
			}

			current.next = new ListNode(current, item,current.next);
			current.next.previous=current.next;
		}
		numElements++; 
	}
	public void addLast(E item) {
		//if list has no elements then addFront
		if(size()==0)
			addFront(item);


		//sets the old end's next to the new end and the new ends previous to the old end while also setting end to the new end
		else {
			end.next = new ListNode(end, item,null);
			ListNode tempEnd = end.next;
			tempEnd.previous = end;
			end = tempEnd;
			tempEnd = null;
		}

		numElements++; 
	}

	public int size() {
		//returns how many elements are in the list
		return numElements;

	}


	public void removeFirst(){
		//if no elements inside the list
		if(isEmpty())
			return;
		if(size()==1) {
			front=null;
			end=null;
		}

		ListNode toRem = front;
		front = front.next;
		toRem.next.previous = null;
		toRem.next = null;

		numElements--; 
	}

	public void remove(E item) {
		//if no elements inside the list
		if(isEmpty())
			return;

		ListNode current=front;
		int index=0;

		//finds the index of the item and if the index is greater than the size it means the item is not in the list.
		while(!current.data.equals(item)){
			current = current.next;
			index++;
			if(index>size())
				throw new NullPointerException("Invalid Item");
		}
		//if it is the last element then call the removeLast function and if it is 0 then call removeFirst
		if(index==size()-1)
			removeLast();
		else if(index==0)
			removeFirst();
		else {

			current=front;

			//creates a temprary vble to point to list so that the nexts and previouses can be set to their new spot and toRem can be severed
			ListNode toRem = current.next;
			current.next = toRem.next;
			toRem.next=null;
			toRem.previous=null;
			current.next.previous=current;
		}

		//subtracts one from total elements
		numElements--; 
	}
	public void removeLast() {
		//if no elements inside the list
		if(isEmpty())
			return;

		//deletes the single element if only 1
		if(size()==1)
			front=null;
		else {
			//adds a temporary end then manipulates the real end's next and previous
			ListNode tempEnd= end;
			end.previous.next=null;
			end=end.previous.next;
			tempEnd.previous=null;


			tempEnd=null; 
		}

		numElements--; 
	}

	public E get(int index) {
		//if the index is greater (or equal because the index of the last element is one less) than the size of the list throw exception
		if(index>=size()) 
			throw new IndexOutOfBoundsException("Invalid Index");

		//if the index is the first element then it is not necessary to loop thru the list
		if (index==0)
			return front.data;

		//creates a temp vble so it can run thru the list without manipulating the list
		ListNode current=front;

		//uses a loop to switch to the next element until it reaches the index
		for(int i=1;i<=index;i++) {
			current=current.next;
		}

		return current.data;
	}
	public void set(int index, E element){
		//if no elements inside the list
		if(isEmpty())
			return;
		//if the index is greater (or equal because the index of the last element is one less) than the size of the list throw exception
		if(index>=size()) 
			throw new IndexOutOfBoundsException("Invalid Index");

		//finds the index of the element then adds it in the list after the index it will be swapped with then that node is deleted
		E toSwitch = get(index);
		addAfter(index, element);
		remove(toSwitch);

	}

	//ListNode class is an innerclass that is used to create the ListNodes
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



	public ListIterator<E> iter(){

		return new ListIterator<E> (){
			private ListNode iterLoc=front;
			private STATUS curStat=STATUS.NONE;

			public void add(E item) {
				//if there is only one node in the list it sets both front and end to itself and if there is more than 1 it adds the node by setting front equal to it and the old front's previous to it

				if(isEmpty()||iterLoc==front) {
					front = new ListNode(null, item, front);

					if(size()==1)
						end=front;
					else
						front.next.previous=front;
					return;
				}
				iterLoc.previous.next= new ListNode(iterLoc.previous, item, iterLoc);
				iterLoc=iterLoc.previous.next;
			}

			public boolean hasNext() {
				return iterLoc!=null;
			}

			public boolean hasPrevious() {
				//if the list is not empty or the current loc is not at the first listnode then hasPrevious is true
				if(isEmpty()||iterLoc==front)
					return iterLoc.previous!=null;
				return true;
			}

			public E next() {
				//if there is a next then return becomes the current data and then thd current data becomes current's next. Status is also changed
				if(!hasNext()) {
					throw new NoSuchElementException();
				}

				E toReturn=iterLoc.data;
				iterLoc=iterLoc.next;
				curStat=STATUS.NEXT;
				return toReturn;
			}

			public int nextIndex() {//ignore
				return 0;
			}

			public E previous() {
				//if there is a previous then return becomes the current data's previous and then the location becomes current's previous. Status is also changed

				E toReturn;
				if(!hasPrevious()) {
					throw new NoSuchElementException();
				}

				if(iterLoc==null) {
					toReturn=end.data;
					iterLoc=end;
				}
				else {
					toReturn=iterLoc.previous.data;
					iterLoc=iterLoc.previous;
					curStat=STATUS.PREVIOUS;
				}
				return toReturn;
			}

			public int previousIndex() {//ignore
				return 0;
			}

			public void remove() {
				//first figures out which to removed based on the status. Then changes the listNode's previous and nexts around the toRemove to sever the connections
				ListNode toRemove;
				if(curStat==STATUS.NEXT) 
					toRemove=iterLoc.previous;

				else if(curStat==STATUS.PREVIOUS) {
					toRemove=iterLoc;

					if(toRemove==end) {
						removeLast();
						return;
					}
				}

				else
					throw new IllegalStateException();

				if(toRemove==front)
					removeFirst();
				else {
					toRemove.previous.next=toRemove.next;
					toRemove.next.previous=toRemove.previous;
					toRemove.next=null;
					toRemove.previous=null;
				}
			}

			public void set(E item) {
				//first figures out which to set based on the status. Then either changes the current's previous data or the current data to the new data
				if (curStat==STATUS.NEXT)
					iterLoc.previous.data=item;
				else if(curStat==STATUS.PREVIOUS)
					iterLoc.data=item;
				else
					throw new IllegalStateException();

			}

		};

	}




}



