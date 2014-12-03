/**
 * Version 0.1
 */

package main;

import javax.swing.ImageIcon;

public class MyShip extends Ship {
	
	private static ImageIcon ship=new ImageIcon("image/MyShip/ship.PNG");
	private int x,y,moveX,life;
	private final int ImageWidth=ship.getIconWidth(); 
	public MyShip()
	{
		super(ship);
		x=250;y=((GamePanel.getBackgroundDimension().height-100)-ship.getIconHeight());moveX=0;
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
		if((xB+11)>=(x)&&(xB+11)<=(x+ship.getIconWidth())&&(yB-9)>=(y-10))
			return true;
		return false;
	}
}
