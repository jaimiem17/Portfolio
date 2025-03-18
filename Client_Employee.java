package programming2;

public class Client_Employee {

	public static void main(String[]args) {
		Employee fred=new Employee("Fred",200,"HR");
		Employee ted=new Employee("Ted",200,"HR");
		Manager steve=new Manager("Steve", 500, "HR");
		Executive bob=new Executive("Bob", 700, "HR", .3 );
		steve.addEmployee(fred);
		steve.addEmployee(ted);
		
		System.out.println(fred.getSalary());
		System.out.println(steve.getSalary());
		System.out.println(bob.getSalary());
		
		System.out.println(fred.toString());
		System.out.println(steve.toString());
		System.out.println(bob.toString());
		steve.removeEmployee(2);

		System.out.println(steve.toString());

	}

}
