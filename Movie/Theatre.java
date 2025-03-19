package programming2;
import java.util.*;
import java.util.Scanner;

public class Theatre {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String file ="movies.txt";
		MovieDatabase one=new MovieDatabase(file);
		String choice;
		//System.out.println("start");
		do {
			System.out.print("Menu Options:\nB) Buy a ticket\nL) List all Movies\nS) Show all purchased tickets\nQ) Quit\nEnter Choice: ");
			choice = keyboard.nextLine();
			choice=choice.toUpperCase();
			//keyboard.nextLine();
			if(choice.equals("B")) {
				System.out.print("Enter Customer Name: ");
				String cName = keyboard.nextLine();
				keyboard.nextLine();//had to frequently add these unsure why it wouldnt work without
				
				System.out.print("Enter Movie: ");
				String m1 = keyboard.nextLine();
				keyboard.nextLine();
				
				System.out.print("Enter arrival time (hour space minute): ");
				int hr = keyboard.nextInt();
				int min=keyboard.nextInt();
				keyboard.nextLine();
				
				Time ti= new Time(hr, min);
				Movie mov= new Movie(m1, ti);
				Customer Cus1= new Customer(cName, mov, ti);
				one.sellTicket(Cus1);//tries to sell ticket
			}
			else if(choice.equals("L")) {
				one.toString();
			}
			else if(choice.equals("S")) {
				one.manifest();//calls manifest which then calls print movie

			}
		}while(!choice.equals("Q"));//ends loop if user inputs Q
		/*

		Customer j=new Customer("jaimie", m1, t1);
		System.out.println(j.toString());
		m1.printMovie();*/

	}

}
