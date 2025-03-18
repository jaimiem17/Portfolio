package programming2;

public class Client7 {

	public static void main(String[] args) {
		int len = 3;
		int ctr=1;
		int[][] board=new int[len][len];
		//making board
		board[0][0]=1;
		board[0][1]=3;
		board[0][2]=0;
		board[1][0]=8;
		board[1][1]=5;
		board[1][2]=2;
		board[2][0]=7;
		board[2][1]=6;
		board[2][2]=4;
		
		Board_p2 start = new Board_p2(board, 0, null);
		start.print();
		Board_p2 swap1=start.swap(1,2);
		System.out.println("after swap");
		swap1.print();
		System.out.println(swap1.gameOver());
		/* a perfect board
		for(int row=0;row<len;row++) {
			for (int col=0;col<len;col++) {
				if (ctr!=9)
					board[row][col]=ctr;
				System.out.print(board[row][col]+"\t");
				ctr++;

			}
			System.out.print("\n");
		}*/

	}

}
