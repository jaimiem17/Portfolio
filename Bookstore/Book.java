package programming2;

public class Book {
	private String title;
	private double price;
	private int quantity;

	public Book(String t, double p, int q) {
		title = t;
		price = p;
		quantity = q;
	}
	//copy constructor
	public Book(Book other) {
		title=other.title;
		price=other.price;
		quantity=other.quantity;
	}

	//accessor methods below
	public String getTitle() {
		return title;
	}
	public double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}

	public int compareTo(Book other) {
		return (title.compareTo(other.title));
	}
	
	//mutator method
	public void setQuantity(int qtyAdded) {
		quantity+=qtyAdded;
	}

	public String toString() {
		return ("Title: "+title+" Price: $"+ price+"0 Copies in store: "+quantity);
	}
}
