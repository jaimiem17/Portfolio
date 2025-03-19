package programming2;
/*
 * JAimie Morris
 * Client for Pig Latin
 */
import java.util.Scanner;

public class Client_PigLatin {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		Cryptable cryp = null;

		System.out.println("Pick your encryption type\n1. Unstoppable\n2. Vigenere\n3. PigLatin\nPick a number ");
		int choice= input.nextInt();
		
		if(choice==1)
			cryp=new VigenereCrypt();
		
		else if(choice==2)
			cryp=new UnstoppableCrypt();
		
		else if(choice==3)
			cryp=new PigLatin();
		
		convert(cryp);
	}
	public static void convert(Cryptable o) {

		Scanner input=new Scanner(System.in);

		System.out.println("Type a sentance! ");
		String sentance= input.nextLine();

		System.out.println(o.encrypt(sentance));
		
		System.out.println(o.decrypt(o.encrypt(sentance)));

	}

}
