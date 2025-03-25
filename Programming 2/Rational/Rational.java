/*
 * Jaimie Morris
 * Rational Class program
 * Adds, subtracts, multiplies and divides a given rational
 */
package programming2;

public class Rational {
	private int numer;//numerator
	private int denom;//denominator

	//constructor
	public Rational(int n, int d) {

		numer=n;
		if (d==0) {
			//exception keeps age from being inputed a smaller number than valid
			throw new IllegalArgumentException("denominator too small");
		}
		denom=d;
	}

//adds the rational then calls the reduce function before returning the Final Sum
	public Rational sum(Rational nums){
		int top;
		int bottom;
		top=(numer*nums.denom)+(nums.numer*denom);
		bottom=denom*nums.denom;
		Rational tSum=new Rational(top,bottom);
		Rational tFSum=reduce(tSum);
		return tFSum;
	}
	//subtracts the rational then calls the reduce function before returning the Final Difference
	public Rational diff(Rational nums){
		int top;
		int bottom;
		top=(numer*nums.denom)-(nums.numer*denom);
		bottom=denom*nums.denom;
		Rational tDiff = new Rational(top, bottom);
		Rational tFDiff=reduce(tDiff);
		return tDiff;
	}
	//Multiplies the rational then calls the reduce function before returning the Final Product
	public Rational prod(Rational nums){
		int top;
		int bottom;
		top=numer*nums.numer;
		bottom=denom*nums.denom;
		Rational tProd = new Rational(top, bottom);
		Rational tFProd=reduce(tProd);
		return tProd;
	}
	//divides the rational then calls the reduce function before returning the Final Quotient
	public Rational quo(Rational nums){
		int top;
		int bottom;
		top=numer*nums.denom;
		bottom=denom*nums.numer;
		Rational tQuo = new Rational(top, bottom);
		Rational tFQuo=reduce(tQuo);
		return tQuo;
	}//calls a function that find the greatest common denominator and then uses that number to reduce the fraction by dividing the top and bottom by it
	public Rational reduce(Rational rat) {
		int n = 0;
		int gcf=findGCF(rat.numer, rat.denom);
		if (gcf==0) {
			return rat;
		}
		rat.numer=rat.numer/gcf;
		rat.denom=rat.denom/gcf;

		return rat;
	}
	public int findGCF(int n1, int n2) {
		if (n2 != 0)
            return findGCF(n2, n1 % n2);
        else
            return n1;
	}


	public String toString() {
		return (Integer.toString(numer) +"/"+ Integer.toString(denom));
	}
}
