package programming2;

import java.util.*;

public class Client_Dictionary {

	public static void main(String[] args) {
		String file="wordlist.txt";
		Dictionary words=new Dictionary(file);
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter Word");
		String word = keyboard.nextLine();

		System.out.println(words.getHits(word));
	}

}
