/*
 * Jaimie Morris
 * Hand program uses previous deck/card/etc classes to create a map of cards that holds ranks as keys and holds functions that add, find, and remove cards 
 */
import java.util.*;
import static java.lang.Math.*;

public class JaimieMorris_Hand {

	private TreeMap<RANK, Deck> allCards;

	public JaimieMorris_Hand() {

		allCards = new TreeMap<RANK, Deck>();
	}

	//adds a card to the map
	public void addToHand(Card toAdd) {
		boolean duplicate = false;
		Deck curDeck;

		//if new key creates a new deck
		if(allCards.get(toAdd.getRank())==null) {
			curDeck = new Deck(4);
		}

		//if existing key sets deck to already existing deck associated with that key
		else {
			curDeck = allCards.get(toAdd.getRank());
			duplicate = isDup(curDeck, toAdd.getSuit());
		}

		//checks if already in deck if not puts key in map with curDeck and then adds new card to curDeck
		if(!duplicate) {
			allCards.put(toAdd.getRank(), curDeck);
			curDeck.addToBottom(toAdd);
		}

	}

	//helper method to find if the card is a duplicate
	private boolean isDup(Deck d, SUIT s) {

		
		//uses for loop exact times as cards in current deck
		for(int i = 0; i <d.numberInDeck(); i++) {
			Card top = d.dealFromTop();
			
			
			//if same suit in the rank deck then return true but add it back first
			if(top.getSuit()==s) {
				d.addToBottom(top);
				return true;
			}

			else
				d.addToBottom(top);
		}

		return false;

	}

	//returns deck of certain rank and removes it from allCards
	public Deck giveUp(RANK toFind) {

		Deck found = allCards.get(toFind);

		//if does not exist
		if(found==null)
			return new Deck(0);

		allCards.remove(toFind);


		return found;


	}

	//prints all cards
	public void printAll() {
		//creates a queue to hold all cards so when they are removed in dealFromTop they can be added back
		Queue<Card>cards=new LinkedList<Card>();

		//loops through all keys and prints them then adds them to the queue
		for(RANK key: allCards.keySet()) {
			Deck currentDeck = allCards.get(key);
			while(currentDeck.numberInDeck()>0) {
				Card top = currentDeck.dealFromTop();

				System.out.println(top.toString());
				cards.add(top);
			}
		}

		//adds cards back to deck from queue
		while(!cards.isEmpty()) {
			addToHand(cards.remove());
		}

	}

	//returns 1 if there is a 4 of a current rank
	public int booksWon(RANK toFind) {

		// calls giveUp which returns the deck with the specific rank and checks if the amt of cards is a book
		if(giveUp(toFind).numberInDeck()==4)
			return 1;

		return 0;

	}

	//counts amount of full books and returns num also removes books from allCards
	public int booksWon() {
		RANK[] ranks = RANK.values();
		int totalBooks = 0;

		//loops through all ranks in a for loop and if it finds a full book adds to total- if its not a full book then adds it back to allCards
		for(int i = 0;i<ranks.length;i++) {
			Deck ranked = giveUp(ranks[i]);

			if(booksWon(ranked.getSuit())==1)
				totalBooks++;

			else {
				while(ranked.numberInDeck()>0) {
					addToHand(ranked.dealFromTop());
				}
			}
		}

		return totalBooks;

	}

	//finds most frequently occuring rank discluding toIgnore unless toIgnore is the only rank in deck
	public RANK mostFreq(RANK toIgnore) {
		RANK[] ranks = RANK.values();
		int max = 0;
		
		//created to hold ranks with the highest freq
		ArrayList<RANK> equalFreqs = new ArrayList<RANK>();

		//loops through all gets and gets amt of occurences from current key
		for(RANK key: allCards.keySet()) {
			int freq = allCards.get(key).numberInDeck();

			//if it is the highest key so far and not the ignore rank: clear current arrayList and add current key
			if(freq>max && key!= toIgnore) {
				max = freq;
				equalFreqs.clear();
				equalFreqs.add(key);
			}

			//if it is equal to max and not toIgnore then just add to arraylist
			else if(freq==max && key!= toIgnore) {
				equalFreqs.add(key);
			}

		}
		
		//if it never finds any cards checks if ignore has occurences and if not returns null
		if(equalFreqs.size()==0) {
			if(allCards.get(toIgnore)!=null)
				return toIgnore;

			return null;
		}

		//returns rank at random number between 0 and amount of ranks with same occurences
		int r = (int) (Math.random()*equalFreqs.size());
		return equalFreqs.get(r);


	}


}



