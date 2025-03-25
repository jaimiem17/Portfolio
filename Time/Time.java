package programming2;

public class Time {
	private int h;
	private int m;

	public Time(int hour, int minute) {
		h=hour;
		m=minute;
	}
	public String toString() {//toString method
		return (h+":"+m);
	}
	public int compareTo(Time movi1) {//puts the number in its full form to compare
		int full= (h*1000)+m;
		int full_m= (movi1.h*1000)+movi1.m;
		if (full==full_m) 
			return 0;

		else if(full<full_m) 
			return -1;

		else
			return 1;
	}

}
