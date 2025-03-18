/*
 * Jaimie Morris
 * IntegerSet program 
 * cli5
 * Takes in sets from client and compares them to the objects by adding and removing/ finding union, intersections, and if equal
 */
package programming2;

public class IntegerSet {
	private boolean[] set;

//constructor
	public IntegerSet(int i) {
		set = new boolean[i];
	}
	//copy constructor w/ deep copy
	public IntegerSet(IntegerSet other) {
		other= new IntegerSet(set.length);
		for(int i=0;i<set.length;i++) {
			other.set[i]=set[i];
		}
	}
	//add and remove function just take in the number they want to add/remove and switch to true/false
	public void add(int toAdd){
		if (toAdd<set.length) {
			set[toAdd] = true;
		}
	}
	public void remove(int toRemove){
		if (toRemove<set.length) {
			set[toRemove] = false;
		}
	}//goes thru the array and returns false if there is any true vbles inside
	public boolean isEmpty() {
		for (int i=0;i<set.length;i++) {
			if(set[i]==true) 
				return false;
		}
		return true;
	}
	//function uses a loop to check if the numbers in the array ever line up and adds those to a new array
	public IntegerSet intersection(IntegerSet other) {
		IntegerSet setN = new IntegerSet(set.length);

		for(int p=0;p<set.length;p++) {
			if(set[p]==true&&other.set[p]==true) {
				setN.set[p]=true;
			}
		}
		return setN;

	}
	//function uses a loop to add the numbers from the array into a new array
	public IntegerSet union(IntegerSet other) {
		IntegerSet setN;
		if(set.length>other.set.length) 
			setN = new IntegerSet(set.length);
		else
			setN = new IntegerSet(other.set.length);
		for(int p=0;p<set.length;p++) {
			if(set[p]==true)
				setN.set[p]=true;

		}
		for(int p=0;p<other.set.length;p++) {
			if (other.set[p]==true)
				setN.set[p]=true;

		}
		return setN;
	}
	//runs thru both arrays to check if either line up
	public boolean equals(IntegerSet other) {;
	//System.out.println(other.set.length);
	for(int p=0;p<set.length;p++) {
		if(set[p]==true&&other.set[p]==false){
			
			return false;
			//System.out.println(p+"\t"+set[p]);
		}
	}
	return true;

	}


//beautiful toString method that does not put a comma afterwards
	public String toString() {
		String fSet="{";
		for (int i=0;i<set.length;i++) {
			if(set[i]==true)
				fSet+=i+",";
		}
		int l=fSet.length();
		if(fSet.substring(l-1,l).equals(",")) {
			fSet=fSet.substring(0,l-1);
		}
		return fSet+"}";
	}
}