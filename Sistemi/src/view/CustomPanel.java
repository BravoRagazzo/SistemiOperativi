package view;

import javax.swing.JPanel;

public class CustomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	
	public CustomPanel(int id) {
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
