/**
 * Version 0.1
 */

package main;

import javax.swing.ImageIcon;

public class Bullet {
	private static ImageIcon bullet = new ImageIcon("image/Bullet/bullet_small.PNG");
	private int ammoy,ammoMoveY,ammox;
	private boolean bulletInMotion=false;
	private boolean friendlyFire;
	public Bullet()
	{
		ammoy=0;
		ammox=0;
		ammoMoveY=3;
		bulletInMotion=false;
	}
	public void setAmmoY(int y){ammoy=y;}
	public void setAmmoMoveY(int y){ammoMoveY=y;}
	public void setAmmoX(int x){ammox=x;}
	public void setFireMotion(boolean flag){bulletInMotion=flag;}
	public void setFriendlyFire(boolean flag){friendlyFire=flag;}
	
	public int getAmmoY(){return ammoy;}
	public int getAmmoX(){return ammox;}
	public int getAmmoMoveY(){return ammoMoveY;}
	public boolean getFireMotion(){return bulletInMotion;}
	public boolean getFriendlyFire(){return friendlyFire;}
	public ImageIcon getBullet(){return bullet;}
	public void resetAmmo(int y){ammoy=y;}
	
	public void fireBullet()
	{
		setAmmoY(getAmmoY()-ammoMoveY);
	}
}
