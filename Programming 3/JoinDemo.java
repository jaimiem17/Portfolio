import java.util.*;

//I'm using already existing classes (Bankers) just to avoid rewriting
public class JoinDemo {

	public static void main(String[] args){

		//Here I'm creating a runnable object that I want my 
		//threads to manipulate

		//Either the object is made just once like you will see now
		//Or the instance variables in the object must be static (to make them shared)
		//Or the Runnable object must be an inner class (we will do this most frequently)
		

		PrintNums shared = new PrintNums();

		ArrayList<Thread> allthreads = new ArrayList<Thread>();

		Thread t1 = new Thread(shared);
		Thread t2 = new Thread(shared);
		Thread t3 = new Thread(shared);

		allthreads.add(t1);
		allthreads.add(t2);
		allthreads.add(t3);

		//Now I'm going to launch all threads

		for(Thread nextThread: allthreads)
			nextThread.start();
	
			
		for(Thread nextThread: allthreads){
			//the main thread will wait until nextThread is done before moving on
			//t1,t2 and t3 are still running concurrently. Only the main thread is told to wait
			//because it is the thread that executed the call to join
			try{
				nextThread.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		//at this point all babu threads are guaranteed to have run to completion
	}
}
