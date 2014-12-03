/**
 * Version 0.1
 */

package main;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class Ship{
	private ImageIcon ship;
	public Ship()
	{
		ship=null;
	}
	public Ship(ImageIcon img)
	{
		ship = img;
	}
	public void setImage(ImageIcon img){ship=img;}
	public ImageIcon getImage(){return ship;}
}
