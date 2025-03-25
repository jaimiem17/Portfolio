
public class PointPair {

	private Point p1;
	private Point p2;
	
//constructor creates new points
	public PointPair( Point s1, Point s2){

		p1 = new Point(s1);
		p2 = new Point(s2);
	}
	
	//constructor copys points from p in
	public PointPair( PointPair p){

		p1 = new Point(p.p1);
		p2 = new Point(p.p2);
	}
	
	//sets points to new points
	public void setPoints(Point s1, Point s2){

		p1 = s1;
		p2 = s2;
	}

	//returns the distance
	public double distance(){

		return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2)+ Math.pow(p1.getY()-p2.getY(), 2));
	}


	//checks if the currents points are equal to the calling object points
	public boolean equals(Object o){

		if(!(o instanceof PointPair))
		    return false;	
	
		PointPair  p = (PointPair)o;

		//two if statements check if the points are the same even if theyre swapped
		if(p.p1.equals(p1)&& p.p2.equals(p2))
			return true;

		if(p.p2.equals(p1)&& p.p1.equals(p2))
			return true;

		return false;
	}
	
	//returns sum of hashcodes
	public int hashCode(){
		return p1.hashCode() + p2.hashCode();
	}
	
	//tostring method
	public String toString(){

		return "{ "+  p1.toString() + ", " + p2.toString()+ " }";
	}
	
}
