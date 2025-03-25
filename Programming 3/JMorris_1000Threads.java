import java.util.*;

public class JMorris_1000Threads {
	
	public static void main(String[]args) {

		JaimieMorris_NumberGen gen = new JaimieMorris_NumberGen(0);
		
		ArrayList<Thread> allthreads = new ArrayList<Thread>();
		
		//adds the threads
		for(int i = 0;i<1000;i++) {
			allthreads.add(new Thread(gen));
		}
		
		//loops thru and starts prior to join
		for(Thread nextThread: allthreads)
			nextThread.start();
		
		
		//joins!
		for(Thread nextThread: allthreads){
			
			try{
				nextThread.join();
				
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
		}

	}


}
