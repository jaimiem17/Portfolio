package programming2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovieDatabase {
	private Movie []movieList;

	public MovieDatabase(String fname) {
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
		movieList = new Movie[count];
		count = 0;
		fileIn.close();
		try {
			fileIn = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(-1);

		}
		while (fileIn.hasNextLine()) {
			String name=fileIn.nextLine();
			int h=fileIn.nextInt();
			int m=fileIn.nextInt();
			fileIn.nextLine();
			movieList[count]=new Movie(name,new Time(h,m));

			count++;
		}


	}

	public void sellTicket(Customer star) {
		Movie wantSee=null;
		for(int i =0;i<movieList.length;i++) {
			if(movieList[i]==star.getMovie()) {
				wantSee=movieList[i];
			}
			
		}
		if (wantSee==null) {
			System.out.println("Movie does not exist");//if the movie that wants to be seen stays null it means it is not in the list of movies
		}
		else if(wantSee.sellTicket(star)==true) {//runs the sellTicket func to see if ticket is available
			System.out.println("Ticket Sold!");
		}
		else 
			System.out.println("Unable to Purchase Ticket");
	}

	public String toString() {
		String bigString="";
		for(int i=0;i<movieList.length;i++) 
			bigString+="Movies:\t\tTime:\t\tMax Seats:\t\tSeats Filled:\n"
					+ movieList[i].toString();
		return bigString;//had to return a variable because it needed to loop through all of the movies prior to returning
	}

	public void manifest() {

		System.out.println("Movies:\t\tTime:\t\tMax Seats:\t\tSeats Filled:\n"); 
		for(int i=0;i<movieList.length;i++) 
			movieList[i].printMovie();
	}
}


/*notes for(int i=0;i<movieList.length;i++) 
return("Movies:\t\tTime:\t\tMax Seats:\t\tSeats Filled:\n"
		+ movieList[i].toString());
		*/
