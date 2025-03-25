

import java.util.*;

public class Point {
	
	private int x; 
	private int y; 
	
	
	//constructor that creates points always at int 0
	public Point(){

		x = 0;
		y = 0;
	}
	
	//constructor that takes in an int to create points
	public Point(int someX, int someY){

		x = someX;
		y = someY;
	}
	
	//copy constructer
	public Point(Point p){

		x = p.x;
		y = p.y;
	}
	

	//finds the distance of the two points
	public double distance(Point p){

		return Math.sqrt(Math.pow(p.x-x, 2) + Math.pow(p.y-y, 2));
	}
	
	//get method for X
	public int getX(){

		return x;
	}
	
	//get method for Y
	public int getY(){

		return y;
	}
	
	//sets x and y to the sent in parameter
	public void set(int someX, int someY){

		x = someX;
		y = someY;
	}
	
	//checks if the current points are equal to another point
	public boolean equals(Object o){
	
		if(!(o instanceof Point))
			return false;

		Point p = (Point)o;
		return (x == p.x && y == p.y);
	}
	
	//returns the hashcode of x and y
	public int hashCode(){
		return 31*x +y;
	}
	
	
	//to string method to print x and y
	public String toString(){

		return "[" + x + ", " + y + "]";
	}
	
}
