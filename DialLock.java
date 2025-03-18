/*
 * Jaimie Morris
 * DialLock program. Generates random numbers and the user puts in a combo.
 * Program spins dial and tries to unlock
 */
package programming2;
import java.lang.*;

public class DialLock {
	private int[] nums=new int[3];
	private int curr=1;
	//private int guess;
	private boolean unlock=true;
	private boolean dir=true;//true is right, false is left   starts true bc it goes right first
	private int spun=0;//checks if they spun 3 times.
	
	//constructor that finds a random # 0-39 for the lock combo
	public DialLock(){
		nums[0]=(int) (Math.random()*39);
		nums[1]=(int) (Math.random()*39);
		nums[2]=(int) (Math.random()*39);
	}
/*functions below start by checking if dial was just turning the opposite 
 * direction (if so it checks that number is equal to the corresponding 
 * num123) then it will add 1 if the number is not equal to 39 already 
 * (then it restarts at 0)*/
	
	public void turnRight(){ 
		if (dir==false) {
			spun++;
			if (curr!=nums[1])
				unlock=false;
		}
		dir=true;
		if (curr==39)
			curr=0;

		else
			curr++;

	}

	public void turnLeft() {
		if (dir==true) {
			spun++;
			if (curr!=nums[0])
				unlock=false;
		}
		dir=false;
		if(curr==0) 
			curr=39;
		else
			curr--;
	}
//checks if it was just spinning right the checks if unlock is true and the dial had spun 3 times
	public boolean open() {
		if (dir==true) {
			spun++;
			if(curr!=nums[2])
				unlock=false;
		}
		
		if (unlock==true&&spun==3)
			return true;
		else
			return false;
	}
	//sets current to 1 and unlock and dir to true to reset the dial
	public void reset() {
		curr=1;
		unlock=true;
		dir=true;
	}

}


