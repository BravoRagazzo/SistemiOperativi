package test;

import java.util.ArrayList;

public class Row implements Runnable {

	private ArrayList<Pixel> pixel;
	private int max;
	private boolean done = false;
	private int lastPos;
	
	public Row() {
		pixel = new ArrayList<Pixel>();
	}
	
	@Override
	
	public void run() {
		

		int count = 0;
		int idx = 0;
		Pixel p = pixel.get(0);
		for(int i = 1; i < pixel.size(); i++) {
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



	@Override
	public String toString() {

		String s = "Riga: ";
		for (Pixel pixel2 : pixel) {
			
			s+=pixel2.toString();
		}
		
	
		return s+"\n";
		
	}


}
