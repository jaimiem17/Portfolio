package programming2;

public class Magazine extends Book {
	private String genre; //genre! 
	public Magazine(String t, double p, int q, String g) {
		super(t, p, q);
		genre=g;
	}
	
	public String toString() {
		return ((super.toString())+" Genre: "+ genre);
	}

}
