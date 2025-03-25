/*
 * Jaimie Morris
 */
import java.util.*;
import java.io.*;

public class Adventure {

	public Adventure(String fname) {

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


		do {


			if(curLine.availableChoices.size()>1) {

				next = curLine.availableChoices.get(1);
				System.out.println(next);
				System.out.println("hello");
				curStoryFull+=curLine.content + "\n";
				curLine=lines.get(curLine.availableChoices.get(0));
				
				Story nextStoryLine = new Story(curStoryFull, lines.get(next), next+1);
				theStack.push(nextStoryLine);
				System.out.println("curr: "+curLine.content);

			}

			else if(curLine.availableChoices.size()==1) {
				next = (curLine.availableChoices.get(0))+1;
				System.out.println("hi"+ next);
				curStoryFull+=curLine.content + "\n" + lines.get(curLine.availableChoices.get(0)).content + "\n";
				System.out.println("curr: "+curLine.content);		
				curLine=lines.get(next);
				System.out.println("hi"+ next);
				System.out.println("curr: "+curLine.content);

			}

			else {
				curStoryFull+=curLine.content + "\n";
				curLine=lines.get(next);

				if(next>lines.size())
					next++;
				else {
					System.out.println(curStoryFull);
					Story newVersion = theStack.peek();
					curStoryFull= "\n-----------\n" +newVersion.storyPart;
					curLine=newVersion.lineCont;
					theStack.pop();
					
				}
			}

	//		System.out.println(curStoryFull);

		} while(!theStack.isEmpty());

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
		private Line lineCont;
		private int nextInd;

		public Story(String s, Line lineIn, int n) {
			storyPart=s;
			lineCont = lineIn;
			nextInd = n;
		}
	}

	public static void main(String[] args) {

		new Adventure("adventure.txt");

	}
}
