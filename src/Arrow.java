import java.awt.Color;
import java.awt.Image;

import objectdraw.*;


public class Arrow extends ActiveObject{

	VisibleImage arrow;
	VisibleImage finger;
	boolean sliding = true;
	private static final int MIN_X = 100;
	private static final int MAX_X = 400;
	private static final int Y_COORD = 400;
	private static final int MOVE_IT = 25;
	private static final int PAUSE = 50;
	private static final int SIZE = 200;
	private Controller control;
	private FilledOval nostril;
	private boolean dead = false;
	private boolean fired = false;
	Target target;
	
	public Arrow(VisibleImage arrow, Target theTarget, Controller theControl, DrawingCanvas canvas){
		this.control = theControl;
		this.arrow = arrow;
		target = theTarget;
		
		start();
	}
	
	public void run(){
		while(!fired){
			pause(30);
		}
		boolean won = false;
		while(arrow.getY() > 0 && fired){
			arrow.move(0, -MOVE_IT);
			pause(PAUSE);
			
			
			if(target.overlaps(arrow)){
				target.winKill();
				arrow.removeFromCanvas();
				control.gameWon();
				won = true;
				pause(500);
				
			} 
		}
		if(!won){
		target.kill();
		arrow.removeFromCanvas();
		
		control.gameLost();
		}
		
	}
	
	public void kill(){
		dead = true;
		arrow.removeFromCanvas();
	}
	
	public void fire(){
		fired = true;
	}
}