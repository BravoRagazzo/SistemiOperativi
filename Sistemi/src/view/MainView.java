package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.*;

public class MainView{

	
	
	/**
	 * 
	 */
	JFrame main;
	JPanel source;
	JPanel sequence;
	JPanel sorted;
	JPanel buttonPane;
	JPanel cardPane;
	JButton open;
	JButton start;
	JButton sx;
	JButton dx;
	JLabel pic;
	CardLayout c1;
	int w,h,i=0;
	
	public MainView() {
		
		try {
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			w = screenSize.width;
			h = screenSize.height;
			main = new JFrame("Jpeg");
			main.setSize(w, h);
			main.setExtendedState(JFrame.MAXIMIZED_BOTH);
			main.setLayout(new BorderLayout());
			source = new JPanel();
			sequence = new JPanel();
			sorted = new JPanel();
			source.setBackground(Color.black);
			sequence.setBackground(Color.black);
			sorted.setBackground(Color.black);
			main.setResizable(true);
			cardPane = new JPanel();
			c1 = new CardLayout();
			cardPane.setLayout(c1);			
			
			cardPane.add(source,"0");
			cardPane.add(sequence,"1");
			cardPane.add(sorted,"2");
			main.add(cardPane);
			c1.show(cardPane,"0");
			
			buttonPane = new JPanel();
			buttonPane.setBackground(Color.black);
			open = new JButton("Open");
			open.setFont(new java.awt.Font("Synchro LET", 1, 32));
			start = new JButton("Start");
			start.setFont(new java.awt.Font("Synchro LET", 1, 32));
			sx = new JButton("< Prev");
			start.setFont(new java.awt.Font("Synchro LET", 1, 32));
			dx = new JButton("Next >");
			start.setFont(new java.awt.Font("Synchro LET", 1, 32));
			buttonPane.add(sx);
			buttonPane.add(open);
			buttonPane.add(start);
			buttonPane.add(dx);
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

	public JPanel getSource() {
		return source;
	}

	public void setSource(CustomPanel source) {
		this.source = source;
	}

	public JPanel getButtonPane() {
		return buttonPane;
	}

	public void setButtonPane(JPanel buttonPane) {
		this.buttonPane = buttonPane;
	}

	public JPanel getCardPane() {
		return cardPane;
	}

	public void setCardPane(JPanel cardPane) {
		this.cardPane = cardPane;
	}

	public JPanel getSequence() {
		return sequence;
	}

	public void setSequence(JPanel sequence) {
		this.sequence = sequence;
	}

	public JPanel getSorted() {
		return sorted;
	}

	public void setSorted(JPanel sorted) {
		this.sorted = sorted;
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
	
	public JButton getSx() {
		return sx;
	}

	public void setSx(JButton sx) {
		this.sx = sx;
	}
	
	public JButton getDx() {
		return dx;
	}

	public void setDx(JButton dx) {
		this.dx = dx;
	}

	public CardLayout getC1() {
		return c1;
	}

	public void setC1(CardLayout c1) {
		this.c1 = c1;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}	

	
	
}


