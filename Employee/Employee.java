package programming2;
/*
 * Jaimie Morris
 * This program creates an employee with a name, salary, ID, and department name. Super class
 */
import javax.swing.*;

public class Employee {

	private String name;
	private int ID;
	private int salary;
	private String depName;
	private static int nextID = 1;

	public Employee(String n, int s, String d) {
		name=n;
		ID = nextID;
		nextID++;
		salary=s;
		depName=d;
	}
	public void changeSalary(int newSal) {
		salary=newSal;
	}
	public void changeSalary(double salInc) {
		salary+= salary*salInc;
	}

	public int getSalary() {
		return salary;
	}
	public String getDepartment() {
		return depName;
	}
	public int getID() {
		return ID;
	}
	public String toString() {
		return ("Employee Name: "+name+" ID: " +ID+" Salary: "+ getSalary()+" Department: "+ depName);
	}

}
