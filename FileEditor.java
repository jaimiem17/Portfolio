package programming2;
import java.util.*;
import java.io.*;

//lyrics.txt program jaimie morris
public class FileEditor {
  public static void main(String[] args) {
    String word;
    String[] lines = new String[1];
    Scanner fileIn = null;
    int ans;
    int ansLast = 0;
    String toReplace = " ";
    String wordRep = " ";
    String fname = " ";
    Scanner keyboard = new Scanner(System.in);
    do {
      System.out.println(
          "1. Load File\n2. Display File\n3. Save File\n4. Find Word\n5. Replace Word\n6. Undo Last Change\n7. Exit");
      ans = keyboard.nextInt();
      if (ans != 6)
        ansLast++;
      keyboard.nextLine();
      if (ans == 1) {
        System.out.println("File Name: ");
        fname = keyboard.nextLine();
        lines = loadFile(fname);
      } else if (ans == 2) {
        displayFile(lines);
      } else if (ans == 3) {
        saveFile(fname, lines);
      } else if (ans == 4) {
        System.out.print("Enter a word to find: ");
        word = keyboard.nextLine();
        findWord(lines, word);
      } else if (ans == 5) {
        System.out.print("Enter a word to replace: ");
        wordRep = keyboard.nextLine();
        System.out.print("Enter a word to replace " + wordRep + " with: ");
        toReplace = keyboard.nextLine();

        replaceAllLines(lines, toReplace, wordRep);

      } else if (ans == 6) {
        undoLast(ansLast, toReplace, wordRep, lines);
      }

    } while (ans != 7);

  }

  public static String[] loadFile(String fname) {
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
      count++;
      fileIn.nextLine();

    }
    String[] lines = new String[count];
    count = 0;
    fileIn.close();
    try {
      fileIn = new Scanner(new File(fname));
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found");
      System.exit(-1);

    }
    while (fileIn.hasNextLine()) {

      lines[count] = fileIn.nextLine();//set to new string freq param of 

      count++;
    }

    return lines;
  }

  public static void displayFile(String[] lines) {
    Scanner keyboard = new Scanner(System.in);
    Scanner fileIn = null;
    for (int z = 0; z < lines.length; z++) {
      System.out.println(lines[z]);
    }
  }

  public static void saveFile(String fname, String[] lines) {
	  
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter file name: ");
    String newfname = keyboard.nextLine();
    try {
      Scanner fileIn = new Scanner(new File(fname));
      FileWriter outFile = new FileWriter(newfname);
      while (fileIn.hasNextLine()) {
        // System.out.print("boo");
        String input = fileIn.nextLine();
        outFile.write(input + "\n");
      }
      outFile.close();
    } catch (IOException e) {
      System.out.print("IO Exception");
      System.exit(-1);
    }
    
  }

  public static void findWord(String[] lines, String word) {
    int index = 0;
    int add = 0;
    // String[] sWords=new String[10];
    for (int i = 0; i < lines.length; i++) {
      index = lines[i].indexOf(word);
      add = index + (word.length());
      if (index != -1) {
        if ((!lines[i].substring(index - 1, index).equals(" "))
            && !lines[i].substring(index + add, index + add + 1).equals(" ")) {

        } else
          System.out.println((i + 1) + " " + lines[i]);
      }
    }
    System.out.println("\n");
  }

  public static String replaceLine(String line, String toReplace, String word) {
    int len = word.length();
    int result = line.indexOf(word);
    if (result != -1) {
      line = line.substring(0, result) + toReplace + line.substring(result + len, line.length());
    }
    return line;
  }

  public static void replaceAllLines(String[] lines, String toReplace, String word) {

    for (int i = 0; i < lines.length; i++) {
      lines[i] = replaceLine(lines[i], toReplace, word);
    }
  }

  public static void undoLast(int ans, String toReplace, String word, String[] lines) {
    if (ans >= 1) {
      replaceAllLines(lines, word, toReplace);
    } else
      System.out.println("no previous change");
  }

}