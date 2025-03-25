/*
 * Jaimie Morris
 * Word counter program
 */

import java.io.*;
import java.util.*;

class JaimieMorris_WordCounter {

	public static void main(String []args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter file name");
		String fname = input.nextLine();

		System.out.println(fname);
		Scanner in = null;

		try {
			in = new Scanner(new File(fname));
		}catch(FileNotFoundException e) {
			System.out.println(fname + " not found");
			System.exit(-1);
		}

		String fullFile="";

		while(in.hasNext()) {
			fullFile=in.nextLine();
		}

		String[] words = fullFile.split("\\W+");

		Map<String, Integer> map = new TreeMap<String, Integer>();

		for(int i = 0;i< words.length; i++) {

			String key = words[i];

			if(map.get(key)==null) 
				map.put(key, 1);

			else {
				int val = (int) map.get(key);
				val++;
				map.put(key, val);
			}

		}


		for(String key: map.keySet())
			System.out.println(key + "  :   " + map.get(key));

	}
}
