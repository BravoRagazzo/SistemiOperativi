package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImgView extends JPanel {

	
	JPanel sorted;
	JPanel sequence;
	
	public ImgView() {

		sorted = new JPanel(new BorderLayout());
		JLabel so = new JLabel("Sorted Image");
		so.setHorizontalAlignment(JLabel.CENTER);
		so.setFont(new java.awt.Font("Synchro LET", 1, 24));
		sorted.add(so, BorderLayout.NORTH);
		sequence = new JPanel(new BorderLayout());
		JLabel se = new JLabel("Sequence");
		se.setHorizontalAlignment(JLabel.CENTER);
		se.setFont(new java.awt.Font("Synchro LET", 1, 24));
		sequence.add(se, BorderLayout.NORTH);
		this.setLayout(new GridLayout(1,2));
		this.add(sorted);
		this.add(sequence);
//		sorted.setBackground(Color.black);
//		sequence.setBackground(Color.red);
	}

	public JPanel getSorted() {
		return sorted;
	}

	public void setSorted(JPanel sorted) {
		this.sorted = sorted;
	}

	public JPanel getSequence() {
		return sequence;
	}

	public void setSequence(JPanel sequence) {
		this.sequence = sequence;
	}
	
	
	
	
}
