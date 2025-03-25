/*
 * Jaimie Morris
 * Out of order program creates a divide and conquer algoritm that determines the total number of array elements that are out of orderr
 * 1. The run time would be O(n)
 * 2. In totoro solver the runtime was O(n^2) but after converting it to a one dimensional array it is O(n) therefore it is worth the time
 */

import java.util.*;

public class JaimieMorris_OutOfOrder {


	//method sorts and finds the total out of order in the array
	public static int outOfOrder(int[] data, int startLoc, int endLoc) {

		int totalOOO = 0;

		//if there is only 1 element then its in order so returns 0
		if(startLoc == endLoc)
			return 0;

		int mindex = (startLoc + endLoc)/2;


		Queue<Integer> leftQ = new LinkedList<Integer>();
		Queue<Integer> rightQ = new LinkedList<Integer>();

		totalOOO+= outOfOrder(data, startLoc, mindex) + outOfOrder(data, mindex+1, endLoc);

		//these two loops add the data from left and right side to left and right queue
		for(int i = startLoc;i<=mindex;i++) 
			leftQ.add(data[i]);

		for(int i = mindex+1;i<=endLoc;i++) 
			rightQ.add(data[i]);


		int place =startLoc;
		//while both are not empty compares front of both queues
		while(!leftQ.isEmpty() && !rightQ.isEmpty()) {

			//if left front is greater then adds one to out of order and adds first right element to array
			if(leftQ.peek()>rightQ.peek()) {
				totalOOO+= leftQ.size();

				data[place]=rightQ.remove();
				place++;
			}

			//else adds left element to queue
			else {
				data[place]=leftQ.remove();
				place++;
			}

		}	


		//these two loops empty out one of the queues into the array if the other is empty
		while(!rightQ.isEmpty()) {

			data[place]=rightQ.remove();
			place++;
		}

		while(!leftQ.isEmpty()) {

			data[place]=leftQ.remove();
			place++;
		}

		return totalOOO;


	}

}
