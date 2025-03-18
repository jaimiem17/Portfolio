//Jaimie Morris Person Code
/* When a Person object is created using a zero parameter constructor it
 * prints an exception and says constructor Person() is undefined */
package programming2;

public class Person {
	private String name;
	private int age;
	private String bday;
	
	
	//constructor
	public Person(int a, String n, String b) {
		name=n;
		bday=b;
		if (a<0) {
			//exception keeps age from being inputed a smaller number than valid
			throw new IllegalArgumentException("Age too small");
		}
		age=a;
	}
	//constructor2
	public Person(String n, String b) {
		this(0,n,b);
	}
	
	
	/* NO MUTATORS
	//mutator
	public void setAge(int a) {
	//sets age to a so it can be passed to getAge
		if (a<0) {
			//exception keeps age from being inputed a smaller number than valid
			throw new IllegalArgumentException("Age too small");
		}
		age=a;
	}
	public void setName(String n) {
		//sets name to n so it can be passed to getName
		name=n;
	}
	public void setBday(String b) {
		bday=b;
	}*/
	
	public void isBirthday(String d) {
		if(d.equals(bday)) {
			System.out.print("Happy Birthday");
		}
		age++;
	}
	//accessor method calls getAge so that client can print what is inside age
	public int getAge() {
		return age;
	}
	//accessor method calls getName so that client can print what is inside the vble name
	public String getName() {
		return name;
	}
	public String getBday() {
		return bday;
	}
	public String toString() {
		return "Age: " + age + "\nName: " + name+ "\nBirthday: "+bday;
	}
}

