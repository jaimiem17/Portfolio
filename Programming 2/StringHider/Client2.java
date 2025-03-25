//Jaimie Morris Client code
package programming2;

public class Client2 {

	public static void main(String[] args) {
		StringHider theString = new StringHider("I choose you, Squirtle!");
		theString.hide(21,22);
		System.out.println(theString);
		theString.recover(2,3);
		theString.recover(21,22);
		System.out.println(theString);

	}

}
