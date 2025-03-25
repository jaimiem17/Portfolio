package programming2;

import java.io.File;

public class JaimieMorris_RecurisionProject {

	public static void main(String[] args) {
		// delete before submission
		int num = 5;
		int[] arr = { 1, 2, 3, 4, 5, 6 };
		// String bin=" ";
		String sen = "Harry Potter";
		String r = "r";
		harmonicSum(num);
		System.out.println();
		triples(arr, arr.length);
		System.out.println();
		int ans = charCount(sen, r);
		System.out.println(ans);
		System.out.println();
		genBinary(3, "");
		System.out.println();
		noConsZeros(3, "");
		System.out.println();
		FileCounter(new File("/home/"));
	}

	// #1
	public static void harmonicSum(int num) {
		/*
		 * Write a recursive function that takes in a single integer. The method prints
		 * the parameter out and then follows the following guidelines: Quits if the
		 * number is 1 If the number is even, the next number printed should be the
		 * number divided by 2 If the number is odd, the next number printed should be
		 * 3*number + 1
		 */
		System.out.print(num + " ");
		if (num == 1)
			return;
		if (num % 2 == 0) {
			harmonicSum(num / 2);
		}

		else {
			harmonicSum(3 * num + 1);
		}

	}

	// #2
	public static void triples(int[] arr, int len) {
		/*
		 * Write a recursive function called triples that takes in an array of integers
		 * and the length of that array and then triples the value of every element.
		 */
		if (len > 0) {
			arr[len - 1] = arr[len - 1] * 3;
			// System.out.print(arr[len-1]+" ");
			triples(arr, len - 1);

		}
	}

	// #3
	public static int charCount(String sen, String r) {
		/*
		 * Write a recursive function called charCount that takes in a String
		 * representing a sentence and another String representing a single
		 * character.This function will return the total number of times the single
		 * character appears within the String sentence parameter.
		 */
		int len = sen.length();
		if (len != 0) {
			if (sen.substring(len - 1).equals(r)) {
				return charCount(sen.substring(0, len - 1), r) + 1;
			} else {
				return charCount(sen.substring(0, len - 1), r);
				//
			}
		} else
			return 0;

	}

	// #4
	public static void genBinary(int n, String bin) {
		/*
		 * A binary string consists of only 0s and 1s. Write a recursive method
		 * genBinary that has one integer parameter n representing the length of the
		 * number to generate and another parameter representing the binary string built
		 * so far. The function will print all complete combinations of binary Strings
		 * of a given length.
		 */
		if (bin.length() == n) {
			System.out.println(bin);
			return;
		}
		genBinary(n, bin + "0");
		genBinary(n, bin + "1");

	}

	// #5
	public static void noConsZeros(int n, String bin) {
		/*
		 * Create another binary generator with the same parameters as the previous
		 * called noConsZeros that generates a binary string of a certain length that
		 * does not have two or more zeros in a row.
		 */
		if (bin.length() == n) {
			System.out.println(bin);
			return;
		}
		if ((bin.length() == 0) || bin.substring(bin.length() - 1, bin.length()).equals("1"))
			noConsZeros(n, bin + "0");

		noConsZeros(n, bin + "1");
	}

	/*
	 * #6 FileCounter For this function you may assume that you are working with two
	 * different types of files: a standard file and a directory (folders). A
	 * directory contains files and other directories.
	 * 
	 * Write a recursive function called FileCounter that takes in one parameter of
	 * type File. You may assume that a directory is initially passed into the first
	 * function call. This function will examine all directories (and
	 * subdirectories, etc..) and count all of the files (non-directories) found
	 * within the directory tree. This means it returns an int. Use the following
	 * File class methods to assist you in writing this function (for more
	 * information consult the API)
	 * 
	 * File Methods: isDirectory() and listFiles()
	 */ public static int FileCounter(File file) {
		if (file.isDirectory()) {
			File[] folder = file.listFiles();
			int temp = 0;
			for (int i = 0; i < folder.length; i++) {
				temp += FileCounter(folder[i]);
			}
			return temp;
		} else {
			return 1;
		}
	}

}
