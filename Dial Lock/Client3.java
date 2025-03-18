package programming2;

public class Client3 {

	public static void main(String[] args) {
		DialLock nums=new DialLock();
		SafeDialLock safe=new SafeDialLock();
		for(int i=0;i<14;i++) {
			safe.turnRight();
		}
		for(int i=0;i<17;i++) {
			safe.turnLeft();
		}
		for(int i=0;i<7;i++) {
			safe.turnRight();
		}

		System.out.println("Unlocked = "+safe.open());
		System.out.println("Unlocked = "+safe.open());
		safe.reset();
		System.out.println("Unlocked = "+safe.open());
		System.out.println("Unlocked = "+safe.open());
		for(int i=0;i<13;i++) {
			safe.turnRight();
		}
		for(int i=0;i<18;i++) {
			safe.turnLeft();
		}
		for(int i=0;i<2;i++) {
			safe.turnRight();
		}
		System.out.println("Unlocked = "+safe.open());
		
		safe.reset();

	}
}
