/*Jaimie Morris
 * Exhausted Knight program- prints a board with the moves only a knight can make
 * 10/18
 */
package programming2;
import java.util.*;

public class JaimieMorris_ExhaustedNight {

	private static final int[] VERT_DISP= {1,2,2,1,-1,-2,-2,-1};
	private static final int[] HORZ_DISP= { -2,-1,1,2,2, 1,-1,-2};

	public static void main(String[] args){
		int[] moves=new int[1];
		final int boardSize = 8;
		int curRow = 0;
		int curCol = 0;
		int ctr=1;
		int move=0;
		int[][] board = new int[boardSize][boardSize];
		board[curRow][curCol]=ctr;
		while(moves.length!=0){
//			System.out.println("n0");
			ctr++;
			moves=determineMoves(board, curRow, curCol);
//if it found moves then it will open doMoves which chooses randomly from the
//available moves and adds the moves to the board
			if (moves.length>0) {
//				System.out.println("CLASS");
				move=doMove(board, moves, curRow, curCol, ctr);
				curRow=curRow+VERT_DISP[moves[move]];
				curCol=curCol+HORZ_DISP[moves[move]];
			}

		}

		printBoard(board);
	}

	public static int[] determineMoves(int[][] board, int currentRow, int currentCol) {
		// This function examines all 8 different possible moves and returns an array of the move numbers which 
		//are valid. Move numbers correlate to the indices of the horizontal and vertical offset arrays.
		int nextCol;
		int ctr=0;
		int nextRow;
		boolean valid=false;
		boolean beenThere = false;
		int[]moves1=new int[VERT_DISP.length];//amount of possible moves from one position is 8

		for(int i = 0; i < VERT_DISP.length; i++){
			nextRow = currentRow + VERT_DISP[i];
			nextCol = currentCol + HORZ_DISP[i];

			valid=inBounds(board, nextRow, nextCol);
			if (valid==true)//if its not out of bounds THEN check if been there before
				beenThere=spotTaken(board, nextRow, nextCol);

			if (valid==true&&beenThere==false) {
				moves1[ctr]=i;
				ctr++;
			}
		}
		int avail=0;
		int ctr2=0;//to run thru the moves array 
		for(int i=0;i<moves1.length;i++) {
			if(moves1[i]!=0) 
				avail++;
		}
		//the array is equal to 8 spots but this gets rid of all the spots with 0 and creates a new array without them
		int[]moves=new int[avail];
		if(moves.length>0) {
			for(int i=0;i<moves.length;i++) {
				if(moves1[i]!=0) 
					moves[ctr2]=moves1[i];
				ctr2++;
			}
		}

//added by me
		for(int i=0;i<moves.length;i++)
			System.out.println(moves[i]);
		
		return moves;
	}

	public static void printBoard(int[][] board) {
		// This function prints the current state of the board as it’s shown in the sample output on the previous document.
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[row].length; col++)
			{
				System.out.printf("%5d", board[row][col]); 
			}
			System.out.println(); 
		}
	}
	public static boolean inBounds(int[][]board, int row, int col) {
		//checks if it is in bounds
		if (row<0||col<0||row>=board.length||col>=board[0].length) {
			return false;
		}
		return true;
	}

	public static boolean spotTaken(int[][] board, int row, int col) {//checks if been there b4
		if(board[row][col]==0)
			return false;
		return true;
	}

	public static int doMove(int[][]board,int[] moves, int row, int col, int ctr){
		int len=moves.length-1;
		int move=(int)(Math.random()*len);//randomly picks a spot from the available moves
		board[row+(VERT_DISP[moves[move]])][col+(HORZ_DISP[moves[move]])]=ctr;
		System.out.println("h: :"+ (row+(VERT_DISP[moves[move]])));
		System.out.println("b: :"+ (col+(HORZ_DISP[moves[move]])));
		return move;
	}



}
