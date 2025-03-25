/*
 * Jaimie Morris
 * Program prints out every combination of acceptance scenarios given a max number of applications
 * There is an increasing pattern where each following term, starting at 2, is multiplied by 2
 * ex: 2, 4, 8, 16, 32, 64, 128 or 2^1, 2^2, 2^3, 2^4, 2^5, 2^6, 2^7
 * this pattern appears because the application needs to be a certain number of terms in order to finish in its starting place
 * 
 */
import java.util.*;

public class JaimieMorris_CollegeAdmissions<E> {

	public void showAdmissions(int maxNumApps) {
		//loops through maxNumApps amount of times starting at 2
		for(int j=2;j<=maxNumApps;j++) {
			System.out.print("Total Applications: "+ j + ". Accepted starting spot: ");
			
			//loops through j amount of times. creates a new Application object
			for(int i=1;i<=j;i++) {
				Application combo = new Application(i, j);
				int a= combo.accept();

				//checks if the i is accepted
				if(a==i) {
					System.out.print(i+", ");
				}
				
			}
			
			System.out.println();

		}
	}

	//application class creates an integer queue of apps with a starting position and a total
	public class Application {
		Queue<Integer> apps = new LinkedList<Integer>();
		private int appNum;
		private int total;

		public Application(int a, int t) {
			appNum=a;
			total=t;
			int j=0;


			//adds all apps to queue
			for(int i = 1 ; i <=total;i++) {
				//if it reaches max num of apps then resets to 0
				if(appNum+j>total) {
					appNum=1;
					j=0;
				}
				apps.add(appNum+j);

				j++;
			}
		}

		public int accept() {
			int accepted = 0;
			
			//removes all apps while saving every other one
			while(!apps.isEmpty()) {
					accepted=apps.remove();
					apps.add(accepted);
			
					apps.remove();
			}
			
			//returns the last app removed
			return accepted;
		}


	}
	

}
