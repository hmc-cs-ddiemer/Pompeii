import java.awt.Color;
import java.awt.Image;

import objectdraw.*;


public class NosePicker extends ActiveObject{

	VisibleImage nose;
	VisibleImage finger;
	boolean sliding = true;
	private static final int MIN_X = 100;
	private static final int MAX_X = 400;
	private static final int Y_COORD = 400;
	private static final int MOVE_IT = 15;
	private static final int PAUSE = 50;
	private static final int SIZE = 200;
	private Controller control;
	private FilledOval nostril;
	private boolean stopped = false;
	Timer theTimer;
	
	public NosePicker(Image theNose, Image theFinger,Controller theControl, Timer theTimer, DrawingCanvas canvas){
		this.control = theControl;
		nostril = new FilledOval(335,150,50,45,canvas);
		nostril.setColor(Color.red);
		nose = new VisibleImage(theNose, 250,11,SIZE,SIZE,canvas);
		finger = new VisibleImage(theFinger, MAX_X, Y_COORD,100,200,canvas);
		this.theTimer = theTimer;
		
		start();
	}
	
	public void stopSliding(){
		sliding = false;
	}
	public void run(){
		boolean left = true;
		while(sliding && !stopped){
			if(left && (finger.getX() > (MIN_X + MOVE_IT))){
				finger.move(-MOVE_IT, 0);
			} else if(left){
				left = false;
				finger.moveTo(MIN_X, Y_COORD);
			} else if(finger.getX() < (MAX_X - MOVE_IT)){
				finger.move(MOVE_IT, 0);
			} else {
				left = true;
				finger.moveTo(MAX_X, Y_COORD);
			}
			pause(PAUSE);
		}
		while(finger.getY() > SIZE && !stopped){
			finger.move(0, -MOVE_IT);
			pause(PAUSE);
		}
		
		if(finger.overlaps(nostril)){
			theTimer.stopped();
			pause(1000);
			control.gameWon();
		} else {
			control.gameLost();
		}

	}
	public void stopped(){
		stopped = true;
	}
}
