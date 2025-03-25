/*
 * Jaimie Morris
 * Node Heap program implements a priority queue in node heap form
 */
import java.util.*;

public class JaimieMorris_NodeHeap<E extends Comparable<E>> implements PriorityQueue<E>{

	private TreeNode root;
	private int numElements;

	public E peek() {
		if(isEmpty())
			throw new NoSuchElementException ("Empty");

		return root.value;
	}

	//returns if tree is empty
	public boolean isEmpty() {
		return root == null;
	}

	//adds item to tree
	public void add(E item) {
		numElements++;

		//if empty adds new node to tope
		if(isEmpty()) {
			root = new TreeNode(item, null, null, null);
		}

		//finds spot to add the new node using binary 
		else {

			String biNum = Integer.toBinaryString(numElements);
			biNum= biNum.substring(1);

			TreeNode current = root;

			for(int i = 0; i<biNum.length()-1;i++) {


				String place = biNum.substring(0,1);

				if(place.equals("0"))
					current = current.left;
				else
					current = current.right;

				biNum= biNum.substring(1);

			}

			biNum = biNum.substring(biNum.length()-1);

			TreeNode newNode = new TreeNode(item, null, null, current);
			if(biNum.equals("0"))
				current.left =  newNode;
			else
				current.right = newNode;

			TreeNode newParent = newNode.parent;

			//while the item is smaller than its parent swaps it until its in the current place
			while((item).compareTo((newParent.value))<0) {

				if(newNode.parent != null) {
					TreeNode temp = newParent;

					E tempVal = temp.value;
					newParent.value = newNode.value;
					newNode.value = tempVal;

					if(temp.parent!=null)
						newParent = temp.parent;

					newNode = temp;

				}

			}

		}

	}

	//helper method finds the the smallest node in a parent/child relationship
	private TreeNode getMin(TreeNode r) {
		TreeNode min = r;

		if(r.left !=null) {
			if(r.left.value.compareTo(min.value)<0) 
				min = r.left;

		}
		if(r.right !=null) {
			if(r.right.value.compareTo(min.value)<0) 
				min = r.right;

		}

		return min;
	}

	//helper method recursively finds the r nodes right most successor in order to swap w/ root for remove
	private TreeNode lastHelper(TreeNode r) {

		//if empty return null
		if(r==null) 
			return null;

		//if left is null return top node
		if(r.left==null)
			return r;

		if(r.right==null)
			return lastHelper(r.left);

		//else returns the rightmost node
		return lastHelper(r.left);

	}

	//removes highest priority node
	public E remove() {		
		if(isEmpty())
			throw new NoSuchElementException ("Empty");

		E toRem = root.value;
		numElements--;

		TreeNode toSwitch = lastHelper(root.left);
		
		//swaps priority node w bottommost node
		E temp = root.value;
		root.value = toSwitch.value;
		toSwitch.value = temp;			

		//finds which side the kids to be removed is left or right using helper method
		if(isLeftKid(toSwitch.parent, toSwitch)) {
			toSwitch.parent.left = null;
		}
		else
			toSwitch.parent.right = null;	

		//loops while the item at the top is bigger than its children and swaps it
		while(toSwitch!=null) {
			toSwitch = null;		

			toSwitch = getMin(root);

			if(toSwitch.equals(root)) {
				return toRem;
			}

			temp = root.value;
			root.value = toSwitch.value;
			toSwitch.value = temp;

		}

		return toRem;
	}

	//helper method to tell which kid is the kid to be removed
	private boolean isLeftKid(TreeNode parentNode, TreeNode removeNode) {
		if(parentNode.left.equals(removeNode))
			return true;

		return false;

	}


	public class TreeNode{

		private E value;
		private TreeNode left;
		private TreeNode right;
		private TreeNode parent;

		public TreeNode (E v, TreeNode l,TreeNode r, TreeNode p ) {
			value = v;
			left = l;
			right = r;
			parent = p;
		}
	}



}
