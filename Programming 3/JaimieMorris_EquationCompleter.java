/*
 * Jaimie Morris
 * Equation completer program uses 2 stacks to solve an equation
 */

import java.util.*;
import java.lang.Math.*;

public class JaimieMorris_EquationCompleter {


	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);

		System.out.print("Enter equation: ");
		String equation = keyboard.nextLine();

		//divides the equation up in an array of Strings
		String[] sep = equation.split(" ");

		Stack<Integer> numStack = new Stack<Integer>();
		Stack<String> opStack = new Stack<String>();


		//one iteration per String in equation 
		for (int i = 0; i < sep.length; i++) {
			//checks if sep[i] is on a num or not if yes: converts, pushes, then switches it
			
			if(i%2==0) {
				numStack.push(Integer.parseInt(sep[i]));
			}

			else {	
				//while there are operands in stack and the current top of stack is higher priority than the current operand add new solved num to the stack
				while(!opStack.isEmpty()&& hasPriority(opStack.peek(), sep[i])) {
					int right=numStack.pop();
					int left=numStack.pop();
					numStack.push(preformOp(left, right, opStack.pop()));
				}
				opStack.push(sep[i]);
			}
		}

		int right=numStack.pop();
		int left=numStack.pop();
		numStack.push(preformOp(left, right, opStack.pop()));
		System.out.println(numStack.peek());
	}

	//3 + 4 * 2 
	//4 * 3 + 5
	//10 - 3 ^ 2 + 4


	public static boolean hasPriority(String left, String right) {
		String ops = "+- */ ^";

		int leftSpot = ops.indexOf(left);
		int rightSpot = ops.indexOf(right);

		//If both are ^
		if(leftSpot==6&&rightSpot==6)
			return true;

		//if they are only 1 apart from each other they are equal priority
		return leftSpot-rightSpot>=1||leftSpot-rightSpot>=-1;
	}


	public static int preformOp(int left, int right, String op) {

		if( op.equals("+"))
			return left+right;

		else if( op.equals("-"))
			return left-right;

		else if( op.equals("*"))
			return left*right;

		else if( op.equals("/"))
			return left/right;

		else
			return (int) Math.pow(left, right);

	}


}

