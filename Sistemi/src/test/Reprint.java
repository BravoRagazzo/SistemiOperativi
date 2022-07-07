package test;


public class Reprint implements Runnable{

	private int max;
	private int lastPos;
	private Row row;
	
	public Reprint(int max, int lastPos, Row row) {
		this.max = max;
		this.lastPos = lastPos;
		this.row = row;
	}
	
	

	@Override
	public void run() {

		System.out.println(lastPos + "," + max);
		
		for(int i = lastPos; i > (lastPos - max); i--) {
			row.getPixel().get(i).setR(255);
			row.getPixel().get(i).setG(0);
			row.getPixel().get(i).setB(200);
		}
		
		
	}

}
