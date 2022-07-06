package test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Processing the image...");

            // Upload the image
            BufferedImage image = ImageIO.read(new File("src/test/test.JPG"));
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
            int[] maxArray = new int[height];
            for (Row row : rows) {
				thread[i].join();
            	maxArray[i] = row.getMax();
				i++;
			}
            
            System.out.println(maxArray);
            
                       
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
            if (i % width == 0) f.write(System.lineSeparator());
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
			line = br.readLine();
			
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
						g += spacket[8 + 2*i];
						b += spacket[8 + 3*i];
					}
					
					int rint = Integer.parseInt(r,2);
					int gint = Integer.parseInt(g,2);
					int bint = Integer.parseInt(b,2);
					
					row.getPixel().add(new Pixel(rint,gint,bint));
					
					j++;
					fw.write(rint + "," + gint + "," + bint + ";");
					if (j % width == 0) fw.write(System.lineSeparator());
				
				}
				
				rows.add(row);
				
			}
			
			return rows;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    	
    }
    
    private static int countSubstrings(String path) {
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			String[] entries;
			int cnt = 0;
			int max = 0;
			
			while(line!=null) {
			
				entries = line.split(";");
				line = br.readLine();
				
				for (int i=0;i<entries.length;i++) {
					for(int j=i+1;j<entries.length;j++) {
						if(entries[i].equals(entries[j])) {
							cnt++;
							if(max < cnt) {
								max = cnt;
							}
						}
					}
				}
				
			}
			
			return  max;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
    }
}