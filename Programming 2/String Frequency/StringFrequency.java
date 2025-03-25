/**
 * Jaimie Morris
 * string freq program
 */
package programming2;


public class StringFrequency {
	private String word;
	private int[]arr=new int[26];

	public StringFrequency(String word) {
		word=word.toLowerCase();
		for(int i=0;i<word.length();i++) {
			arr[(int) word.charAt(i)-97]++;
			//System.out.println(arr[(int) word.charAt(i)-97]);
		}
	}//copy constructor
	public StringFrequency(StringFrequency second) {
		this.word=second.word;

		for(int i=0;i<arr.length;i++) {
			arr[i]=second.arr[i];
		}
	}


	//accessor method
	public String getWord() {
		return word;
	}

	public boolean hasSameFreq(StringFrequency other) {
		for(int p=0;p<arr.length;p++) {
			if(arr[p]!=other.arr[p]){
				return false;

			}
		}
		return true;

	}
}


