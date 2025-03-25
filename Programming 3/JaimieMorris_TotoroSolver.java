/*
 * Jaimie Morris
 * Totoro solver uses priority queue to print the path of slides that lead to totoro's solution
 */
import java.util.*;

public class JaimieMorris_TotoroSolver {

	private PriorityQueue<Board> priorityBoards;
	public  final int[] HORZDISP = {1,0,0,-1};
	public  final int[] VERTDISP = {0,1,-1,0};

	public JaimieMorris_TotoroSolver(int[][] initialBoard){

		priorityBoards = new PriorityQueue<Board>();
		priorityBoards.add(new Board(initialBoard, 0, null));

		search();


	}

	public void search() {

		Board current = priorityBoards.remove();
		//	Board current = new Board(priorityBoards.peek().curBoard, priorityBoards.peek().movesMade, priorityBoards.remove().previousBoard);
		if(!current.isSolveable()) {
			System.out.println("Not Solveable");
			return;
		}

		while(!current.gameOver()){

			for(int i = 0; i < VERTDISP.length; i++){

				if(inBounds(current.curBoard, current.totLoc[0]+VERTDISP[i], current.totLoc[1]+HORZDISP[i])) {
					Board toAdd = current.swap(current.totLoc[0]+VERTDISP[i], current.totLoc[1]+HORZDISP[i]);

					//if swap is equal to parent board
					if(current.previousBoard!=null) {

						if(!toAdd.equals(current.previousBoard))
							priorityBoards.add(toAdd);
					}

					else
						priorityBoards.add(toAdd);
				}

			}

			current = priorityBoards.remove();

		}


		gameOverPrint(current);
	}


	private void gameOverPrint(Board current) {


		Stack<Board> boardStack = new Stack<Board>();

		while(current!=null) {

			boardStack.push(current);
			current = current.previousBoard;

		}

		while(!boardStack.isEmpty()) {

			print(boardStack.pop());
		}

	}


	//checks if the move is in bounds
	private boolean inBounds(int[][] curBoard, int row, int col) {

		if (row<0||col<0||row>=curBoard.length||col>=curBoard[0].length) {
			return false;
		}
		return true;
	}


	public void print(Board cur) {

		int[][] thisBoard = cur.curBoard;

		for (int i=0;i<thisBoard.length;i++) {

			for (int j=0;j<thisBoard[i].length;j++) {

				System.out.print(thisBoard[i][j]+"\t");
			}


			System.out.print("\n");
		}	
		System.out.println("moves: "+cur.movesMade);
		System.out.println("manhattan: "+cur.manhattan);

		System.out.print("\n");



	}



	//innner class is Board class
	public class Board implements Comparable<Board>{

		private int[][] curBoard;
		private int movesMade;		
		private Board previousBoard;
		private int[] totLoc;	
		private int manhattan;


		public Board(int[][] board, int moves, Board prev) {
			movesMade = moves;
			previousBoard = prev;
			totLoc=new int[2];
			int len=board.length;
			manhattan = 0;

			curBoard=new int[board.length][board.length];

			//finding totoros location
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


			Board newBoard =new Board(this.curBoard, movesMade+1, this);

			curBoard[row][col]=curBoard[totLoc[0]][totLoc[1]];
			curBoard[totLoc[0]][totLoc[1]]=0;


			//			totLoc[0]=row;
			//			totLoc[1]=col;


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
			int outOfOrder = 0;

			for(int row = 0;row<curBoard.length;row++) {
				for(int col = 0; col<curBoard[0].length; col++) {

					outOfOrder += smallerCTR(row, col);
				}
			}


			return (outOfOrder%2 == 0 && curBoard.length%2 != 0) 
					||	(curBoard.length%2 ==0 && totLoc[0]%2==0 && outOfOrder%2 != 0 ) 
					|| ( totLoc[0]%2!=0 && outOfOrder%2 == 0);


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



		
	}
	
	
	public static void main(String[]args) {
		int ctr=0;
		
		System.out.println(foo(10, ctr));
		
		
		int n = 500;

		for(int i = (n*n)/2; i<=(n*n)/2; i = i++){
			i++;
			ctr++;
		}

		System.out.println(ctr);




		/*	int len = 3;
		int[][] board=new int[len][len];


		//making board len 3
		board[0][0]=0;
		board[0][1]=1;
		board[0][2]=3;
		board[1][0]=4;
		board[1][1]=2;
		board[1][2]=5;
		board[2][0]=7;
		board[2][1]=8;
		board[2][2]=6;
		 */


		//making board len 3
		int[][] board1 = {{1,8,2},{0,4,3},{7,6,5}};
		int[][] board = {{7,2,4},{5,0,6},{8,3,1}};

		JaimieMorris_TotoroSolver start = new JaimieMorris_TotoroSolver(board);
	}
 
	
//helpstuff not paet fo toty
	public static int foo(int n, int ctr) {
		System.out.println(ctr);
		if(n==0||n==1)
			return ctr;
		
		return foo(n-1, ctr+1)*foo(n-2, ctr+1);
	}
	
	
	
}



