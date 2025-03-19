package programming2;

public class Complex {
	private int real;
	private int imag;

	public void setR(int n) {//n stands for normal number
		real=n;
	}
	public void setI(int im) {//i stands for imaginary number
		imag=im;
	}
	public Complex sum(Complex nums){
		Complex tSum = new Complex();
		tSum.real=real+nums.real;
		tSum.imag=imag+nums.imag;
		return tSum;
	}
	public Complex diff(Complex nums){
		Complex tDiff = new Complex();
		tDiff.real=real-nums.real;
		tDiff.imag=imag-nums.imag;
		return tDiff;
	}
	public int compareTo(Complex nums){
		if(real==nums.real) {
			if(imag==nums.imag)
				return 0;
			else {
				int greaterI= (int)Math.max(imag, nums.imag);
				if (greaterI==imag)
					return 1;
				else
					return -1;
			}
		}

		int greater= (int)Math.max(real, nums.real); 
		if (greater==real)
			return 1;
		else
			return -1;

	}

	public String toString() {
		return (Integer.toString(real) +"+"+ Integer.toString(imag) + "i");
	}
}
