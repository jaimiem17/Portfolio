package programming2;

public class Client_LoadedDeck {

	public static void main(String[]args) {
		Card ex=new Card(SUIT.DIAMOND, RANK.THREE);
		Deck start=new Deck();
		LoadedDeck play= new LoadedDeck();
		System.out.println(play.deal());
		play.addCard(ex);
		System.out.println(play.findTotal());
	}

}
