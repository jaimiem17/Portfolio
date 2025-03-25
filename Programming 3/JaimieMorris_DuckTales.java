import java.util.*;
import java.io.*;
public class JaimieMorris_DuckTales <E> {
	/*
	 * Jaimie Morris
	 * Ducktale project uses stacks allow someone to traverse through Tunnels and Vaults
	 */

	public JaimieMorris_DuckTales(String fname) {

		Scanner in = null;

		try {
			in = new Scanner(new File(fname));
		}catch(FileNotFoundException e) {
			System.out.println(fname + " not found");
			System.exit(-1);
		}

		Stack<Vault> theStack = new Stack<Vault>();
		Vault curVault = new Vault();
		int[] line=parseLine(in.nextLine());
		
		//sets the new current vault to the first number and gem amount
		//creates best vault varuable to hold the vault with the highest amount of gems- replaces it every time a vault is found higher
		curVault.vaultNum=line[0];
		curVault.vaultTotal=line[1];
		Vault bestVault = curVault;

		
		//loops through the whole file and at each line asks 3 if statements, one if its a new vault, one if its a vault that has previously been, and if its the same
		while(in.hasNextLine()){
			line=parseLine(in.nextLine());
			int vNum=line[0];

			//if new vault (higher num than current) push current vault and makes curr a new one 
			if(vNum>curVault.vaultNum) {

				theStack.push(curVault);
				curVault=new Vault();
				curVault.vaultNum=vNum;
				curVault.vaultTotal=0;
			}
			
			//if it returns back down to a prev vault than pops the top Vault off the stack and makes that the current vault
			if(vNum<curVault.vaultNum) {

				for(int i=0;i<=(curVault.vaultNum-vNum);i++)
					curVault=theStack.pop();

			}

			//adds the gems of current vault to the current Vault Total
			curVault.vaultTotal+=line[1];
			
			//if the gems in the current vault is greater than the current bestVault then the bestVault is replaced with current
			if(curVault.vaultTotal>bestVault.vaultTotal)
				bestVault=curVault;
		}

		System.out.print("Vault "+ bestVault.vaultNum+ " contains the most gems at "+ bestVault.vaultTotal);

	}

	//returns an array of size 2
	//spot 0 is the vault number,
	//spot 1 is the amount found at that spot in the vault
	private int[] parseLine(String line) {

		String[] sep = line.split(",");
		int[] toRet = {Integer.parseInt(sep[0]),Integer.parseInt(sep[1])};

		return toRet;
	}

	private Vault largest(Vault one, Vault two) {

		if(one.vaultTotal > two.vaultTotal)
			return one;
		else
			return two;
	}

	public class Vault{
		private int vaultNum;
		private int vaultTotal;

		public Vault() {
			vaultNum = -1;
			vaultTotal = Integer.MIN_VALUE;
		}

		public Vault(Vault other) {
			vaultNum = other.vaultNum;
			vaultTotal = other.vaultTotal;

		}

		public Vault(int[] info) {
			vaultNum = info[0];
			vaultTotal = info[1];

		}

		public String toString() {
			return "Vault "+vaultNum +" has "+vaultTotal;
		}
	}

	public static void main(String[] args) {
		new JaimieMorris_DuckTales("treasureList.txt");
	}

}


