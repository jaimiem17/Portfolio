package programming2;
/*
 * Jaimie Morris
 * This program maintains words in an ArrayList dictionary and then has a function to find ones that contain a user input.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Dictionary extends ArrayList<DefinitionOG> {

	public Dictionary() {
		super();
	}
	public Dictionary (String fname) {
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(-1);

		}

		while (fileIn.hasNextLine()) {

			String line=(fileIn.nextLine());
			int space = line.indexOf(" ");
			String word= line.substring(0,space);
			String definition=line.substring(space+1,line.length());
			DefinitionOG def=new DefinitionOG(word, definition);
			add(def);
		}

	}//searches for the word and if it is found the object is added to the dictionary. Then the dictionary is returned
	public Dictionary getHits(String input){
		Dictionary word=new Dictionary();
		for(int i=0;i<size();i++) {
			int ind= get(i).getWord().indexOf(input);
			if (ind>=0) {
				word.add(get(i));
				System.out.println(get(i).getWord());
			}
		}

		return word;

	}

}
