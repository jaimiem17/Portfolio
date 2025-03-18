package programming2;

public class Board_p2 implements Comparable<Board_p2>{

	private int[][] curBoard;
	private int movesMade;		//how many moves to reach this board
	private Board_p2 previousBoard;
	private int[] totLoc=new int[2];		//location of totoro. [0] == row
	private int manhattan;


	public Board_p2(int[][] board, int moves, Board_p2 prev) {
		curBoard=new int[board.length][board.length];
		//making totoros location!
		for (int row=0;row<curBoard.length;row++) {
			for (int col=0;col<curBoard[row].length;col++) {
				curBoard[row][col]=board[row][col];
				if (board[row][col]==0) {
					totLoc[0]=row;
					totLoc[1]=col;
				}
			}
		}

		//create manhattan
		manhattan=manhattan();

	}

	public int manhattan(){
		int numCol = 0;
		int manNum = 0;
		int len=3;//curBoard.length;

		for (int row=0;row<len;row++) {
			for (int col=0;col<len;col++) {
				if(curBoard[row][col]!=0) {
					numCol=((curBoard[row][col]-1)%len);//finds place it belongs by subtracting one then modulo by length
					
					double numRow = curBoard[row][col]/len;
					int baseNum = curBoard[row][col]/len;
					if (numRow!=baseNum){
						numRow=baseNum+1;
					}
					if(curBoard[row][col]%len==0) {
						numRow-=1;
					}//adds to manhattan by using absolute value to find the distance
					manNum += ((double)Math.abs(row-numRow))+((double)Math.abs(col-numCol)) ;
				}
			}
		}
		return manNum;
	}
	public Board_p2 swap(int row, int col) {//first makes a deep copy then swaps totoros location (0) with the sent row and col
		Board_p2 nBoard=new Board_p2(curBoard, col, this);//[curBoard.length][curBoard.length];
		for (int i=0;i<curBoard.length;i++) {
			for (int j=0;j<curBoard[i].length;j++) {
				nBoard.curBoard[i][j]=curBoard[i][j];
			}
		}
		nBoard.curBoard[totLoc[0]][totLoc[1]]=curBoard[row][col];
		nBoard.curBoard[row][col]=0;
		totLoc[0]=row;
		totLoc[1]=col;
		return nBoard;
	}
	public boolean gameOver() {
		if (manhattan==0) {
			return true;
		}
		return false;
	}

	public int compareTo(Board_p2 other) {

		return (this.manhattan+ this.movesMade) - (other.manhattan+other.movesMade);

	}

	public boolean equals(Object other){

		if(!(other instanceof Board_p2))
			return false;

		Board_p2 otherBoard = (Board_p2)other;

		for(int r = 0; r < curBoard.length; r++){
			for(int c = 0; c < curBoard[0].length; c++)
				if(curBoard[r][c] != otherBoard.curBoard[r][c])
					return false;
		}

		return true;


	}
	public void print() {
		for (int i=0;i<curBoard.length;i++) {
			for (int j=0;j<curBoard[i].length;j++) {
				System.out.print(curBoard[i][j]+"\t");
			}
			System.out.print("\n");
		}

	}
}
