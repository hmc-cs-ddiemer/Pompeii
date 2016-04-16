import java.awt.Image;
import java.util.ArrayList;

import objectdraw.*;

public class bulletHellGame extends ActiveObject{

	private ArrayList<asteroid> theAsteroids;
	private Image ass;
	private VisibleImage ship;
	private Controller control;
	private DrawingCanvas canvas;
	private int PAUSE = 30;
	private boolean dead;
	
	Timer theTimer;
	
	public bulletHellGame(Image ass, Image space, VisibleImage ship, Controller control, Timer theTimer, DrawingCanvas canvas){
		this.ass = ass;
		this.ship = ship;
		this.control = control;
		this.canvas = canvas;
		
		theAsteroids = new ArrayList<asteroid>();
		dead = false;
		this.theTimer = theTimer;
		start();
	}
	
	public void run(){
		RandomIntGenerator xCoord = new RandomIntGenerator(0,500);
		RandomIntGenerator xSpeed = new RandomIntGenerator(-8,8);
		int countdown = 7;
		while(!dead){
			if(countdown == 0){
			asteroid asst = new asteroid(xCoord.nextValue(), 0, xSpeed.nextValue(),ass,ship,this,canvas);
			theAsteroids.add(asst);
			countdown = 7;
			} else {
				countdown--;
			}
			pause(PAUSE);
		}
	}
	
	public void winKill(){
		dead = true;
		for(int i = 0; i < theAsteroids.size(); i++){
			theAsteroids.get(i).kill();
		}
		control.gameWon();
	}
	
	public void kill(){
		dead = true;
		for(int i = 0; i < theAsteroids.size(); i++){
			theAsteroids.get(i).kill();
		}
		control.gameLost();
		theTimer.stopped();
	}
}
