package spacepirates.GameBoard;

import inputhandler.KeyboardInput;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import spacepirates.Ships.EnemyShip;
import spacepirates.Ships.MyShip;
import spacepirates.Ships.Ship;
import spacepirates.Weapons.Bullet;

public class BoardDraw extends BasicGame{
	private Ship myShip;
	private List<Ship> enemy;
	private Bullet[] bullets;
	private Image background;
	private String playerName;
	private Input input;
	public BoardDraw(String title, String playerName) {
		super(title);
		this.playerName = playerName; 
	}
	private void spawnEnemy(int n)
	{
		for(int i = 0; i<n;i++)
			try {
				enemy.add(new EnemyShip());
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void render(GameContainer game, Graphics g) throws SlickException {
		g.drawImage(background,background.getTextureWidth(),
					background.getTextureHeight());
		g.drawImage(myShip.getImage(), myShip.getX(),myShip.getY());
		if(!enemy.isEmpty())
		{
			for(int i=0;i<enemy.size();i++)
				g.drawImage(enemy.get(i).getImage(),
						enemy.get(i).getX(), enemy.get(i).getY());
		}
		
	}

	@Override
	public void init(GameContainer panel) throws SlickException {
		myShip = new MyShip();
		enemy = new LinkedList<Ship>();
		background = new Image("image/back.PNG");
		input = panel.getInput();
		spawnEnemy(3);
		
	}

	@Override
	public void update(GameContainer game, int n) throws SlickException {
		if(input.isKeyDown(Input.KEY_LEFT))
			myShip.setX(myShip.getX()-myShip.getMoveX());
		if(input.isKeyDown(Input.KEY_RIGHT))
			myShip.setX(myShip.getX()+myShip.getMoveX());
		
		
	}
	 
}
