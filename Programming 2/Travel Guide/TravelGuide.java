package programming2;
/*Jaimie Morris
 * Travel guide is a book and implements the Comparable interface to easily compare
 * travelguides sent from a client
 */

public class TravelGuide extends Book implements Comparable<TravelGuide>{
	private String destination; //destination country
	public TravelGuide(String t, double p, int q, String d) {
		super(t, p, q);
		destination=d;
	}

	
	public String toString() {
		return "Destination: "+ destination+" "+(super.toString());
	}
	
//compares destination with the Parameter destination
	public int compareTo(TravelGuide o) {
		
		return (destination).compareTo(o.destination);
		
	}

}
