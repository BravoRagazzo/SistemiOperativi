package controller;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import test.Main;
import view.MainView;

public class MVCController {

	MainView v; // view
	Main m; // model
	
	
	public static void main(String[] args) {
		Main m = new Main();
		MainView v = new MainView();
		MVCController c = new MVCController(m, v);
	}
	
	
	public MVCController(Main m, MainView v) {
		
		this.v = v;
		this.m = m;
		initActionListeners();
	}
	
	
	public void initActionListeners() {
	
	ActionListener openListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				
			
			String path;
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a jpg image");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg Images", "jpg");
			jfc.addChoosableFileFilter(filter);
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				m.setPath(jfc.getSelectedFile().getPath()); 
				path = m.getPath();
			} else path = "";
			
			
		
			//da mettere nel controller dopo la lettura del path
			BufferedImage myPicture = ImageIO.read(new File(path));
			myPicture = resize(myPicture, v.getW()/3, v.getH()/3);
			v.setPic(new JLabel(new ImageIcon(myPicture)));
			v.getSource().add(v.getPic());
			v.getMain().repaint();
			
			
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	};
	
	
	v.getOpen().addActionListener(openListener);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
	
	public BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	
	
}
