//Jaimie Morris Client code
package programming2;

public class Client {

	public static void main(String[] args) {
		
		int[][] matr = {{1,2,3,4,5}, 
						{6,7,8,9,10},
						{1,2,3,4,5}};
		int row;
		int col;
		
		for(int r=0;r<matr.length;r++) {
			for (int c=0;c<matr[0].length;c++) {
				System.out.print(matr[r][c]+" [" + r + "]["+c+"] ");
			}
			System.out.println();
		}
		
		
	/*	
		Person me = new Person(16, "Jaimie", "12/02/03");
		Person mom = new Person("Sharone", "11/25/72");
		//Person dad = new Person();
		
		System.out.println(me);
		System.out.println(mom);
*/

	}

}
