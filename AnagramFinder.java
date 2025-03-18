/*program that uses has-a relationships to find anagrams
 * Jaimie Morris
 */
package programming2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnagramFinder {
	private String[]arr;//used to make a list of just the words
	private StringFrequency []lists;

	public AnagramFinder(String fname) {
		//load file program
		Scanner keyboard = new Scanner(System.in);
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(-1);

		}
		int count = 0;
		while (fileIn.hasNextLine()) {
			count++;//to find the amount of lines in the file
			fileIn.nextLine();
		}
		arr = new String[count];
		lists = new StringFrequency[count];
		count = 0;
		fileIn.close();
		try {
			fileIn = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(-1);

		}
		while (fileIn.hasNextLine()) {

			arr[count]=(fileIn.nextLine());//this array just holds the words to print later
			lists[count]=new StringFrequency(arr[count]); //builds StringFrequency array
			count++;
		}

	}
	public void printAnagrams(String word) {
		StringFrequency newWord=new StringFrequency(word);//finds StringFrequency of user input
		for(int i=0;i<lists.length;i++) {
			StringFrequency sWord=new StringFrequency(lists[i]); //looks thru list of words to find matching frequency
			if (newWord.hasSameFreq(sWord)==true) {
				System.out.println(arr[i]);//prints word if StringFrequency is the same
			}
		}
	}

}
