package programming2;

public class Client8 {

	//enumerated datatypes demo
	public static void main(String[] args){
		Deck one= new Deck();
		RANK toRANK = RANK.TWO;
		SUIT toSUIT=SUIT.CLUB;
		Card first=new Card(toSUIT, toRANK);

		System.out.println(one.numberInDeck());
		System.out.println(one.dealFromTop());
		System.out.println(one.numberInDeck());
		System.out.println(one.isEmpty());
		System.out.println(one.isFull());
		one.addToBottom(first);
		System.out.println("\n\n\nOG:");
		one.print();
		one.shuffle(100);
		System.out.println("\n\n\nshuffled:");
		one.print();
		one.order();
		System.out.println("\n\n\nBACK IN ORDER:");
		one.print();
		
	

		/*
		RANK toRANK = RANK.FIVE;
		SUIT toSUIT=SUIT.CLUB;
		Card first=new Card(toSUIT, toRANK);

		RANK toRANK1 = RANK.KING;
		SUIT toSUIT1=SUIT.DIAMOND;
		Card second=new Card(toSUIT1, toRANK1);
	    System.out.println(first.cardValue());
		//convert enum value to int 
		int val = toRANK.ordinal();
		System.out.println(first.toString());
		first.flip();
				System.out.println(first.toString());
		System.out.println(first.compareTo(second));
		System.out.println(first.equals(second));
		 */	
	}


}
