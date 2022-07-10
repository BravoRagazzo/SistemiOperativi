package test;

import java.util.ArrayList;
import java.util.Collections;

public class Row implements Runnable {

	private ArrayList<Pixel> pixel;
	private ArrayList<Pixel> pixel2;
	private int max;
	private boolean done = false;
	private int lastPos;
	private Pixel avgPixel;
	
	public Row() {
		pixel = new ArrayList<Pixel>();
		pixel2 = new ArrayList<Pixel>();

	}
	
	@Override
	
	public void run() {
		
//Sorting pixels
		Collections.sort(pixel2, new Pixel(0, 0, 0));
		

		
//FIND MAXIMUM EQUAL PIXELS SEQUENCE
		int count = 0;
		int idx = 0;
		Pixel p = pixel.get(0);
		int[] rgbAv = new int[3];
		
		for(int i = 1; i < pixel.size(); i++) {
			
			rgbAv[0] += pixel.get(i).getR();
			rgbAv[1] += pixel.get(i).getG();
			rgbAv[2] += pixel.get(i).getB();
			
			if(i == pixel.size() - 1) {
				if(count > max) {
					max = count;
					idx = i;					
				}
			} else if(pixel.get(i).compareTo(p)==1) {
				count ++;
			} else {
				p = pixel.get(i);
				if(count > max) {
					max = count;
					idx = i-1;
				}
				count = 0;
			}
		}
		
		this.lastPos = idx;
		


		
		
		
//RGB AVERAGE CALCULUM
		
		for(int i = 0; i < 3; i++) {
			rgbAv[i] = rgbAv[i]/pixel.size();
		}
		
		avgPixel = new Pixel(rgbAv[0],rgbAv[1],rgbAv[2]);
		
		this.done = true;
		
	}
	
	
	public ArrayList<Pixel> getPixel() {
		return pixel;
	}

	public void setPixel(ArrayList<Pixel> pixel) {
		this.pixel = pixel;
	}


	public int getMax() {
		while(!done);
		return max;
	}


	public void setMax(int max) {
		this.max = max;
	}

	
	public int getLastPos() {
		while(!done);
		return lastPos;
	}

	
	public Pixel getAvgPixel() {
		while(!done);
		return avgPixel;
	}

	@Override
	public String toString() {

		String s = "Riga: ";
		for (Pixel pixel2 : pixel) {
			
			s+=pixel2.toString();
		}
		
	
		return s+"\n";
		
	}

	public ArrayList<Pixel> getPixel2() {
		return pixel2;
	}


}
