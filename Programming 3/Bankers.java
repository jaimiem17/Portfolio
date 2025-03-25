import java.util.*;

public class Bankers implements Runnable{

	private BankAccount account = new BankAccount();
	
	public void run(){
				
		for(int x =0; x < 10; x++){
			
			makeWithdrawal(10);
			if(account.getBalance() < 0)
				System.out.println("Overdrawn!");
		}
	}
	
	
	private void makeWithdrawal(int amount){
		
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
		
		Bankers theJob = new Bankers();
		
		Thread one = new Thread(theJob);
		Thread two = new Thread(theJob);
		
		one.setName("Jack");
		two.setName("Kate");    
		
		one.start();
		two.start();         
	}
}
