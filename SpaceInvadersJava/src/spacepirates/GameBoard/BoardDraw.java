package spacepirates.GameBoard;

import inputhandler.KeyboardInput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

import spacepirates.Ships.EnemyShip;
import spacepirates.Ships.MyShip;
import spacepirates.Weapons.Bullet;

public class BoardDraw extends BasicGame{
	private MyShip sh;
	private Animation fps;
	private EnemyShip[] enemy;
	private Bullet[] bullet;
	private Timer bulletTime;
	private boolean GameOver;
	private boolean GameStart;
	private boolean fire;
	private boolean kLeft=false;
	private boolean kRight=false;
	private boolean kSpace=false;
	private boolean kCtrl=false;
	private boolean isPlayerFireReady=false;
	private static String LoseMessage="GAME OVER!";
	private static String Credits="Created by:";
	private static String Iz="Izabela Beatrice Gafitoiu";
	private static String Dn="Danut Niculae";
	private static String BeginMessege="";
	private int eindex=0;
	private int bindex=0;
	private int currentLevel,nextLevel,fireSpeed,threshold;
	private int tick=0;
	private long score=0; 
	private Image back;
	private InputProvider input;
	private Dimension backgroundSz;
	private String name;
	private KeyboardInput kbInputListener;
	public BoardDraw(String title, String name) {
		super(title);
		this.name = name;
	}
	
	
	//TODO: Please if possible move these into Handler class
	private void shift(){bindex-=1;}
	private void eraseEnemy(int index)
	{
		for(int i=index;i<eindex-1;i++)
			enemy[i]=enemy[i+1];
		eindex--;
	}
	private void resize()
	{
		Bullet [] tmp = new Bullet[2*bullet.length];
		System.arraycopy(bullet, 0, tmp, 0, bullet.length);
		bullet = tmp;
	}
	
	//TODO Overlapping issue still persists.
		private void overlapingEnemy(int i, EnemyShip e)
		{
			int k=0;
			while(k<i){
				//System.out.println("Spawn spacepirates.Ships"+ " "+enemy[i].getX()+" "+k);
				//System.out.println("Overlap spacepirates.Ships "+enemy[k].getX()+" "+k);
					if(e.checkIfOverlap(enemy[k])){
						enemy[eindex].setRandomPosX();
						e=enemy[eindex];
						k=-1;
					}
					k++;
			}
		}
		
		public void spawnShip(boolean flag){
			for(int i=0;i<3;i++){
				try {
					enemy[eindex]= new EnemyShip();
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				enemy[eindex].setFireLimit(fireSpeed);
				if(flag)enemy[eindex].setFireModeOn();
				if(i>=1) overlapingEnemy(i, enemy[eindex]);
				eindex++;
				if(eindex>=enemy.length) enemyResize();
			}
		}
		public void enemyResize()
		{
			EnemyShip [] tmp = new EnemyShip[2*enemy.length];
			System.arraycopy(enemy, 0, tmp, 0, enemy.length);
			enemy=tmp;
		}
		private void resetGame()
		{
			eindex=0;
			bindex=0;
			GameStart=true;
		}
		
		/*
		 * Please note that i have moved the spacepirates.Ships movement in here. Some tweaks are required
		 * to achieve good performance 
		 */
		private void shipMovement()
		{
			if(sh.getX()>=0)
				if(kLeft)
					sh.setX(sh.getX()-2);
			if(sh.getX()<=(backgroundSz.getWidth()-sh.getWitdh()))
				if(kRight)
					sh.setX(sh.getX()+2);
			if(kSpace&&isPlayerFireReady)
			{
				isPlayerFireReady=false;
				bindex++;
				fire=true;
				if(bindex>=bullet.length-1) 
					resize();
				bullet[bindex] = new Bullet();
				bullet[bindex].setFriendlyFire(false);
				bullet[bindex].setFireMotion(true);
				bullet[bindex].setAmmoX(sh.getX()+25);
				bullet[bindex].setAmmoY(sh.getY());
				if(!bulletTime.isRunning())
					bulletTime.start();
			}
		}
		private void setLevel()
		{	
			if(currentLevel==nextLevel)
			{
				nextLevel++;
				fireSpeed+=50;

			}
			//TODO: add an end level
		}
		private void fireEnemy()
		{
			for(int i=0;i<eindex;i++)
				if(enemy[i].getFireMode()) 
				{
					bindex++;
					fire=true;
					if(bindex>=bullet.length-1) 
						resize();
					bullet[bindex] = new Bullet();
					bullet[bindex].setFriendlyFire(true);
					bullet[bindex].setFireMotion(true);
					bullet[bindex].setAmmoX(enemy[i].getX()+25);
					bullet[bindex].setAmmoY(enemy[i].getY());
					bullet[bindex].setAmmoMoveY(-3);
					if(!bulletTime.isRunning())
						bulletTime.start();
					enemy[i].setFireModeOff();
				}
		}
		
		private void calculateScore()
		{
			score+=10;
			if(score>=threshold)
			{
				threshold*=2;
				currentLevel++;
				setLevel();
			}
		}
		//TODO Redo the bullet concept
		private void bulletErase(int k)
		{
			bullet[k].setFireMotion(false);
			boolean stop =true;
			for(int i=0;i<bindex;i++)
				if(bullet[i].getFireMotion())
					stop=false;
			if(stop)
				{shift();bulletTime.stop();}
			fire=false;
		}
	
	@Override
	public void render(GameContainer gamePanel, Graphics g) throws SlickException {
		g.drawImage(back, 0, 0) ;
		System.out.println("Drawing ships");
		if(!GameOver&&!GameStart){
			g.drawImage(sh.getImage(), sh.getX(), sh.getY());
			for(int i =0;i<eindex;i++)
				if(enemy[i].isAlive()){
					g.drawImage(enemy[i].getImage(), enemy[i].getX(), 
								enemy[i].getY());
				}

			if(fire){
				for(int i=0;i<=bindex;i++)
					if(bullet[i].getFireMotion())
						g.drawImage(bullet[i].getBullet(), bullet[i].getAmmoX(),
									bullet[i].getAmmoY());
						
				for(int i=0;i<=bindex;i++)
				{
					if(bullet[i].getAmmoY()>=backgroundSz.getHeight())
						bullet[i].setFriendlyFire(false);
					if(bullet[i].getAmmoY()<=0)
						{bullet[i].setFriendlyFire(true);bullet[i].setFireMotion(false);}
				}
				//TODO there is a bug which cases all bullets to erase if last bullet is fired
				if(bullet[bindex].getAmmoY()<=0|| bullet[bindex].getAmmoY()>=backgroundSz.getHeight())
				{
					bullet[bindex].setFireMotion(false);
					boolean stop =true;
					for(int i=0;i<bindex;i++)
						if(bullet[i].getFireMotion())
							stop=false;
					if(stop)
						{shift();bulletTime.stop();}
					fire=false;
				}
			}
			g.setColor(org.newdawn.slick.Color.white);
			g.drawString(name, 0, 580);
			g.drawString("Score: "+score, this.backgroundSz.getWidth()/2,30);
			g.drawString("Level: "+currentLevel, this.backgroundSz.getWidth()/2, 580);
		}
		else
		{
			g.setColor(Color.white);
			if(GameStart)
				g.drawString(BeginMessege, this.backgroundSz.getWidth()/2, this.backgroundSz.getHeight()/2);
			else
			{
				g.drawString(LoseMessage, 220, 300);
				g.drawString(Credits, 150, 350);
				g.drawString(Iz,220,370);
				g.drawString(Dn, 220, 390);
			}
		}
		
	}

	@Override
	public void init(GameContainer gamePanel) throws SlickException {
		try {
			back = new Image("image/back.PNG");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backgroundSz =new Dimension();
		backgroundSz.setSize(back.getWidth(), back.getHeight());
		sh = new MyShip();
		bulletTime = new Timer(10, new TimeListner());
		bullet = new Bullet[8];
		fire = false;
		enemy = new EnemyShip[8];
		GameOver=false;
		GameStart=false;
		currentLevel=1;
		nextLevel=2;
		fireSpeed=0;
		threshold=500;
		gamePanel.setFullscreen(false);
		kbInputListener = new KeyboardInput(sh, back.getWidth(), true);
		input = new InputProvider(gamePanel.getInput());
		input.setActive(true);
		System.out.println("This is a test for input");
		//fps = new Animation(true);
		//fps.start();
		input.addListener(kbInputListener);
		input.bindCommand(new KeyControl(Input.KEY_LEFT), kbInputListener.getMoveLeft());
		input.bindCommand(new KeyControl(Input.KEY_RIGHT), kbInputListener.getMoveRight());
		input.bindCommand(new KeyControl(Input.KEY_SPACE), kbInputListener.getFire());
		
		//gamePanel.set
	}

	@Override
	public void update(GameContainer gamePanel, int n) throws SlickException {
		System.out.println("Update panel");
		//TODO please use n as tick control for spam fire
		//input.isCommandControlDown()
		
	}
	
	private class TimeListner implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<=bindex;i++)
			{
				bullet[i].fireBullet();
				for(int k=0;k<eindex;k++)
					if(enemy[k].isColision(bullet[i].getAmmoX(), bullet[i].getAmmoY())
							&&!bullet[i].getFriendlyFire()){
						//TODO Erase bullets when hit enemy
						eraseEnemy(k);
						calculateScore();
					}
				if(sh.isColision(bullet[i].getAmmoX(),bullet[i].getAmmoY())
						&&bullet[i].getFriendlyFire())
				{
					if(sh.getLife()>0) sh.setLife(sh.getLife()-1);
					if(sh.getLife()<=0){
						GameOver=true;
						resetGame();
						//GameOver();
						bulletTime.stop();
					}
					else
						resetGame();
				}
			}
		}
	}
}
