//This represents a small set of point pairs

import java.util.*;


public class PointPairSet extends HashSet<PointPair> {

	//constructor
	public PointPairSet(){
		
	}
	
	//constructer that calls the superclass
	public PointPairSet( PointPairSet s){

		super(s);
	}
	
	//goes thru removes everything from the iterated set
	public void clean(){
		
		Iterator iter = this.iterator();
		
		//loops thru the set with an iterator and removes each element
		while(iter.hasNext()){
			iter.next();
			iter.remove();
		}
	}
	
	//adds everything from the sent in set to the current set
	public void addAll(PointPairSet other){
		
		Iterator<PointPair> iter = other.iterator();
		
		while(iter.hasNext())
			add(iter.next());
	}

	//get method returns the calculated distance of the points
	public double getDistance(){
		Iterator<PointPair> iter = this.iterator();
		return iter.next().distance();
	} 

}
