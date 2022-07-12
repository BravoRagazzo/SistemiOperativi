package model;

import java.util.Comparator;

public class Pixel implements Comparator<Pixel>{
	private int r,g,b,a;
	private int avg;
	
	
	public Pixel(int r,int g,int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		avg = (r+g+b)/3;
	}

//	public Pixel(int r,int g,int b, int a) {
//		this.a = a;
//		this.r = r;
//		this.g = g;
//		this.b = b;
//		avg = (r+g+b)/3;
//	}
	
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
//
//	public int getA() {
//		return a;
//	}
//	
//	public void setA(int a) {
//		this.a = a;
//	}
	public int getAvg() {
		return avg;
	}

	@Override
	public String toString() {
		return "Pixel " + "," + r + "," +  g  + "," + b + ";";
	}

	@Override
	public int compare(Pixel o1, Pixel o2) {

		if(o1.getAvg() > o2.getAvg())
			return 1;
		else if (o1.getAvg() < o2.getAvg())
			return -1;
		else
			return 0;
	}
	
	
}
