/**
 * Version 0.1
 */

package spacepirates.Ships;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.newdawn.slick.Image;

public abstract class Ship{
	private Image ship;
	public Ship()
	{
		ship=null;
	}
	public Ship(Image img)
	{
		ship = img;
	}
	public void setImage(Image img){ship=img;}
	public Image getImage(){return ship;}
}
