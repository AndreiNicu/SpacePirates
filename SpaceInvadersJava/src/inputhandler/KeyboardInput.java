package inputhandler;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

import spacepirates.Ships.MyShip;

public class KeyboardInput implements InputProviderListener {
	private Command left;
	private Command right;
	private Command fire;
	private MyShip sh;
	private int width;
	private boolean isPlayerFireReady;
	
	public KeyboardInput(MyShip sh, int width, boolean flag)
	{
		this.sh = sh; this.width = width;
		this.isPlayerFireReady = flag;
	}
	@Override
	public void controlPressed(Command e) {
		System.out.println("Hello");
		if (e.equals(left)){
			System.out.println("Command");
			if(sh.getX()>=0)
				sh.setX(sh.getX()-2);}
		else if(e.equals(right))
		{
			if(sh.getX()<=(width-sh.getWitdh()))
					sh.setX(sh.getX()+2);
		}
		else if(isPlayerFireReady&&e.equals(fire))
		{
			isPlayerFireReady = false;
		}
	}

	@Override
	public void controlReleased(Command e) {
		System.out.println("Hello Released");

	}
	public Command getMoveLeft(){return left;}
	public Command getMoveRight(){return right;}
	public Command getFire(){return fire;}
	public boolean isPlayerReadyToFire(){return isPlayerFireReady;}
	public void setIsPlayerReadyToFire(boolean flag)
	{
		this.isPlayerFireReady = flag;
	}
}
