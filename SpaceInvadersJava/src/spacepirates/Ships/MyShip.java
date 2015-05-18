/**
 * Version 0.1
 */

package spacepirates.Ships;

import javax.swing.ImageIcon;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import spacepirates.GameBoard.GamePanel;

public class MyShip extends Ship {
	
	private int x,y,moveX,life;
	private int ImageWidth; 
	public MyShip() throws SlickException
	{
		super(new Image("image/MyShip/ship.PNG"));
		ImageWidth = getImage().getWidth();
		x=250;y=((GamePanel.getBackgroundDimension().height-100)-getImage().getHeight());moveX=0;
		life=3;
	}
	public void setX(int x){this.x=x;}
	public void setMoveX(int x){moveX=x;}
	public void setLife(int i){life=i;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getWitdh(){return ImageWidth;}
	public int getMoveX(){return moveX;}
	public int getLife(){return life;}
	public boolean isColision(int xB, int yB)
	{
		if((xB+11)>=(x)&&(xB+11)<=(x+getImage().getWidth())&&(yB-9)>=(y-10))
			return true;
		return false;
	}
}
