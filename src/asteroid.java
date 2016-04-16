import objectdraw.*;
import java.awt.Image;

public class asteroid extends ActiveObject{

	private VisibleImage ass;
	private final static int MOVE_IT_Y = 15;
	private final static int PAUSE = 50;
	private DrawingCanvas canvas;
	private int dx;
	private VisibleImage ship;
	private boolean dead = false;
	private bulletHellGame hell;
	private boolean stopped = false;
	
	public asteroid(int x, int y, int dx, Image asteroid, VisibleImage rocketShip, bulletHellGame hell, DrawingCanvas canvas){
		this.canvas = canvas;
		this.dx = dx;
		this.hell = hell;
		this.ship = rocketShip;
		ass = new VisibleImage(asteroid, x, y, 50, 50, canvas);
		start();
	}
	
	public void run(){
		dead = false;
		while(ass.getY() < canvas.getHeight() && !dead && !stopped){
			ass.move(dx, MOVE_IT_Y);
			pause(PAUSE);
			if(ass.overlaps(ship)){
				hell.kill();
				break;
			}
		}
		//ass.removeFromCanvas();
	}
	
	public void kill(){
		dead = true;
	}
	public void stopped(){
		stopped = true;
	}
	public void remove(){
		ass.removeFromCanvas();
	}
}
