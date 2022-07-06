package test;

public class Pixel {
	private int r,g,b;
	
	public Pixel(int r,int g,int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public int compareTo(Pixel p) {
		
		if(r == p.getR() && g == p.getG() && b == p.getB()) {
			return 1;
		} else {
			return 0;
		}
		
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
	
}
