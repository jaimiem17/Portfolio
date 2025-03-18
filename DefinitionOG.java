package programming2;
/*
 * Jaimie Morris
 * Superclass of Dictionary. Holds the word and definitions w/ accessors
 */

public class DefinitionOG {
	private String word;
	private String def;
	
	
	public DefinitionOG(String w, String d) {
		word=w;
		def=d;
	}
	public String getWord() {
		return word;
	}
	public String getDef() {
		return def;
	}

}
