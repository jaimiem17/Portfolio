package programming2;
import java.util.Arrays;
public class JaimieMorris_SortingProject {

	public static void main(String[] args) {
		String[] sArr= {"Sharone", "Gary", "Lainey", "Jaimie", "Hallie"};
		int[] iArr= {4, 2, 1, 5, 3};
		int[] iArr2= {4, 2, 1, 5, 3};
		// TODO Auto-generated method stub
		anotherSort(sArr);
		impSelSort(iArr);
		System.out.println("bye");
		//impSelSort2(iArr2);

	}
	public static void anotherSort(String[]sArr) {
		/*Implement a static function called anotherSort that takes in an array of Strings and
		sorts it a specific way*/
		boolean shortCircuit = false;
		int counter = 0;

		while(!shortCircuit) {
			counter++;
			boolean flipped = false;
			for(int n = 1; n <sArr.length; n++) {
				String nextEl = sArr[n];
				// If n-1 value comes after n then swap positions

				if (sArr[n-1].compareTo(nextEl) > 0 ) {
					sArr[n] = sArr[n-1];
					sArr[n-1] = nextEl;
				}
				else {
					if (n == sArr.length - 1)
						flipped = true;
				}

				System.out.println("Iteration " + n + " " + Arrays.toString(sArr));
			}
			if (counter == sArr.length -1 && flipped == true)
				shortCircuit = true;
		}
		System.out.println("End Value: " + Arrays.toString(sArr));
	}


	public static void impSelSort(int[] array){

		//outer loop set up so that we don't re-examine largest element twice
		for(int n = array.length; n >=2; n--){

			int maxIndex=0;// location of the best index
			int minIndex=0;

			//initially examines entire array,then one less, then two less,....
			//finds the largest element in scanned portion
			for(int i =1; i < n; i++) {

				if(array[i] > array[maxIndex])
					maxIndex = i;
				if(array[i] < array[minIndex])
					minIndex = i;
			}

			//swap the largest value with where it belongs in the array

			int tempSwap = array[maxIndex];
			int tempLowSwap = array[minIndex];

			array[maxIndex] = array[n-1];
			array[n-1] = tempSwap;

			array[minIndex] = array[array.length - 1];
			array[n-1] = tempLowSwap;

			System.out.println(Arrays.toString(array));
		}
	}
}