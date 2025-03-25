
public class Board implements Comparable<Board>{

	private int[][] curBoard;
	private int movesMade;		
	private Board previousBoard;
	private int[] totLoc;	
	private int manhattan;


	public Board(int[][] board, int moves, Board prev) {
		previousBoard = prev;
		totLoc=new int[2];
		int len=board.length;
		
		curBoard=new int[board.length][board.length];
		
		//making totoros location
		for (int row=0;row<curBoard.length;row++) {
			
			for (int col=0;col<curBoard[row].length;col++) {
				
				curBoard[row][col]=board[row][col];
				
				if (board[row][col]==0) {
					totLoc[0]=row;
					totLoc[1]=col;
				}
				
				//finds place it belongs by subtracting one then modulo by length
				if(curBoard[row][col]!=0) {
					int numCol=((curBoard[row][col]-1)%len);
					
					double numRow = curBoard[row][col]/len;
					int baseNum = curBoard[row][col]/len;
					
					if (numRow!=baseNum)
						numRow=baseNum+1;
					
					if(curBoard[row][col]%len==0) 						
						numRow-=1;
					
					//adds to manhattan by using absolute value to find the distance
					manhattan += ((double)Math.abs(row-numRow))+((double)Math.abs(col-numCol)) ;
				}
				
			}
		}

	}

	
	//first makes a deep copy then swaps totoros location (0) with the sent row and col
	public Board swap(int row, int col) {
		
		curBoard[totLoc[0]][totLoc[1]]=curBoard[row][col];
		curBoard[row][col]=0;
		
	
		Board newBoard =new Board(this.curBoard, col, this);
		
		curBoard[row][col]=curBoard[totLoc[0]][totLoc[1]];
		curBoard[totLoc[0]][totLoc[1]]=0;
		
		
		totLoc[0]=row;
		totLoc[1]=col;
		
		
		return newBoard;
	}
	
	
	public boolean gameOver() {
		
		if (manhattan==0) 
			return true;
		
		return false;
	}

	public int compareTo(Board other) {

		return (this.manhattan+ this.movesMade) - (other.manhattan+other.movesMade);

	}

	public boolean equals(Object other){

		if(!(other instanceof Board))
			return false;

		Board otherBoard = (Board)other;

		for(int r = 0; r < curBoard.length; r++){
			
			for(int c = 0; c < curBoard[0].length; c++) {
				if(curBoard[r][c] != otherBoard.curBoard[r][c])
					return false;
			}
			
		}

		return true;


	}
	
	public boolean isSolveable() {
		int counter = 0;
		
		for(int row = 0;row<curBoard.length;row++) {
			for(int col = 0; col<curBoard[0].length; col++) {
				
				counter += smallerCTR(row, col);
				
			}
			
		}


		System.out.println(counter);
		
		return false;
				
	}
	
	private int smallerCTR(int row, int col) {
		
		int ctr = 0;
		
		for(int r = row;r<curBoard.length;r++) {
			for(int c = col; c<curBoard[0].length; c++) {
				
				if(curBoard[row][col] > curBoard[r][c] )
					ctr++;
				
				
				
			}
		}
		
		return ctr;
		
		
	}
	
	
	public void print() {
		
		for (int i=0;i<curBoard.length;i++) {
			
			for (int j=0;j<curBoard[i].length;j++) {
				
				System.out.print(curBoard[i][j]+"\t");
			}
			
			System.out.print("\n");
		}

	}
	
	
	//client
	public static void main(String[] args) {
		int len = 3;
		int ctr=1;
		int[][] board=new int[len][len];
		//making board
		board[0][0]=1;
		board[0][1]=8;
		board[0][2]=2;
		board[1][0]=0;
		board[1][1]=4;
		board[1][2]=3;
		board[2][0]=7;
		board[2][1]=6;
		board[2][2]=5;
		
		Board start = new Board(board, 0, null);
		start.print();
		start.isSolveable();
		
		Board swap1=start.swap(1,2);
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
