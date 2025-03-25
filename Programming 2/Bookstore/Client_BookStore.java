package programming2;

public class Client_BookStore {

	public static void main(String[] args) {
		BookStore store= new BookStore();
		Book one=new Book("Harry Potter", 12, 3);
		Book two=new Book ("One of Us", 10, 7);
		Book three=new Book("Unbreakable", 7, 3);
		
		TravelGuide guide = new TravelGuide("Hawaii", 6, 2, "Honululu");
		store.add(one);
		store.add(one);
		store.add(guide);
		store.add(two);
		store.display();
		System.out.println(store.sell("Harry Potter", 3));
		store.add(three);
		
		store.display();
		
		System.out.println(store.search("Harry Potter"));
		store.remove("Unbreakable");
		
		store.display();
		
		System.out.println(guide.toString());
	}

}
