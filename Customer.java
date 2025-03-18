package programming2;


public class Customer {
	private String name;
	private Movie mov;
	private Time time;

	public Customer(String n, Movie m, Time t) {
		name=n;
		mov=m;
		time=t;
	}
	public String toString() {
		return ("Name: \t\tArrival Time: \n" +name+ "\t\t"+time.toString()+"\n\n"+mov.toString());//toString method
	}
	
	//accessors to easily get
	public String getName() {
		return name;
	}
	public Time getTime() {
		return time;
	}
	public Movie getMovie() {
		return mov;
	}

}
