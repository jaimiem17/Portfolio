package programming2;

public class Hallway {
	private boolean near;
	private boolean far;
	private boolean light; //starts off
	

	public void setnear(boolean s1) {
		near=s1;
		if (!light)
			light=true;
		else
			light=false;
	}
	public void setfar(boolean s2) {
		far=s2;
		if (!light)
			light=true;
		else
			light=false;
	}
	public void setLight(boolean li) {
		light=li;
	}

	public boolean getNear() {
		return near;
	}

	public boolean getFar() {
		return far;
	}
	
	public boolean getLight() {
		return light;
	}

	public String toString() {
		String n, f, l;
		if(near==true) 
			n="up";
		else
			n="down";
		if(far==true) 
			f="up";
		else
			f="down";
		if(light==true)
			l="on";
		else
			l="off";
		return "The first switch is " +n+ "; The second switch is " + f;
	}
}

