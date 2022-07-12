package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import model.Main;
import model.Pixel;
import model.Reprint;
import model.Row;

import java.awt.*;

import view.MainView;

public class MVCController {

	private MainView v; // view
	private Main m; // model
	private String s;

	public static void main(String[] args) {
		Main m = new Main();
		MainView v = new MainView();
		MVCController c = new MVCController(m, v);
	}


	public MVCController(Main m, MainView v) {

		this.v = v;
		this.m = m;
		this.s = "";
		initActionListeners();
	}


	public void initActionListeners() {

		ActionListener openListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if(v.getPic()!=null) {
						v.getPic().setIcon(null);
						v.getSource().removeAll();
						v.getSequence().removeAll();
						v.getSorted().removeAll();
						v.getSource().repaint();
						v.getC1().show(v.getCardPane(),"0");
						v.getMain().setVisible(true);					
					}
					
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					jfc.setDialogTitle("Select a jpg image");
					jfc.setAcceptAllFileFilterUsed(false);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg Images", "jpg");
					jfc.addChoosableFileFilter(filter);
					int returnValue = jfc.showOpenDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						m.setPath(jfc.getSelectedFile().getPath()); 
						s = m.getPath();
					} else s = "";


					//da mettere nel controller dopo la lettura del path
					BufferedImage myPicture = ImageIO.read(new File(s));

					myPicture = resize(myPicture, v);
					
					v.setPic(new JLabel(new ImageIcon(myPicture)));
					v.getSource().add(v.getPic());
					v.getMain().setVisible(true);


				} catch (Exception e2) {
					e2.printStackTrace();				}

			}
		};


		v.getOpen().addActionListener(openListener);


		ActionListener startListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					System.out.println("Inizio Elaborazione");

					// Upload the image
					BufferedImage image = ImageIO.read(new File(s));
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
					Pixel p = new Pixel(0,0,0);



					for (Row row : rows) {
						thread[i].join();
						maxArray[i] = row.getMax();
						lastPosArray[i] = row.getLastPos();
						p.setR(row.getAvgPixel().getR() + p.getR());
						p.setG(row.getAvgPixel().getG() + p.getG());
						p.setB(row.getAvgPixel().getB() + p.getB());
						//	            	System.out.println(maxArray[i]);
						i++;
					}


					p.setR(p.getR()/height);
					p.setG(p.getG()/height);
					p.setB(p.getB()/height);

					if((Math.abs(p.getR()-p.getG()) < 30) && (Math.abs(p.getR()-p.getB()) < 30) && (Math.abs(p.getG()-p.getB()) < 30)) {
						System.out.println("TINTA NEUTRA");
						v.getSource().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSorted().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSequence().setBackground(new Color(p.getR(),p.getG(),p.getB()));
					} else if(p.getR() > p.getG() && p.getR() > p.getB()) {
						System.out.println("TINTA ROSSA");
						v.getSource().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSorted().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSequence().setBackground(new Color(p.getR(),p.getG(),p.getB()));
					} else if(p.getG() > p.getR() && p.getG() > p.getB()) {
						System.out.println("TINTA VERDE");
						v.getSource().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSorted().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSequence().setBackground(new Color(p.getR(),p.getG(),p.getB()));
					} else if(p.getB() > p.getR() && p.getB() > p.getG()) {
						System.out.println("TINTA BLU");
						v.getSource().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSorted().setBackground(new Color(p.getR(),p.getG(),p.getB()));
						v.getSequence().setBackground(new Color(p.getR(),p.getG(),p.getB()));
					}

					System.out.println("Terminazione Thread");

					Reprinter(height, maxArray, lastPosArray, rows);
					int [][] mat =	new int[2][height * width];
					mat = RGBtoBinary(rows, height, width);
					int []col1 = new int[height*width];
					int []col2 = new int[height*width];

					for (int j = 0; j < height*width; j++) {
						col1[j] = mat[j][0];
						col2[j] = mat[j][1];
					}


					textToImage("src/output.png", width, height, col1);
					textToImage("src/output2.png", width, height, col2);


					System.out.println("Elaborazione Terminata");


					BufferedImage myPicture2 = ImageIO.read(new File("src/output.png"));
					BufferedImage myPicture3 = ImageIO.read(new File("src/output2.png"));

					//SET LARGHEZZA, ADATTO ALTEZZA
					//					myPicture2 = resize(myPicture2, (int)v.getImg().getSequence().getSize().getWidth(), (int)(myPicture2.getHeight()*v.getImg().getSequence().getSize().getWidth())/myPicture2.getWidth());
					//					myPicture3 = resize(myPicture3, (int)v.getImg().getSorted().getSize().getWidth(), (int)(myPicture3.getHeight()*v.getImg().getSorted().getSize().getWidth())/myPicture3.getWidth());
					//SET ALTEZZA, ADATTO LARGHEZZA
					//					myPicture2 = resize(myPicture2, (int)(myPicture2.getWidth()*v.getImg().getSorted().getSize().getHeight())/myPicture2.getHeight(), (int)v.getImg().getSorted().getSize().getHeight());
					//					myPicture3 = resize(myPicture3, (int)(myPicture3.getWidth()*v.getImg().getSequence().getSize().getHeight())/myPicture3.getHeight(), (int)v.getImg().getSequence().getSize().getHeight());
					//SET NORMALE
					myPicture2 = resize(myPicture2, v);
					myPicture3 = resize(myPicture3, v);
					
					
					v.setPic(new JLabel(new ImageIcon(myPicture2)));
					v.getSequence().add(v.getPic());
					v.getMain().setVisible(true);
					v.setPic(new JLabel(new ImageIcon(myPicture3)));
					v.getSorted().add(v.getPic());
					v.getMain().setVisible(true);




				} catch (Exception exc) {
					exc.printStackTrace();

				}
			}

		};

		v.getStart().addActionListener(startListener);


		ActionListener sxListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
						if(v.getI() == 0) {
							v.getC1().show(v.getCardPane(),"2");
							v.setI(2);
						} else if(v.getI() == 1) {
							v.getC1().show(v.getCardPane(),"0");
							v.setI(0);
						} else if(v.getI() == 2) {
							v.getC1().show(v.getCardPane(),"1");
							v.setI(1);
						}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		};


		v.getSx().addActionListener(sxListener);
		
		
		ActionListener dxListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(v.getI() == 0) {
						v.getC1().show(v.getCardPane(),"1");
						v.setI(1);
					} else if(v.getI() == 1) {
						v.getC1().show(v.getCardPane(),"2");
						v.setI(2);
					} else if(v.getI() == 2) {
						v.getC1().show(v.getCardPane(),"0");
						v.setI(0);
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		};
		
		
		v.getDx().addActionListener(dxListener);

	}


	
	private BufferedImage resize(BufferedImage img, MainView v) {
	
	if(img.getHeight()<v.getSource().getSize().getHeight() && img.getWidth()<v.getSource().getSize().getWidth()) {
		return resize(img, (getMin((int)v.getSource().getSize().getWidth(),(int)v.getSource().getSize().getHeight())*img.getWidth())/img.getHeight(), getMin((int)v.getSource().getSize().getWidth(),(int)v.getSource().getSize().getHeight()));
	} else if(img.getHeight()>v.getSource().getSize().getHeight() && img.getWidth()>v.getSource().getSize().getWidth()) {
		return resize(img, (getMin((int)v.getSource().getSize().getWidth(),(int)v.getSource().getSize().getHeight())*img.getWidth())/img.getHeight(), getMin((int)v.getSource().getSize().getWidth(),(int)v.getSource().getSize().getHeight()));
	} else if(img.getHeight()>v.getSource().getSize().getHeight() && img.getWidth()<v.getSource().getSize().getWidth()) {
		return resize(img, (int)v.getSource().getSize().getHeight()*img.getWidth()/img.getHeight(), (int)v.getSource().getSize().getHeight());
	} else if(img.getHeight()<v.getSource().getSize().getHeight() && img.getWidth()>v.getSource().getSize().getWidth()) {
		return resize(img, (int)v.getSource().getSize().getWidth(), (int)v.getSource().getSize().getWidth()*img.getHeight()/img.getWidth());
	}
		
	return img;
	}
	
	
	public BufferedImage resize(BufferedImage img, int newW, int newH) { 
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}  


	private void writeTextFile(String path, int[] data, int width) throws IOException {
		FileWriter f = new FileWriter(path);

		// Write pixel info to file, comma separated
		for(int i = 0; i < data.length; i++) {
			String s = Integer.toString(data[i]);
			f.write(s + ", ");
			if ((i+1) % width == 0) f.write(System.lineSeparator());
		}
		f.close();
	}


	private  ArrayList<Row> integerToBinary(String path, String path2, int width) {
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
					//					String a = "";
					String r = "";
					String g = "";
					String b = "";

					for(int i=0;i<8;i++) {
						//						a += spacket[i];
						r += spacket[8 + i];
						g += spacket[16 + i];
						b += spacket[24 + i];

					}

					//					int aint = Integer.parseInt(a,2);
					int rint = Integer.parseInt(r,2);
					int gint = Integer.parseInt(g,2);
					int bint = Integer.parseInt(b,2);

					row.getPixel().add(new Pixel(rint,gint,bint));
					row.getPixel2().add(new Pixel(rint,gint,bint));

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


	private void Reprinter(int n, int maxArray[], int lastPosArray[], ArrayList<Row> rows) {

		Thread[] thread = new Thread[n];


		int i=0;
		for (Row row : rows) {
			thread[i] = new Thread(new Reprint(maxArray[i], lastPosArray[i], row.getPixel()));
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


	private int[][] RGBtoBinary(ArrayList<Row> rows, int height, int width) {


		int data[][] = new int[height * width][2];
		int i = 0;
		int j = 0;

		for (Row row : rows) {

			for (Pixel pixels : row.getPixel()) {

				int binaryNum = 0;

				//				binaryNum += (pixels.getA()&0x000000FF);
				//				binaryNum = binaryNum << 8; 
				binaryNum += (pixels.getR()&0x000000FF); 
				binaryNum = binaryNum << 8; 
				binaryNum += (pixels.getG()&0x000000FF);
				binaryNum = binaryNum << 8;
				binaryNum += (pixels.getB()&0x000000FF); 		
				binaryNum = binaryNum | 0xFF000000;	
				data[i][0] = binaryNum;
				i++;
			}


			for (Pixel pixels : row.getPixel2()) {

				int binaryNum2 = 0;

				//				binaryNum2 += (pixels.getA()&0x000000FF);
				//				binaryNum2 = binaryNum2 << 8;
				binaryNum2 += (pixels.getR()&0x000000FF); 
				binaryNum2 = binaryNum2 << 8;
				binaryNum2 += (pixels.getG()&0x000000FF);
				binaryNum2 = binaryNum2 << 8;
				binaryNum2 += (pixels.getB()&0x000000FF); 		
				binaryNum2 = binaryNum2 | 0xFF000000;	
				data[j][1] = binaryNum2;
				j++;
			}
		}

		return data;
	}


	private void textToImage(String path, int width, int height, int[] data) throws IOException {
		MemoryImageSource mis = new MemoryImageSource(width, height, data, 0, width);
		Image im = Toolkit.getDefaultToolkit().createImage(mis);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bufferedImage.getGraphics().drawImage(im, 0, 0, null);
		ImageIO.write(bufferedImage, "png", new File(path));
		System.out.println("Done! Check the result");
	}

	private int getMin(int w, int h) {

		if(w > h)
			return h;
		else
			return w;
		
	}
}
