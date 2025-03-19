package programming2;
/*
 * Jaimie Morris
 * This program extends employee. Adds/removes employees from an array as well as makes a salary 
 * 1. The manager toString method will run even though it was just called through
 * the Executive method because Executive is a class extension of Manager. Since they share
 * variables, the Executive can be treated like a Manager
 * 
 * 2. It does not add the bonus salary.
 * 
 * 3. The Executive toString would give the info including the bonus Salary.
 */

import java.util.NoSuchElementException;

public class Manager extends Employee{

	private Employee[] employees=new Employee[0];

	public Manager(String n, int s, String d) {
		super(n, s, d);
	}
	//creates a new array to add employees and will add the old array using a for loop
	public void addEmployee(Employee toAdd) {
		if(toAdd.getDepartment()!=super.getDepartment()) {
			throw new IllegalArgumentException("Departments must be the same.");
		}
		Employee[] arr=new Employee[employees.length+1];
		if(employees.length>0) {
			for (int i =0;i<employees.length;i++) {
				arr[i]=employees[i];
			}
		}
		arr[employees.length]=toAdd;
		employees=arr;
	}

	//creates a new array leaving out the employee that is to be removed
	public void removeEmployee(int empID) {
		Employee toRemove=null;
		for (int i =0;i<employees.length;i++) {
			if(employees[i].getID()==empID)
				toRemove=employees[i];
		}
		if (toRemove.equals(null))
			throw new NoSuchElementException();

		Employee[] arr=new Employee[employees.length-1];
		int ctr=0;
		while(!employees[ctr].equals(toRemove)) {
			arr[ctr]=employees[ctr];
			ctr++;
		}
		if(ctr!=arr.length) {
			for(int i=ctr+1;i<arr.length;i++)
				arr[i]=employees[i+1];
		}
		employees=arr;
	}
	//starts with manager salary and then adds on each employee
	public int getSalary() {
		int total=super.getSalary();
		for (int i=0;i<employees.length;i++) {
			total+= employees[i].getSalary();
		}
		return total;
	}

	//if there are employees in the array the string eList will hold them all to return later
	public String toString() {
		if(employees.length>0) {
			String eList="\nEmployees: ";
			for (int i=0;i<employees.length;i++) {
				eList+=(employees[i].toString())+"\n";
			}
			return ("Manager "+ super.toString() + eList);
		}
		return ("Manager "+ super.toString());
	}



}
