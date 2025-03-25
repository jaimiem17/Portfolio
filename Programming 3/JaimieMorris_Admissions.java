/*
 * Jaimie Morris
 * 
 * 
 */
import java.util.*;
import java.io.*;


public class JaimieMorris_Admissions {
	Queue<Student> unmatched = new LinkedList<Student>();

	public JaimieMorris_Admissions(String fnames, String fschools) {
		Scanner in = null;

		ArrayList<Prospect> studs = new ArrayList<Prospect>();
		ArrayList<College> collg = new ArrayList<College>();

		//Load student file
		try {
			in = new Scanner(new File(fnames));
		}catch(FileNotFoundException e) {
			System.out.println(fnames + " not found");
			System.exit(-1);
		}


		//loads the file of names into the queue 
		while(in.hasNext()) {
			Student name = new Student (in.nextLine());
			unmatched.add(name);
			studs.add(name);
		}

		//Load college file
		try {
			in = new Scanner(new File(fschools));
		}catch(FileNotFoundException e) {
			System.out.println(fschools + " not found");
			System.exit(-1);
		}


		//loads the file of names into the queue 
		while(in.hasNext()) {
			College name = new College (in.nextLine());
			collg.add(name);
			name.generateRankings(studs);
		}

		//generates rankings for all students and colleges
		for(int i = 0;i<collg.size();i++) {
			studs.get(i).generateRankings(collg);
		}

		//print students + rankings
		System.out.println("Student\n-----------");
		for(int i = 0;i<studs.size();i++) {
			System.out.print((studs.get(i)).toString() + ":  [");
			for(int j = 0;j<studs.size();j++) {
				System.out.print((studs.get(i).getRankings()).get(j).toString() + ", ");

			}
			System.out.println("]");
		}
		
		System.out.println();
		
		//prints colleges + rankings
		System.out.println("Colleges\n-----------");
		for(int i = 0;i<collg.size();i++) {
			System.out.print((collg.get(i)).toString() + ":  [");
			for(int j = 0;j<collg.size();j++) {
				System.out.print((collg.get(i).getRankings()).get(j).toString() + ", ");

			}
			System.out.println("]");
		}
		

	}

	public void matching() {
		while(!unmatched.isEmpty()) {

			Student current=unmatched.peek();
			ArrayList<Prospect> ranks =current.getRankings();

			for(int i=0;i<ranks.size();i++) {
				System.out.print(current.apply());
			//		unmatched.remove();
			}

		}

	}


	public class Prospect{
		private ArrayList<Prospect> ranking;
		private Prospect match = null;
		private String name;

		public Prospect(String name){
			ranking = new ArrayList<Prospect>();
			this.name = name;

		}

		public void generateRankings(ArrayList<? extends Prospect> prospectNames) {

			ArrayList<Prospect> copy = new ArrayList<Prospect>();

			for(int i = 0; i < prospectNames.size(); i++)
				copy.add(prospectNames.get(i));

			while(copy.size()!=0){
				ranking.add(copy.remove((int)(Math.random()*copy.size())));
			}

			
		}

		public void setMatch(Prospect val){
			match = val;
		}

		public Prospect getMatch(){
			return match;
		}

		public ArrayList<Prospect> getRankings(){
			return ranking;
		}

		public String toString() {
			return name;
		}

		//assume everyone has a diff name
		public boolean equals(Object o) {
			if(!(o instanceof Prospect))
				return false;
			Prospect other = (Prospect)o;
			return name.equals(other.name);

		}
	}
	public class Student extends Prospect{

		public Student(String name) {
			super(name);
		}

		public boolean apply() {

			ArrayList<Prospect> ranks =getRankings();

			//if the students preferred college also prefers the student more than its current match then accept
			if(getRankings().get(0).getMatch().isPreferred(this)) {
				((College) getRankings().get(0)).acceptStudent(this);
				return true;
			}

			//else: modifies the students top school
			else {
				getRankings().remove(0);
				return false;
			}
		}
	}

	public class College extends Prospect{


		public College(String name) {
			super(name);
		}

		public boolean isPreferred(Student other) {
			int curSpot=-1;
			int othSpot=-1;
			Student curr = (Student) getMatch();
			ArrayList<Prospect> ranks =getRankings();

			for(int i=0;i<ranks.size();i++) {

				if(ranks.get(i).equals(curr))
					curSpot=i;
				if(ranks.get(i).equals(other))
					othSpot=i;
			}

			return curSpot<othSpot;
		}

		public void acceptStudent(Student stu) {

			if(getMatch()!=null) {
				Student reject = (Student) getMatch();
				reject.setMatch(null);
				unmatched.add(reject);
			}
			else
				setMatch(stu);
		}
	}


	public static void main(String[] args){
		JaimieMorris_Admissions start = new JaimieMorris_Admissions("names.txt", "schools.txt");

		start.matching();


	}

}



