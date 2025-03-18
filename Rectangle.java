package programming2;

public class Rectangle {
	private int length;
	private int width;
	
	//mutator
	public void setWidth(int w) {
		if (w>20||w<0) {
			//exception keeps width from being inputed a smaller number than valid
			throw new IllegalArgumentException("width invalid");
		}
		width=w;
	}
	public void setLength(int l) {
		if (l>20||l<0) {
			throw new IllegalArgumentException("length invalid");
	}
		length=l;
	}
	// fill in below
	public boolean isSquare() {
		if (width==length) {
			return true;
		}
		else {
			return false;
		}
	}
	public int getPerim() {
		return (width*2)+(length*2);
	}
	public int getArea() {
		return (width*length);
	}
	// Accessor Method
	public int getWidth() {
		return width;
	}
	//Accessor Method
	public int getLength() {
		return length;
	}
	// make the below more meaningful
	public String toString() {
		return "Rectangle width: " + width + "; Rectangle length: " + length;
	}
}

