package test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.util.ArrayList;

public class Main {

	int maxArray[];
	int lastPosArray[];
	
    public static void main(String[] args) {
        try {
            System.out.println("Processing the image...");

            // Upload the image
            BufferedImage image = ImageIO.read(new File("src/test/nike.png"));
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = new int[width * height];
            
            
            // Retrieve pixel info and store in 'pixels' variable
            PixelGrabber pgb = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
            pgb.grabPixels();

            // Write pixels to CSV
            writeTextFile("src/raw.txt", pixels, width);

            //Converting integers to binary values
            ArrayList<Row> rows = integerToBinary("src/raw.txt", "src/rgb.txt", width);
            Thread[] thread = new Thread[height];
            
            int i=0;
            for (Row row : rows) {
				thread[i] = new Thread(row);
				i++;
            }
            
            for (Thread thread2 : thread) {
				thread2.start();
			}
            
            i = 0;
            int maxArray[] = new int[height];
            int lastPosArray[] = new int[height];
            for (Row row : rows) {
            	thread[i].join();
            	maxArray[i] = row.getMax();
            	
            	lastPosArray[i] = row.getLastPos();
//            	System.out.println(maxArray[i]);
				i++;
			}
            System.out.println("FINE -------------- THREAD");
            
            
            
    		Reprinter(height, maxArray, lastPosArray, rows);
            textToImage("src/output.jpg", width, height, RGBtoBinary(rows, height, width));

            System.out.println("FINE");
            
            
            
            
            
            
            
            
            
            
            
                       
        } catch (Exception exc) {
        	exc.printStackTrace();
        }
    }

    private static void writeTextFile(String path, int[] data, int width) throws IOException {
        FileWriter f = new FileWriter(path);
        
        // Write pixel info to file, comma separated
        for(int i = 0; i < data.length; i++) {
            String s = Integer.toString(data[i]);
            f.write(s + ", ");
            if ((i+1) % width == 0) f.write(System.lineSeparator());
        }
        f.close();
    }

    private static ArrayList<Row> integerToBinary(String path, String path2, int width) {
    	try {
			FileReader fr = new FileReader(path);
			FileWriter fw = new FileWriter(path2);
			BufferedReader br = new BufferedReader(fr);
			String[] entries;
			ArrayList<Row> rows = new ArrayList<Row>();
			Row row = new Row();
			int j = 0;
			
			String line = br.readLine();
			
			while(line!=null) {
				entries = line.split(", ");
				line = br.readLine();
			
				for (String string : entries) {
					String component  = Integer.toBinaryString(Integer.parseInt(string));
					char[] spacket = component.toCharArray();
					String r = "";
					String g = "";
					String b = "";
					
					
					for(int i=0;i<8;i++) {
						r += spacket[8 + i];
						g += spacket[16 + i];
						b += spacket[24 + i];
						
					}
					
					int rint = Integer.parseInt(r,2);
					int gint = Integer.parseInt(g,2);
					int bint = Integer.parseInt(b,2);
					
					row.getPixel().add(new Pixel(rint,gint,bint));
					
//					System.out.println(new Pixel(rint,gint,bint));
					
					j++;
					fw.write(rint + "," + gint + "," + bint + ";");
					if (j % width == 0) fw.write(System.lineSeparator());
				
				}
				
				
				rows.add(row);
				//System.out.println(row);
				row = new Row();
			}
			
			br.close();
			fr.close();
			fw.close();
			
			return rows;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    	
    }
    
    
    
    private static void Reprinter(int n, int maxArray[], int lastPosArray[], ArrayList<Row> rows) {
    	
    	Thread[] thread = new Thread[n];
    	
    
        int i=0;
        for (Row row : rows) {
			thread[i] = new Thread(new Reprint(maxArray[i], lastPosArray[i], row));
			i++;
        }
        
        for (Thread thread2 : thread) {
			thread2.start();
    	
        }
        
        for (Thread thread2 : thread) {
			try {
				thread2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        
        
        
        
 }
    
    
    
    private static int[] RGBtoBinary(ArrayList<Row> rows, int height, int width) {
    	
    
    	int data[] = new int[height * width];
    	int i = 0;
    	
    	for (Row row : rows) {
			
    		
    		for (Pixel pixels : row.getPixel()) {
				
    			int binaryNum = 0;
    	    	binaryNum+= (pixels.getR()&0x000000FF); 
       	    	binaryNum = binaryNum << 8;
    			binaryNum += (pixels.getG()&0x000000FF);
    			binaryNum = binaryNum << 8;
    			binaryNum += (pixels.getB()&0x000000FF); 		
    			binaryNum = binaryNum|0XFF000000;
				data[i] = binaryNum;
				i++;
			}
		}
    	
    	return data;
    }
    
    
    
    
    
    private static void textToImage(String path, int width, int height, int[] data) throws IOException {
        MemoryImageSource mis = new MemoryImageSource(width, height, data, 0, width);
        Image im = Toolkit.getDefaultToolkit().createImage(mis);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(im, 0, 0, null);
        ImageIO.write(bufferedImage, "jpg", new File(path));
        System.out.println("Done! Check the result");
    }
}