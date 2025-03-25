package programming2;
import java.util.*;

public class RExhaustedKnight {


	public static final int[] HORZDISP = {1,2,2,1,-1,-2,-2,-1};
	public static final int[] VERTDISP  = {-2,-1,1,2,2,1,-1,-2};

	public static void main(String[] args) {

		int[][] board = new int [8][8];
		board[0][0] = 1;

		int currentRow = 0;
		int currentCol = 0;
		int counter = 1;

		int[] validMoves = new int[1];

		while(validMoves.length > 0)
		{
			validMoves = determineMoves(board,currentRow,currentCol);

			int shiftPos = choosePos(validMoves);

			//adjusts position of the knight based off the randomly generated movement
			currentRow += HORZDISP[shiftPos];
			currentCol  += VERTDISP[shiftPos];

			board[currentRow][currentCol] = counter + 1;
			counter++;
			printBoard(board);
			//runs until there are no moves left
		}

		//printBoard(board);

	}

	//examines the possible moves, returns an array of valid ones
	public static int[] determineMoves(int[][] board, int currentRow, int currentCol) {

		int counter = 0;
		int nextRow = 0;
		int nextCol = 0;

		//counts each valid move
		for(int i = 0; i < board.length; i++) {

			nextRow = currentRow + VERTDISP[i];
			nextCol = currentCol + HORZDISP[i];

			if(nextRow >= 0 && nextCol >= 0 && nextRow < board.length && nextCol < board[0].length ) {
				if(board[nextRow][nextCol] == 0) {
					counter++;
				}
			}
		}

		int[] validMoves = new int[counter];
		int count = 0;

		//checks each possible move, stores it in an array of valid moves, returns it to main
		for(int i = 0; i < board.length; i++) {

			nextRow = currentRow + VERTDISP[i];
			nextCol = currentCol + HORZDISP[i];

			if(nextRow >= 0 && nextCol >= 0 && nextRow < board.length && nextCol < board.length) {
				if(board[nextRow][nextCol] == 0) {
					validMoves[count] = i;
					count++;
				}
			}
		}
		return validMoves;
	}

	//randomly picks a spot in the array for the next position of the knight
	public static int choosePos(int[] validMoves){

		int rand = (int) (Math.random() * (validMoves.length - 1));

		int shiftPos = validMoves[rand];

		return shiftPos;
	}


	//prints the state of the board, including the order of the locations
	public static void printBoard(int[][] board) {

		System.out.println("\t" + 1 + "\t" + 2 + "\t" + 3 + "\t" + 4 + "\t" + 5 + "\t" + 6 + "\t" + 7 + "\t" + 8);
		System.out.println();

		for(int i = 0; i < board.length; i++) {

			System.out.print(i + 1 + "\t");

			for(int x = 0; x < board.length; x++) {

				System.out.print(board[i][x] + "\t");
			}

			System.out.println();
		}
	}

}

