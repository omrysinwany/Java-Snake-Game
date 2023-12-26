package run;

import javax.swing.JFrame;

public class GFrame extends JFrame{
	
	GFrame(){
		this.add(new GPanel());
		this.setTitle("My Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
	}

}
