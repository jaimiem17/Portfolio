package programming2;

public class Client4 {

	public static void main(String[] args) {
		Rational n1 = new Rational(3, 9);
		Rational n2 = new Rational(2,3);

		Rational sum=n1.sum(n2);
		System.out.println("Sum: "+sum);
		Rational diff=n1.diff(n2);
		System.out.println("Diff: "+diff);	
		Rational prod=n1.prod(n2);
		System.out.println("Prod: "+prod);
		Rational quo=n1.quo(n2);
		System.out.println("Quo: "+quo);
	}

}
