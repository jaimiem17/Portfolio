package programming2;

import java.lang.Math;

public class Movie {
	private String name;
	private Time time;
	private int seats;
	private int tixSold;
	private Customer[] allViewers;
	

	public Movie(String movieN, Time movieT) {
		name=movieN;
		time=movieT;
		seats= (int)(Math.random() * 56) +65;
		tixSold=0;
		Customer[]allViewers=new Customer[seats];
	}

	public String getName() {
		return name;
	}
	public Time getTime() {
		return time;
	}
	public int getTixSold() {
		return tixSold;
	}
	public String toString() {
		return (name+"\t\t"+time.toString()+"\t\t"+seats+"\t\t"+tixSold);
	}
	
	public boolean isFull() {
		if (tixSold>=seats) {//checks if there are enough seats for the amount of people who bought
			return true;
		}
		tixSold++;
		return false;
	}
	public boolean sellTicket(Customer p) {
		Time arrTime=p.getTime();//gets the customers time
		if(isFull()!=true && (arrTime.compareTo(time)<0)){//compares the customers arrival time to the actual movie time
			int ctr=0;
			while(allViewers[ctr]!=null) {//goes through the list of people viewing
				ctr++;
		}
			allViewers[ctr]=p;//adds the next customer when there is an open slot
			return true;
		}
		return false;
	}
	public void printMovie() {
		System.out.println("\nMovie: "+name+"\t Time: "+time+
				"\n"+ "Max Cap: "+seats+"\t\t Current Occ: "+tixSold);
		for(int i=0;i<allViewers.length;i++) {//loops through customerds and prints if not null
			if (allViewers[i]!=null) {
				System.out.println(allViewers[i].toString());
			}
		}
	}
}

//notes : "Movie: \t\t\tTime: \t\tMax Cap: \tCurrent Cap \n"+ 
