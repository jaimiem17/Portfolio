package programming2;
/*
 * Jaimie Morris
 * Client of TravelGuide comparable interface practice program
 * It is best to write an insertion sort of Comparables because you can send different 
 * types of variables without continuously rewriting code
 */
public class Client_ComparableInterface {
	
	public static void main(String[] args) {
		
		Comparable []array=new Comparable[5];
		
		array[0] = new TravelGuide("Hawaii", 6.00, 6, "Honululu");
		array[1] = new TravelGuide("Japan", 7.60, 8, "Tokyo");
		array[2] = new TravelGuide("Maldives", 9.50, 2, "Addu City");
		array[3] = new TravelGuide("Tennessee", 4.00, 13, "Nashville");
		array[4] = new TravelGuide("California", 6.70, 2, "San Diego");
		
		/*
		array[0] = 2;
		array[1] = 3;
		array[2] =1;
		array[3] = 5;
		array[4] = 4;
		 */
		/*
		array[0] = "egg";
		array[1] = "blueberry";
		array[2] ="cheese";
		array[3] = "apple";
		array[4] = "donut";
		*/
		insertionSort(array);
		
	}

	public static void insertionSort(Comparable[] array) {
		
		for(int n = 1; n <array.length; n++){
			
			Comparable nextElement = array[n];
			
			int i = n;

			while( i>0	&&	nextElement.compareTo(array[i-1]) < 0){
				
				array[i] = array[i-1];
				i--;
				
			}
			
			array[i] = nextElement;

			for(int j = 0; j < array.length; j++)
				
				System.out. println( array[j] + " ");
			
			System.out.println();



		}
	}
}

//while( i > 0 && (nextElement.compareTo(array[i-1]))<0){
