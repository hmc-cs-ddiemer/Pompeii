import java.awt.Image;

import objectdraw.ActiveObject;
import objectdraw.Drawable2DInterface;
import objectdraw.DrawingCanvas;
import objectdraw.FilledOval;
import objectdraw.VisibleImage;


public class Target extends ActiveObject{

	private VisibleImage arrow;
	private VisibleImage target;
	private final static int MOVE_IT = 15;
	private final static int PAUSE = 50;
	private DrawingCanvas canvas;
	private Controller controller;
	private boolean dead = false;
	private boolean won = false;
	boolean isDone = false;
	public Target(VisibleImage arrow, Image target1, Controller controller, DrawingCanvas canvas){
		this.arrow = arrow;
		target = new VisibleImage(target1, 0,100,100,100,canvas);
		this.canvas = canvas;
		this.controller = controller;
		start();
	}
	
	public void run() {
		
		while(target.getX() >= 0){
		while(target.getX() < 300 - MOVE_IT && !dead){
			target.move(MOVE_IT, 0);
			pause(PAUSE);
		}
		while(target.getX() > 0 + MOVE_IT && !dead){
			target.move(-MOVE_IT, 0);
			pause(PAUSE - 10);
		}
		while(target.getX() < 300 - target.getWidth() - MOVE_IT && !dead){
			target.move(MOVE_IT, 0);
			pause(PAUSE+5);
		}
		while(target.getX() > 0 + MOVE_IT && !dead){
			target.move(-MOVE_IT, 0);
			pause(PAUSE);
		}
		while(target.getX() < canvas.getWidth() - target.getWidth() - MOVE_IT && !dead){
			target.move(1.5*MOVE_IT, 0);
			pause(PAUSE);
		}
		while(!dead){
			target.move(-MOVE_IT, 0);
			pause(PAUSE);
			if(target.getX() < 0){
				isDone = true;
				break; 
				
			}
			
		}
		}
		if(isDone){
		target.removeFromCanvas();
		controller.gameLost();
		
		}
		
	}
	
	public void winKill(){
		won = true;
		dead = true;
		target.removeFromCanvas();
	}
	public void kill(){
		won = true;
		dead = true;
		isDone = true;
		target.removeFromCanvas();
	}
	public boolean overlaps(Drawable2DInterface object){
		return target.overlaps(object);
	}
}
