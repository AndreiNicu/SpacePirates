/**
 * Version 0.1
 */

package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import spacepirates.GameBoard.BoardDraw;

public class GameMain {

	public static void main(String[] args) {
		//InDialog intro = new InDialog();
		
		String name ="Andrei";
		System.out.println(name);
		try{
			AppGameContainer n;
			n = new AppGameContainer(new BoardDraw("Simple",name));
			n.setDisplayMode(1024, 720, false);
			n.start();
		}catch(SlickException ex)
		{
			ex.printStackTrace();
		}
	}

}
