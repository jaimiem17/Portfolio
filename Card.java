package programming2;

public class Card {
	private boolean face;
	private RANK r;
	private SUIT s;

	public Card(SUIT s1, RANK r1) {
		r=r1;
		s=s1;
		face=false;
	}	
	public Card(Card other) {
		other.r=r;
		other.s=s;
	}
	//accessor method
	public SUIT getSuit() {
		return s;
	}
	//accessor method
	public RANK getRank() {
		return r;
	}
	//accessor method
	public boolean isFaceUp() {
		if (face==true)
			return true;
		else
			return false;
	}
	public void flip() {
		face=!face;
	}//calls cardValue to find the its unique number then checks if they are the same
	public boolean equals(Card other) {
		int paC=other.cardValue();
		int obC=this.cardValue();
		if(obC==paC)
			return true;
		return false;
	}//calls cardValue to find the its unique number then compares them using if statements
	public int compareTo(Card other) {
		int paC=other.cardValue();
		int obC=this.cardValue();
		if(equals(other)==true)
			return 0;
		else if(paC>obC)
			return 1;
		else
			return -1;
	}
	public int cardValue() {
		//valR is wherever it is in its own set of 13 and then when you multiply it by its suit num it puts its place in the deck as a whole then adds one bc the rank num starts at 0 instead of 1
		int set=13;
		int valR = r.ordinal();
		int valS = s.ordinal();
		return valR+(set*valS)+1;

	}
	public String toString() {
		String sLow;
		String rLow;
		if (face==false) {
			return "Unknown";
		}
		else {
			sLow = s.name();
			sLow = sLow.substring(0,1) + sLow.substring(1,sLow.length()).toLowerCase();
			rLow = r.name();
			rLow = rLow.substring(0,1) + rLow.substring(1,rLow.length()).toLowerCase();


			return (rLow+" of "+ sLow+"s");
		}
	}

}
