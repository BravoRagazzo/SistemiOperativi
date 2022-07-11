package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainView{

	
	
	/**
	 * 
	 */
	JFrame main;
	ImgView img;
	JPanel source;
	JPanel buttonPane;
	JPanel container;
	JButton open;
	JButton start;
	JLabel pic;
	int w,h;
	
	public MainView() {
		
		try {
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			w = screenSize.width;
			h = screenSize.height;
			main = new JFrame();
			main.setSize(w, h);
			main.setExtendedState(JFrame.MAXIMIZED_BOTH);
			main.setLayout(new BorderLayout());
			source = new JPanel(new BorderLayout());
			img = new ImgView();
			main.setResizable(true);
			container = new JPanel();
			container.setLayout(new GridLayout(2,1));

			
			// original image pane details
			JLabel original = new JLabel("Original Image");
			original.setHorizontalAlignment(JLabel.CENTER);
			original.setFont(new java.awt.Font("Synchro LET", 1, 24));
			source.add(original, BorderLayout.NORTH);
			
			
//			// da mettere nel controller dopo la fine delle elaborazioni
//			BufferedImage myPicture2 = ImageIO.read(new File("src/output.jpg"));
//			myPicture2 = resize(myPicture2, w/3, h/3);
//			pic = new JLabel(new ImageIcon(myPicture2));
//			img.getSequence().add(pic);
//			
//			//da mettere nel controller dopo la fine delle elaborazioni
//			BufferedImage myPicture3 = ImageIO.read(new File("src/output2.jpg"));
//			myPicture3 = resize(myPicture3, w/3, h/3);
//			pic = new JLabel(new ImageIcon(myPicture3));
//			img.getSorted().add(pic);
//			
			
			
			
			container.add(source);
			container.add(img);
			main.add(container, BorderLayout.CENTER);
			
			buttonPane = new JPanel();
			open = new JButton("Open");
			open.setFont(new java.awt.Font("Synchro LET", 1, 24));
			start = new JButton("Start");
			start.setFont(new java.awt.Font("Synchro LET", 1, 24));
			buttonPane.add(open);
			buttonPane.add(start);
			main.add(buttonPane, BorderLayout.NORTH);
			
			
			main.setVisible(true);
			main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
	}

	

	public JFrame getMain() {
		return main;
	}




	public void setMain(JFrame main) {
		this.main = main;
	}




	public ImgView getImg() {
		return img;
	}




	public void setImg(ImgView img) {
		this.img = img;
	}




	public JPanel getSource() {
		return source;
	}




	public void setSource(JPanel source) {
		this.source = source;
	}




	public JPanel getButtonPane() {
		return buttonPane;
	}




	public void setButtonPane(JPanel buttonPane) {
		this.buttonPane = buttonPane;
	}




	public JPanel getContainer() {
		return container;
	}




	public void setContainer(JPanel container) {
		this.container = container;
	}




	public JButton getOpen() {
		return open;
	}




	public void setOpen(JButton open) {
		this.open = open;
	}




	public JButton getStart() {
		return start;
	}




	public void setStart(JButton start) {
		this.start = start;
	}




	public int getW() {
		return w;
	}




	public int getH() {
		return h;
	}




	public JLabel getPic() {
		return pic;
	}




	public void setPic(JLabel pic) {
		this.pic = pic;
	}
	
	
	
	
	
	
}
