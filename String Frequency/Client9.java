package programming2;

public class Client9 {

	public static void main(String[] args) {
		String word= "abcdefaa";
		StringFrequency first=new StringFrequency(word);
		word="abcdefcdef";
		StringFrequency second=new StringFrequency(word);

		StringFrequency third=new StringFrequency(second);
		System.out.println(first.hasSameFreq(second));
		System.out.println(third.hasSameFreq(second));
	}

}
