import java.util.ArrayList;

import objectdraw.*;


public class Bullets extends ActiveObject {
	
	private boolean stop = false;
	private boolean won = false;
	private int timer = 5000;
	private Controller control;
	private DrawingCanvas canvas;
	private Location bulLoc1;
	private Location bulLoc2;
	private Location bulLoc3;
	private ArrayList<bullet> theBullets;
	private Timer theTimer;
	private int bulletCount = 0;

	public Bullets(Location bulLoc1,Location bulLoc2,Location bulLoc3,Controller control, Timer theTimer, DrawingCanvas canvas){
		this.bulLoc1 = bulLoc1;
		this.bulLoc2 = bulLoc2;
		this.bulLoc3 = bulLoc3;
		this.control = control;
		this.theTimer = theTimer;
		this.canvas = canvas;
		
		bullet bullet1 = new bullet(control, bulLoc1, this, bulletCount, canvas);
		bulletCount++;
		bullet bullet2 = new bullet(control, bulLoc2, this, bulletCount, canvas);
		bulletCount++;
		bullet bullet3 = new bullet(control, bulLoc3, this, bulletCount, canvas);
		bulletCount++;
		theBullets = new ArrayList<bullet>();
		theBullets.add(bullet1);
		theBullets.add(bullet2);
		theBullets.add(bullet3);
		start();
	}
	
	public void run(){
		while(!stop && !won){
			timer -= 50;
			pause(50);
			if(timer == 4000 || timer == 3000 || timer == 2000){
				bullet bullet1 = new bullet(control, bulLoc1, this, bulletCount,canvas);
				bulletCount++;
				bullet bullet2 = new bullet(control, bulLoc2, this, bulletCount,canvas);
				bulletCount++;
				bullet bullet3 = new bullet(control, bulLoc3, this, bulletCount, canvas);
				bulletCount++;
				theBullets.add(bullet1);
				theBullets.add(bullet2);
				theBullets.add(bullet3);
			}
			//control.setTimer(timer);
			for(int i = 0; i < theBullets.size(); i++){
				if(theBullets.get(i).contains(control.getMouseLoc())){
					kill();
				}
			}
		}
		if(!stop){
			won();
		}
		
	}
	public void won(){
		stop = true;
		for(int i = 0; i < theBullets.size(); i++){
			theBullets.get(i).kill();
		}
		control.gameWon();
	}
	
	public void winKill(){
		won = true;
		for(int i = 0; i < theBullets.size(); i++){
			theBullets.get(i).kill();
		}
		control.gameWon();
	}
	
	public void kill(){
		stop = true;
		for(int i = 0; i < theBullets.size(); i++){
				theBullets.get(i).kill();
			
		}
		theTimer.stopped();
		control.gameLost();
	}
}
