package programming2;
/*
 * Jaimie Morris
 * This program extends manager. Executive is a type of manager.
 */

public class Executive extends Manager {
	
	private double bonus;
	
	public Executive(String n, int s, String d, double b) {
		super(n,s,d);
		bonus=b;
	}
	//calls to String from manager and has to cast to int bc bonus is a dub less than 0
	public int getSalary() {
		return (int)(super.getSalary()+(super.getSalary()*bonus));
	}

}
