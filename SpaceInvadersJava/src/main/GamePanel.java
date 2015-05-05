/**
 * Version 0.1
 */

package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

import ship.EnemyShip;
import ship.MyShip;

public class GamePanel extends JPanel {
	private MyShip sh;
	private Timer bulletTime;
	private Bullet[] bullet;
	private int bindex=-1;
	private boolean fire;
	private EnemyShip []enemy;
	private int eindex=0;
	private Timer refreshRate;
	private int tick=0;
	private boolean GameOver;
	private boolean GameStart;
	private static String LoseMessage="GAME OVER!";
	private static String Credits="Created by:";
	private static String Iz="Izabela Beatrice Gafitoiu";
	private static String Dn="Danut Niculae";
	private static String BeginMessege="";
	private boolean fireSecret=false;
	private int currentLevel,nextLevel,fireSpeed,threshold;
	private OutDialog outro;
	private ImageIcon backgroundImg = new ImageIcon("image/back.PNG");
	private String name;
	private long score=0;
	private static final Dimension backgroundSz = new Dimension(1024,768);
	private boolean kLeft=false;
	private boolean kRight=false;
	private boolean kSpace=false;
	private boolean kCtrl=false;
	private boolean isPlayerFireReady=false;
	public GamePanel(String name)
	{
		sh =new MyShip();
		bulletTime = new Timer(10,new TimeListner());
		bullet = new Bullet[8];
		fire = false;
		enemy = new EnemyShip[8];
		GameOver=false;
		GameStart=true;
		if(name.equalsIgnoreCase("Tobias")) fireSecret=true;
		currentLevel=1;
		nextLevel=2;
		setPreferredSize(backgroundSz);
		setBackground(Color.black);
		addKeyListener(new MoveShip());
		setFocusable(true);
		this.name=name;
		fireSpeed=0;
		threshold=500;
		refreshRate = new Timer(5,new FPS());
		refreshRate.start();
	}
	public void enemyResize()
	{
		EnemyShip [] tmp = new EnemyShip[2*enemy.length];
		System.arraycopy(enemy, 0, tmp, 0, enemy.length);
		enemy=tmp;
	}
	private void setLevel()
	{	
		if(currentLevel==nextLevel)
		{
			nextLevel++;
			fireSpeed+=50;

		}
		if(currentLevel==9){
			try {
				outro = new OutDialog(name,score, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//TODO Overlapping issue still persists.
	private void overlapingEnemy(int i, EnemyShip e)
	{
		int k=0;
		while(k<i){
			//System.out.println("Spawn ship"+ " "+enemy[i].getX()+" "+k);
			//System.out.println("Overlap ship "+enemy[k].getX()+" "+k);
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
			enemy[eindex]= new EnemyShip();
			enemy[eindex].setFireLimit(fireSpeed);
			if(flag)enemy[eindex].setFireModeOn();
			if(i>=1) overlapingEnemy(i, enemy[eindex]);
			eindex++;
			if(eindex>=enemy.length) enemyResize();
		}
	}
	private void resetGame()
	{
		eindex=0;
		bindex=0;
		GameStart=true;
	}
	private void GameOver()
	{
		try {
			outro = new OutDialog(name,score, false);
		} catch (IOException e) {
			e.printStackTrace();
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
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		this.backgroundImg.paintIcon(this, g, 0, 
				0);
		
		if(!GameOver&&!GameStart){
			sh.getImage().paintIcon(this, g, sh.getX(), sh.getY());
			for(int i =0;i<eindex;i++)
				if(enemy[i].isAlive()){
					enemy[i].getImage().paintIcon(this, g, 
							enemy[i].getX(),enemy[i].getY());
				}

			if(fire){
				for(int i=0;i<=bindex;i++)
					if(bullet[i].getFireMotion())
						bullet[i].getBullet().paintIcon(this, g, bullet[i].getAmmoX(),
								bullet[i].getAmmoY());
				for(int i=0;i<=bindex;i++)
				{
					if(bullet[i].getAmmoY()>=backgroundSz.height)
						bullet[i].setFriendlyFire(false);
					if(bullet[i].getAmmoY()<=0)
						{bullet[i].setFriendlyFire(true);bullet[i].setFireMotion(false);}
				}
				//TODO there is a bug which cases all bullets to erase if last bullet is fired
				if(bullet[bindex].getAmmoY()<=0|| bullet[bindex].getAmmoY()>=backgroundSz.height)
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
			g.setColor(Color.white);
			g.setFont(new Font("Verdana",Font.BOLD,12));
			g.drawString(name, 0, 580);
			g.drawString("Score: "+score, this.backgroundSz.width/2,30);
			g.drawString("Level: "+currentLevel, this.backgroundSz.width/2, 580);
		}
		else
		{
			g.setColor(Color.white);
			g.setFont(new Font("Verdana",Font.BOLD,20));
			if(fireSecret)
			{
				g.drawString("If your name is Tobias a VG grade is required", 0, 300);
				g.drawString("to play this game!", 0, 320);
				g.drawString("Press left CTRL to play (and give VG)...", 0, 350);
				
			}
			if(GameStart)
				g.drawString(BeginMessege, this.backgroundSz.width/2, this.backgroundSz.height/2);
			else
			{
				g.drawString(LoseMessage, 220, 300);
				g.drawString(Credits, 150, 350);
				g.drawString(Iz,220,370);
				g.drawString(Dn, 220, 390);
				refreshRate.stop();
			}
		}
	}
	public static Dimension getBackgroundDimension(){return backgroundSz;}
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
	/*
	 * Please note that i have moved the ship movement in here. Some tweaks are required
	 * to achieve good performance 
	 */
	private void shipMovement()
	{
		if(sh.getX()>=0)
			if(kLeft)
				sh.setX(sh.getX()-2);
		if(sh.getX()<=(backgroundSz.width-sh.getWitdh()))
			if(kRight)
				sh.setX(sh.getX()+2);
		if(kCtrl){fireSecret=false;}
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
						GameOver();
						bulletTime.stop();
					}
					else
						resetGame();
				}
			}
		}
	}
	private class FPS implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(GameStart&&!fireSecret)
			{
				if(tick<=100)BeginMessege="3";
				else if(tick>100&&tick<=200) BeginMessege="2";
				else if(tick>200&&tick<=300) BeginMessege="1";
				else if(tick>300&&tick<=400) BeginMessege="GO!";
				else {GameStart=false;tick=0;spawnShip(false);}
				tick++;

			}
			else if(!fireSecret){
				tick++;
				if(tick>300){
					if(currentLevel>=3)
						spawnShip(true);
					else 
						spawnShip(false);
					tick=0;
					}
				//Checks every 20ms if player trigger is ready to fire.
				//I will use this to avoid spam fire
				
				if(tick%20==0) isPlayerFireReady=true;
				for(int i=0;i<eindex;i++){
					if(enemy[i].getFireMode()) fireEnemy();
					if(enemy[i].isGameOver()) {GameOver=true;resetGame();GameOver();}
				}
			}
			shipMovement();
			repaint();
		}


	}
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
	private void shift()
	{
		bindex=-1;
	}
	private class MoveShip implements KeyListener{
		
		@Override
		public void keyPressed(KeyEvent e) {
			//Please fix this for names instead of values
			//key codes(up - 38, down - 40, left - 37, right - 39, CTRL - 17, SPACE - 32)
			if(e.getKeyCode()==37)kLeft=true;
			if(e.getKeyCode()==39)kRight=true;
			if(e.getKeyCode()==17)kCtrl=true;
			if(e.getKeyCode()==32)kSpace=true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==37)kLeft=false;
			if(e.getKeyCode()==39)kRight=false;
			if(e.getKeyCode()==17)kCtrl=false;
			if(e.getKeyCode()==32)kSpace=false;
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}
}
