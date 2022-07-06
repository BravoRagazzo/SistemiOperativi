package test;

import java.util.ArrayList;

public class Row implements Runnable {

	private ArrayList<Pixel> pixel;
	private int max = 1;
	
	public Row() {
		pixel = new ArrayList<Pixel>();
	}
	
	@Override
	public void run() {
		
		int cnt = 1;
		
		for (Pixel pixel2 : pixel) {
			for (Pixel pixel3 : pixel) {
				
				if(pixel3.compareTo(pixel2) == 1) {
					cnt++;
				}else {
					if(cnt > max) {
						max = cnt;
					}
					cnt = 1;
				}
			}
		}
		
	}
	
	
	public ArrayList<Pixel> getPixel() {
		return pixel;
	}

	public void setPixel(ArrayList<Pixel> pixel) {
		this.pixel = pixel;
	}


	public int getMax() {
		return max;
	}


	public void setMax(int max) {
		this.max = max;
	}




}
