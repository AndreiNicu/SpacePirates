/**
 * Version 0.1
 */

package main;

import javax.swing.JFrame;

public class GameMain {

	public static void main(String[] args) {
		//InDialog intro = new InDialog();
		
		String name ="Andrei";
		
		JFrame frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GamePanel(name));
		frame.setVisible(true);
		frame.pack();
	}

}
