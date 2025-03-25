/*
 * Jaimie Morris
 * Program prints out every combination of acceptance scenarios given a max number
 */
import java.util.*;

public class JaimieMorris_CollegeAdmissions2<E> {

	public void showAdmissions(int maxNumApps) {
		for(int j=2;j<=maxNumApps;j++) {
			System.out.print("Total Applications: "+ j + ". Accepted starting spot: ");
			for(int i=1;i<=j;i++) {
				Application combo = new Application(i, j);
				int a= combo.accept();
	//			System.out.println(a + " == "+ i);
				if(a==i) {
					System.out.print(i);
				}
				
			}
			System.out.println();

		}
	}


	public class Application {
		Queue<Integer> apps = new LinkedList<Integer>();
		private int appNum;
		private int total;

		public Application(int a, int t) {
			appNum=a;
			total=t;

			int j=0;
//			System.out.println("new");
			for(int i = 1 ; i <=total;i++) {
				if(a+j>total) {
					a=1;
					j=0;
				}
				apps.add(a+j);
//				System.out.println(a+j);
				j++;
			}
		}

		public int accept() {
			int accepted = 0;
			int i = 0;
			while(!apps.isEmpty()) {
			//	if(i%2==0) {
					accepted=apps.remove();
					apps.add(accepted);
				
					apps.remove();
			

			}
	//		System.out.println("acc "+accepted);
			return accepted;
		}


	}
	
	public static void main(String []args) {
		JaimieMorris_CollegeAdmissions2 <Integer> start = new JaimieMorris_CollegeAdmissions2();
		
		start.showAdmissions(10);
	}

}
