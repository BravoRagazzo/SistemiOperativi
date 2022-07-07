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
		
		int cnt = 0;
		int max = 0;
		int idx = 0;
		int j = 0;
		int i = 0;
		
		for(i = 0; i < pixel.size()-1; i++) {
			j = i+1;
				if(j == pixel.size() - 1) {
					max = cnt;
					idx = j;
					break;
				} else if((pixel.get(i).compareTo(pixel.get(j)) == 1)) {
					cnt++;
				} else if(cnt>max) {
						max = cnt;
						i = j;
						idx = j;
						cnt = 0;
				}
		}
		
		this.max = max;
		done = true;
		lastPos = idx;
		
		
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
