/**
 * Version 0.1
 */

package spacepirates.Ships;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.newdawn.slick.Image;

public abstract class Ship{
	private Image ship;
	protected int x,y,moveX,moveY;
	public Ship()
	{
		ship=null;
	}
	public Ship(Image img)
	{
		ship = img;
	}
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y=y;}
	public void setMoveX(int moveX){this.moveX=moveX;}
	public void setMoveY(int moveY){this.moveY=moveY;}
	public void setImage(Image img){ship=img;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getMoveX(){return moveX;}
	public int getMoveY(){return moveY;}
	
	public Image getImage(){return ship;}
}
