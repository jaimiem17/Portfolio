/*
 * Jaimie Morris
 * Number generation program uses a number generator to test threads and their sleep time difference with synchronization
 * 
 * before synchronizing the mutator method the numbers would not always reach 1000 but after it would. The not 
 * synchronized version may be going onto the next value without incrementing by 1 the one before making the counter 
 * increase but not making the result increase
 */

import java.util.*;
public class JaimieMorris_NumberGen implements Runnable{

	private int num;

	public JaimieMorris_NumberGen(int n) {
		num = n;
	}

	//accessor method
	public int getNum() {
		return num;
	}

	//mutator method
	public synchronized void setNum() {
		num ++;
	}

	public void run() {
		Random ran = new Random();

		try{

			Thread.sleep(Math.abs(ran.nextInt(500 - 100) + 100));
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}

		setNum();
		
		System.out.println(num);
	}

	public static void main(String[] args) {
		int num = 0;

		JaimieMorris_NumberGen gen = new JaimieMorris_NumberGen(num);

		Thread one = new Thread(gen);

		one.start();
	}

}
