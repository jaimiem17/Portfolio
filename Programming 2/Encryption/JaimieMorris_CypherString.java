//Jaimie Morris
//This program takes a word and cyphers it using a random alphabet generator
import java.util.*;
public class JaimieMorris_CypherString {
	public static void main(String[] args) {
		
		Scanner hola=new Scanner(System.in);
		
		String alpha="abcdefghijklmnopqrstuvwxyz";
		System.out.println("Type a word");
		
		String word= hola.nextLine();
		String fake="";
		
		fake=fook(alpha);
		word=cyph(alpha, fake, word);
		
		System.out.println(word);
	}
	public static String fook(String alpha){
		
		int r =0;
		int b=0;
		int j=26;
		
		//System.out.println(alpha);
		r=(int)(Math.random()*26+1);
		String a=alpha.substring(r,r+1);
		String fake=a;
		//int result =(int)(Math.random()*26+1);
		
		do{
			
			//for (int u=0;u<j;u++){
			b=0;
			r=(int)(Math.random()*26);
			String h=alpha.substring(r,r+1);
			
			for (int i=0;i<fake.length();i++){
			
				//System.out.println(h+fake.substring(i,i+1));
				if (h.equals(fake.substring(i,i+1))){

					b++;
				}
				
			}
			
			if (b==0){
				fake+=h;
			}
			
			else{
				//j++;
			}
			
			//System.out.println(fake);
			
		}while(fake.length()<26);
		//System.out.println(fake);

		return fake;
	}

	public static String cyph(String alpha, String fake, String word){
		
		String neword="";
		int a=0;
		String c="";
		
		for (int i=0;i<word.length();i++){
			a= alpha.indexOf(word.substring(i,i+1));
			c=fake.substring(a,a+1);
			neword+=c;
		}
		
		return neword;
	}
}