import java.util.*;

import java.io.*;

public class CreateYourOwnAdventure<E> {

	public CreateYourOwnAdventure(String fname) {

		Scanner in = null;

		try {
			in = new Scanner(new File(fname));
		}catch(FileNotFoundException e) {
			System.out.println(fname + " not found");
			System.exit(-1);
		}

		ArrayList<Line> lines = new ArrayList<Line>();

		Stack<Story> theStack = new Stack<Story>();

		while(in.hasNext()) {
			lines.add(new Line (in.nextLine()));
		}

		for(int i =0;i<lines.size();i++){
			if (lines.get(i).availableChoices.size() > 1) {
				Story line1 = new Story(lines.get(i), 0);
				line1.addLine(lines.get(lines.get(i).availableChoices.get(0)));
				theStack.push(line1);
			}
			else if (lines.get(i).availableChoices.size() == 1) {
				Story line1 = new Story(lines.get(i), 0);
				line1.addLine(lines.get(lines.get(i).availableChoices.get(0)));
			}
			else {
				Story line1 = new Story(lines.get(i), 0);
				line1.addLine(lines.get(i+1));
			}
		}



		//the majoroity of your code goes here
	}

	//a line from the txt file, broken up by semi-colon
	public class Line{

		private String content;
		private ArrayList<Integer> availableChoices;		//stores all numeric choices found on a line in the file

		public Line(String line) {

			availableChoices = new ArrayList<Integer>();

			String[] sep = line.split(";");
			content = sep[0];

			if (sep.length>0) {
				for (int i = 1; i < sep.length; i++) 
					availableChoices.add(Integer.parseInt(sep[i]));
			}

			//your code goes here
			//assigns a value to content and to the AL (as necessary)
		}

		public int numChoices() {
			return availableChoices.size();
		}
	}

	public class Story{

		private String content;
		private int choice;

		public Story (Line line, int ch) {
			content = line.content;
			choice = ch;
		}

		public void addLine(Line l){
			content+=l.content;
			System.out.println(content);
		}


		//you must determine appropriate instance variables, constructors, etc.
	}
	public static void main(String[] args) {
		new CreateYourOwnAdventure("adventure.txt");
	}
}


