package programming2;

public class Client5 {

	public static void main(String[] args) {
		IntegerSet firstSet = new IntegerSet(10);
		firstSet.add(3);
		firstSet.add(1);
		firstSet.add(5);

		System.out.println(firstSet);				        // {1,3,5}

		IntegerSet secondSet = new IntegerSet(20);
		secondSet.add(15);
		secondSet.add(5);
		secondSet.add(1);
		secondSet.add(4);
		secondSet.remove(4);

		System.out.println(firstSet.isEmpty());
		
		System.out.println(firstSet.intersection(secondSet));  // {1,5}	

		System.out.println(firstSet.union(secondSet));	      // {1,3,4,5,15}

		System.out.println(firstSet.equals(secondSet));			//false

		IntegerSet thirdSet = new IntegerSet(20);
		thirdSet.add(3);
		thirdSet.add(5);
		thirdSet.add(1);

		System.out.println(firstSet.equals(thirdSet));		// true

	}

}
