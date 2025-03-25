

import java.util.*;
//You may assume that all Points are unique.
public class PointSet extends ArrayList<Point> {

	//private Comparator<Point> compareMethod;

	//constructor
	public PointSet(){

	}
	//constructotr that calls the superclass
	public PointSet(PointSet l){

		super(l);
	}

	//sets points to a random number within a given range then adds it to the set
	public PointSet(int xMax, int yMax, int num){

		Random r = new Random();

		for(int i = 1; i <= num; i++){

			int x = r.nextInt(xMax + 1);
			int y = r.nextInt(yMax + 1);
			Point toAdd = new Point(x,y);

			//if the set already has this pair do not add and make the loop run another time
			if(!contains(toAdd))
				this.add(new Point(x,y));
			else
				i--;

		}
	}

	////makes a new set with a copy of this sets elements
	public PointSet union(PointSet set){

		PointSet ret = new PointSet(set);
		for(Point p: this){
			ret.add(p);
		}
		return ret;
	}

	//adds all of the set and this elements that are the same to ret
	public PointSet intersection(PointSet set){

		PointSet ret = new PointSet();

		//loops thru the current set and adds the elements that repeat in the parameter set to the ret set
		for(Point p : this){
			if(set.contains(p))
				ret.add(p);
		}
		return ret;
	}


	/*
		using a brute force approach to determine the two points in the set that
		are the smallest distance apart. Returns as a PointPairSet (in case there
		are multiple point pairs that are the same distance apart). The brute
		force approach simply uses a nested loop structure to examine every
		possible combination.
	 */


	public PointPairSet closestPointBF(){
		PointPairSet smallest = new PointPairSet();

		PointPair tempSmallest = new PointPair(this.get(0), this.get(1));
		double curSmallest = this.get(0).distance(this.get(1));

		//nested loop loops thru the set to find pairs
		for(int i =0;i<size();i++) {
			for (int j =i+1;j<size();j++) {
				
				//if the distance is smaller than the current distance and not equal to 0 (which would mean the two comparing points were the same)
				if(this.get(i).distance(this.get(j))<curSmallest && this.get(i).distance(this.get(j))!=0) {
					//wipes the current PointPairSet clean if any equals were added prior
					smallest.clean();
					
					//tempSmallest represents the current smallest points while curSmallest represents the current smallest distance
					tempSmallest.setPoints(this.get(i),this.get(j));
					curSmallest = this.get(i).distance(this.get(j));
				}
				
				//if theyre equal add it to the return variable smallest
				else if(this.get(i).distance(this.get(j))==curSmallest)
					smallest.add(new PointPair(this.get(i),this.get(j)));
			}
		}


		smallest.add(tempSmallest);

		return smallest;

	}
	
	public PointPairSet closestPointsDC() {
		
	}
	
	


	// sorts by X if XComparator is passed in
	// sorts by Y if YComparator is passed in
	public void sortPoints(Comparator<Point> compareMethod){

		Collections.sort(this,compareMethod);

	}


}
