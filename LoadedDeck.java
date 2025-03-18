package programming2;
/*
 * Jaimie Morris
 * Practice program uses functions to, if in a black jack game, keep the player from reaching 21
 */

public class LoadedDeck extends Deck {
	private Card[] playersCards=new Card[0];
	
	public LoadedDeck() {
		super();
	}
	
	//this function uses a while loop to check if the card value plus the value of the players cards is equal to 21 before adding
	public Card deal() {
		int total=findTotal();
		Card card=null;
		int val=0;
		do {
			if ( card != null)
				super.addToBottom(card);
			card=super.dealFromTop();
			val=(card.getRank()).ordinal();
		}while(total+val==21);
		addCard(card);
		return card;
	}
	//adds up all of the players cards to find the total value
	public int findTotal() {
		int total=0;
		if(playersCards != null) {
			for(int i=0;i<playersCards.length;i++) {
				total+=(playersCards[i].getRank()).ordinal();
				System.out.println((playersCards[i].getRank()).ordinal());
			}
		}
		return total;
	}
	//the is used to add cards to the players hand. Called from deal
	public void addCard(Card toAdd) {
		Card[]arr=new Card[playersCards.length+1];
		if(playersCards.length>0) {
			for(int i=0;i<playersCards.length;i++) {
				arr[i]=playersCards[i];
			}
		}
		arr[arr.length-1]=toAdd;
		playersCards=arr;
	}

}
