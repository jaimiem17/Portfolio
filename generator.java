package programming2;

public class generator {


	public static void main(String[] args) {
		int []markers ={220, 187, 193, 206, 173, 165, 150, 888, 123, 3955, 102, 917, 2288, 377, 371, 354, 353, 335, 7743, 323, 9421, 279, 659, 298, 286, 301, 271, 2715, 2582, 267, 268, 1895, 230, 231, 232, 234, 489, 712, 155, 7502, 7507, 134, 715, 153, 126, 2967, 7565, 401, 2321, 121, 462, 900, 959, 5305, 545, 430, 431, 7546, 444};
		
		double rand = Math.random()*60; 
		System.out.println(markers[(int) rand]);
	}

}
