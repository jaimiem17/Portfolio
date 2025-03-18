package programming2;

public class State {
	private String name;
	private String flower;
	private City[]cities;
	public State(String n, String f) {
		name=n;
		flower=f;
	}
	public void addCity(City newC) {
		City[]temp=new City [this.cities.length+1];
		for(int i=0;i<this.cities.length;i++) {
			temp[i]=this.cities[i];
		}	
			temp[temp.length-1]=newC;
			cities=temp;
	}
}
