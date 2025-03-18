package programming2;
/*
 * Jaimie Morris
 * This is an extension on Dial Lock. Adds clicks and attempts to keep criminals
 * out of safes
 */

public class SafeDialLock extends DialLock {
	private int attempts;

	public SafeDialLock() {
		super();
	}
	//the turnRight and turnLeft functions override the ones in the diallock and they add clicks 1/10 of the time
	public void turnRight() {
		int specialNum=5;
		super.turnRight();
		int val=(int) (Math.random()*10+1);
		if(val==specialNum) {
			System.out.println("click");
		}
	}
	public void turnLeft() {
		int specialNum=5;
		super.turnLeft();
		int val=(int) (Math.random()*10+1);
		if(val==specialNum) {
			System.out.println("click");
		}
	}
	//if they have not attempted to open 3 times prior, it will check if open. If not, it will add an attempt
	public boolean open() {
		if (attempts!=3) {
			if(super.open()!=true) 
				attempts++;

			else 
				return true;
		}
		if(super.open()==true)
			return true;
		else 
			return false;
	}
	//overrides reset to not allow reset if attempts have been maxed out
	public void reset() {
		if(attempts==3)
			System.out.println("Cant reset");
		else {
			attempts=0;
			super.reset();
		}
	}

}
