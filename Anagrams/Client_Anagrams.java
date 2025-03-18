package programming2;
import  java.util.*;

public class Client_Anagrams {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter Word");
		String word = keyboard.nextLine();
		
		String file="words.txt";
		
		AnagramFinder start=new AnagramFinder(file);
		start.printAnagrams(word);
	}

}
