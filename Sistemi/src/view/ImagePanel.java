package view;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class ImagePanel 
{
		 public ImagePanel() 
		 {
		  try 
		  {
		   JFrame f = new JFrame("Add an Image to a JPanel");
		   JPanel panel = new JPanel();
		   JPanel panel2 = new JPanel();
		   panel.setBounds(50, 50, 250, 250);
		
		   BufferedImage img = ImageIO.read(new File("src/test/moto.jpg"));
		   JLabel pic = new JLabel(new ImageIcon(img));
		   panel.add(pic);
//		   panel2.add(pic);
		
		   f.add(panel);
//		   f.add(panel2);
		   f.setSize(400, 400);
		   f.setLayout(null);
		   f.setVisible(true);
		  } 
		  catch (IOException e) {}
		 }
 public static void main(String args[]) 
 {
  ImagePanel i1 = new ImagePanel();

 }
}