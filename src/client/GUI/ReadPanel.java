package client.GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ReadPanel extends JPanel {
	
	public ReadPanel(){
		
		// to know if textArea is in full size or not
		setBackground(Color.darkGray);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JTextArea textArea = new JTextArea ("Now you see me ");
		textArea.setEditable (false);
		
		add(textArea);
		
		this.setVisible(true);
		
	}

}

class SeeReadPanel {
	public static void main(String[] args){
		new ReadPanel();
	}
}