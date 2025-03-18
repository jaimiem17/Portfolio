package programming2;

///JAIMIE morris
//This program converts an english sentence to pig latin also changing it to the correct capitilization
import java.util.*;


public class PigLatin implements Cryptable{

	//calls what was previously in main. It starts to call the functions to encrypt.
	public String encrypt(String text) {

		String[] english=text.split(" ");
		String final1 =PhraseToPig(english);

		return final1;
	}

	private String wordToPig(String english){
		String word=" ";
		int len=english.length();
		String ay="-ay ";
		int vow=HasAVowel(english);
		if (vow==-1||vow==0)
			word=english+ay;
		else{
			word=english.substring(vow, len);
			word+="-"+ english.substring(0, vow)+"ay ";
		}
		//finy=PhraseToPig(word);
		return word;
	}
	//finding the first vowel was hard cause it goes to the first A since thats the beginning of the vowels - this checks each letter individually for all vowels and returns the the place of first vowel it catches
	private int HasAVowel(String english){
		int indie=0;
		english=english.toLowerCase();
		String voo="aeiou";
		/* for (int i=0;i<voo.length();i++){
	      indie= english.indexOf(voo.substring(i,i+1));
	      if (indie!=-1)
	        return indie;
	    }*/

		int i=0;
		for (i=0;i<english.length();i++){
			String A=english.substring(i,i+1);
			for (int u=0;u<voo.length();u++){
				String B=voo.substring(u,u+1);
				indie=A.indexOf(B);
				//System.out.println(i);
				if (indie==0)
					return i;
			}
		}


		return indie;
	}
	private String PhraseToPig(String[] english){
		boolean bent=true;
		String newsenny="";
		String conv="";
		for (int i=0;i<english.length;i++){
			conv=wordToPig(english[i]); 
			//System.out.println(conv);
			bent=isCapitalized(conv);
			if (bent==true)
				conv= capitalize(conv);
			newsenny+=conv;
		}
		return newsenny;
	}
	//switches the word to cap before it is put in the sentance
	private boolean isCapitalized (String word){
		int find=word.indexOf("-");
		String cop=(word.substring(find+1,find+2).toUpperCase());
		if ((word.substring(find+1,find+2)).equals(cop)){
			return true;
		}

		return false;
	}
	private String capitalize(String word){
		word=word.toLowerCase();
		String hola=(word.substring(0,1)).toUpperCase();
		word=hola+(word.substring(1,word.length()));
		//System.out.println(word);
		return word;
	}

//first checks if the word is capitalized so it knows to change it back. Then finds ay and adds what comes before that (after the -) and then the beginning of the word in the array
	public String decrypt(String text) {
		
		boolean isCap=false;
		String unciphered="";
		String[] words=text.split(" ");
		
		for(int i=0;i<words.length;i++) {
			isCap=false;
			
			int val=words[i].indexOf("-");

			String cap=words[i].substring((0),(1)).toUpperCase();
			
			if ((words[i].substring(0,1)).equals(cap)){	
				isCap=true;
			}
			
			int a=words[i].indexOf("ay");
			
			if(!words[i].substring((val+1),(val+2)).equals("a")){
				words[i]=(words[i].substring((val+1),a))+(words[i].substring(0,val));
			}
			
			else
				words[i]=(words[i].substring(0,val))+" ";
			
			if(isCap==true)
				words[i]=capitalize(words[i]);
		}
		
		for (int i=0;i<words.length;i++)
			
			unciphered+=words[i]+" ";
		
		return unciphered;
	}
}

