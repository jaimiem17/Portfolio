/*
 * Jaimie Morris
 */
import java.util.*;
import java.io.*;

public class JaimieMorrisAdventure {

	public JaimieMorrisAdventure(String fname) {

		Scanner in = null;

		try {
			in = new Scanner(new File(fname));
		}catch(FileNotFoundException e) {
			System.out.println(fname + " not found");
			System.exit(-1);
		}

		ArrayList<Line> lines = new ArrayList<Line>();

		String curStoryFull="";
		int next = 0;

		Stack<Story> theStack = new Stack<Story>();


		while(in.hasNext()) {
			Line l = new Line (in.nextLine());
			lines.add(l);
		}
		Line curLine = lines.get(0);


		boolean stop = false;
		//delete later
		int iters=1;

		while (!stop) {


			if(curLine.availableChoices.size()>1) {

				next = curLine.availableChoices.get(1);

				curStoryFull+=curLine.content + "\n";

				Story nextStoryLine = new Story(curStoryFull, next);
				theStack.push(nextStoryLine);
				stop=false;

				curLine=lines.get(curLine.availableChoices.get(0));


			}

			else if(curLine.availableChoices.size()==1) {

				next = (curLine.availableChoices.get(0))+1;
				curStoryFull+=curLine.content + "\n" ;//+ lines.get(curLine.availableChoices.get(0)).content + "\n";

				curLine=lines.get((curLine.availableChoices.get(0)));

			}

			else {
				curStoryFull+=curLine.content + "\n";

		//		System.out.println(next);

				System.out.println(iters+": \n"+curStoryFull+"\n- - - - - - - - - - - - - - - - - - -\n");


				if(theStack.isEmpty()) {
					stop = true;
				}
				else {		
					Story newVersion = theStack.pop();
					curStoryFull= newVersion.storyPart;
					curLine=lines.get(newVersion.nextInd);
					iters++;

				}
			}


			//		System.out.println(curStoryFull);

		}

		//	System.out.println(curStoryFull);
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

		private String storyPart;
		private int nextInd;

		public Story(String s, int n) {
			storyPart=s;
			nextInd = n;
		}
	}

	public static void main(String[] args) {

		new JaimieMorrisAdventure("adventure.txt");

	}
}
