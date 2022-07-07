package test;

import java.util.ArrayList;

public class Row implements Runnable {

	private ArrayList<Pixel> pixel;
	private int max;
	private boolean done = false;
	
	
	public Row() {
		pixel = new ArrayList<Pixel>();
	}
	
	@Override
	
	public void run() {
		
		int max = 1;
		int cnt = 0;
		
		for (Pixel pixel2 : pixel) {
			for (Pixel pixel3 : pixel) {
				
				if(pixel2.compareTo(pixel3) == 1) {
					cnt++;
				} else {
					if(cnt > max) {
						max = cnt;
					}
					cnt = 0;
				}
			}
			cnt = 0;
		}
		this.max = max;
		done = true;
		
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

	
	@Override
	public String toString() {

		String s = "Riga: ";
		for (Pixel pixel2 : pixel) {
			
			s+=pixel2.toString();
		}
		
	
		return s+"\n";
		
	}


}
