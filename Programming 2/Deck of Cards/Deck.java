/*
 * Jaimie Morris
 * 1/17/21
 * Deck class creates a deck of cards using our previously made Card class and also uses
 * many different functions to determine 
 * A: make a copy of the Card to add because when it is flipped for the function you are not meant to permanently alter the original card
 */
package programming2;

import java.lang.Math;

public class Deck {
	private Card []myCards;
	private int numInDeck;
	public Deck() {
		myCards=new Card[52];
		int total=0;
		for (int s=0;s<4;s++) {
			for(int r=0;r<13;r++) {
				SUIT[] allSuits = SUIT.values();//puts suits in one array
				RANK[] allRanks = RANK.values();//puts ranks in one array
				Card start=new Card(allSuits[s], allRanks[r]);//sends each to Card class to create the card
				myCards[total]=start;//takes Card from Card class and adds to my deck of cards
				total++;
				numInDeck=total;
			}
		}
	}
	public Deck(int max) {//2nd constructor w empty deck
		Card []myCards=new Card[max];
		numInDeck=0;
	}
	public int numberInDeck() {
		return numInDeck;
	}
	public boolean isEmpty() {
		if(numInDeck==0)
			return true;
		else
			return false;

	}							 	//these two funcs check if full or empty
	public boolean isFull() {
		if(numInDeck==0)
			return false;
		else
			return true;

	}
	public void addToBottom(Card toAdd) {
		Card copyCard=toAdd;//creates a COPY of the card
		if(numInDeck==myCards.length) {
			throw new IllegalStateException("Deck is full");
		}
		else {
			int total=0;
			if(copyCard.isFaceUp()) {//checks
				copyCard.flip();//flips card if face up
			}
			while(myCards[total]!=null) {
				total++;
			}		
			myCards[total]=copyCard;//adds copy of card to end of deck
			numInDeck+=1;//adds a card to the deck
		}
	}
	public Card dealFromTop() {
		Card []myCards1=new Card[myCards.length];
		Card top;
		if(numInDeck==0) {
			throw new IllegalStateException("Deck is empty");
		}
		else {
			top=myCards[0];
			if(!top.isFaceUp()) {//checks
				top.flip();//flips card if face down
			}

			for (int total=0;total<51;total++) {
				myCards1[total]=myCards[total+1];
			}
		}
		myCards=myCards1;
		numInDeck-=1;//takes out a card
		return top;
	}
	public void shuffle(int numTimes) {//this func uses math rand to choose two random cards to swap
		for (int x=0;x<numTimes;x++) {
			int shuff1=0;
			int shuff2=0;
			while (shuff2==shuff1) {
				shuff1= (int)(Math.random() * numInDeck);
				shuff2= (int)(Math.random() * numInDeck);
			}
			Card copy=myCards[shuff1];
			myCards[shuff1]=myCards[shuff2];
			myCards[shuff2]=copy;
		}
	}
	public void order() {
		for(int i=0;i<numInDeck;i++) {
			int minInd=i;
			for(int j=i+1;j<numInDeck;j++) 
				if(myCards[j].compareTo(myCards[i])==1) {
					minInd = j;

			Card temp = myCards[minInd];
			myCards[minInd] = myCards[i];
			myCards[i] = temp;
				}
		}
	}

	public void print() {//extra function i made to print!

		for(int i=0;i<myCards.length;i++) {
			System.out.println(i+1+": "+myCards[i]);	
		}
	}
}
