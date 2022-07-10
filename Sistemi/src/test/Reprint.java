package test;

import java.util.ArrayList;

public class Reprint implements Runnable{

	private int max;
	private int lastPos;
	private ArrayList<Pixel> pixel;
	
	public Reprint(int max, int lastPos, ArrayList<Pixel> pixel) {
		this.max = max;
		this.lastPos = lastPos;
		this.pixel = pixel;
	}
	
	

	@Override
	public void run() {

//		System.out.println(lastPos + "," + max);
		
		for(int i = lastPos; i > (lastPos - max); i--) {
			pixel.get(i).setA(255);
			pixel.get(i).setR(255);
			pixel.get(i).setG(0);
			pixel.get(i).setB(200);
			
//			row.getPixel().get(i).setA(255);
//			row.getPixel().get(i).setR(255);
//			row.getPixel().get(i).setG(0);
//			row.getPixel().get(i).setB(200);
		}
		
		
	}

}
