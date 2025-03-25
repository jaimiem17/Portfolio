
public class PrintNums implements Runnable{


	public void run(){

		for(int i = 0; i < 10; i++){
			System.out.println(i);
			
		}
		
	}


	public static void main(String[] args){

		Runnable threadJob = new PrintNums();  
		Thread aThread = new Thread(threadJob);	  
		Thread bThread = new Thread(threadJob);

		aThread.start();		
		bThread.start();        

		System.out.println("In main!");

		
	}
}
