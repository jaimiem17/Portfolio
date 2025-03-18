//Jaimie Morris Person Code
package programming2;

public class StringHider {
	private String original;
	private int start;
	private int end;
	private String hidden;

	//constructor
	public StringHider(String o) {
		original=o;
	}
	public String hide(int s, int e){ 
		//adds the string before the hidden part to the new vble then adds the amount of X's to hide the part then adds the rest of the unhidden original
		hidden=original.substring(0,s);
		for (int i=s;i<=e;i++) {
			hidden+="X";
		}
		hidden+=original.substring(e+1,original.length());
		return hidden;
	}
	public String recover(int s, int e) {
		//adds the hidden until it wants to be recovered, adds the rec overed, then adds the rest of the hidden word
		hidden=hidden.substring(0,s)+original.substring(s,e+1)+hidden.substring(e+1,hidden.length());
		return hidden;
	}
	public String toString() {
		return hidden;
	}
}