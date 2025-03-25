import java.util.*;

public class SynchronizedBankers implements Runnable{

	private BankAccount account = new BankAccount();
	
	public void run(){
		
		for(int x =0; x < 10; x++){
			
			makeWithdrawal(10);
			if(account.getBalance() < 0)
				System.out.println("Overdrawn!");
		}
	}

	// only one thread can enter this method at a time
	// so even if the thread falls asleep, nothing else can enter this method
	// until that thread wakes back up and gives the lock away after the thread exits this method.
	private synchronized void makeWithdrawal(int amount){
		
		if(account.getBalance() >= amount){
			System.out.println(Thread.currentThread().getName() + "  is going to withdraw");
		
			try{
				
				System.out.println(Thread.currentThread().getName() + " is going to sleep");
				Thread.sleep(500);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName() + " woke up");
			account.withdraw(amount);
			
			System.out.println(Thread.currentThread().getName() + " complete the withdraw");
		}
		else
			System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
	}
	
	public static void main(String[] args){
		
		SynchronizedBankers theJob = new SynchronizedBankers();
		
		Thread one = new Thread(theJob);
		Thread two = new Thread(theJob);
		
		one.setName("Jack");
		two.setName("Kate");    // this is not necessary, but we can provide a tag for our threads.
								// makes debugging easier.
		
		one.start();
		two.start();         
	}
}
