import objectdraw.ActiveObject;
import objectdraw.DrawingCanvas;
import objectdraw.FilledOval;
import objectdraw.FilledRect;


public class TheHurdles extends ActiveObject{

	private FilledRect r1;
	private FilledRect r2;
	private FilledRect r3;
	private FilledRect r4;
	private FilledRect r5;
	private FilledRect r6;
	private FilledRect r7;
	private FilledRect r8;
	private FilledRect r9;
	private FilledRect r10;
	private FilledRect end;
	private FilledRect start;
	
	private final static int WIDTH = 50;
	private final static int HEIGHT = 200;
	private final static int MOVE_IT = 15;
	private final static int PAUSE = 50;
	private final static int GAP = 300;
	private FilledOval player;
	private Controller control;
	private boolean dead = false;
	
	public TheHurdles(FilledOval player, Controller control, DrawingCanvas canvas){
		this.player = player;
		this.control = control;
		r1 = new FilledRect(canvas.getWidth(),0,WIDTH,HEIGHT,canvas);
		r2 = new FilledRect(canvas.getWidth(),HEIGHT + 50,WIDTH,HEIGHT,canvas);
		
		r3 = new FilledRect(r1.getX()+ GAP, 0, WIDTH, HEIGHT-50, canvas);
		r4 = new FilledRect(r1.getX() + GAP, HEIGHT,WIDTH,HEIGHT+50,canvas);
		
		r5 = new FilledRect(r3.getX() + GAP,0,WIDTH,HEIGHT,canvas);
		r6 = new FilledRect(r3.getX() + GAP ,HEIGHT + 50,WIDTH,HEIGHT,canvas);
		
		r7 = new FilledRect(r5.getX() + GAP,0,WIDTH,HEIGHT+100,canvas);
		r8 = new FilledRect(r5.getX() + GAP, HEIGHT+150 ,WIDTH,HEIGHT-100,canvas);
		
		r9 = new FilledRect(r7.getX() + GAP,0,WIDTH,HEIGHT+25,canvas);
		r10 = new FilledRect(r7.getX() + GAP,HEIGHT + 75,WIDTH,HEIGHT-25,canvas);
		
		start();
	}
	
	public void run(){
		while(r10.getX() > 0 && !dead){
			r1.move(-MOVE_IT, 0);
			r2.move(-MOVE_IT, 0);
			r3.move(-MOVE_IT, 0);
			r4.move(-MOVE_IT, 0);
			r5.move(-MOVE_IT, 0);
			r6.move(-MOVE_IT, 0);
			r7.move(-MOVE_IT, 0);
			r8.move(-MOVE_IT, 0);
			r9.move(-MOVE_IT, 0);
			r10.move(-MOVE_IT, 0);
			pause(PAUSE);
			if(r1.overlaps(player) || r2.overlaps(player) || r3.overlaps(player) ||
					r4.overlaps(player) || r5.overlaps(player) || r6.overlaps(player) ||
					r7.overlaps(player) || r8.overlaps(player) || r9.overlaps(player) ||
					r10.overlaps(player)){
				pause(500);
				dead = true;
				control.gameLost();
			}
		}
		if(!dead){
		r1.removeFromCanvas();
		r2.removeFromCanvas();
		r3.removeFromCanvas();
		r4.removeFromCanvas();
		r5.removeFromCanvas();
		r6.removeFromCanvas();
		r7.removeFromCanvas();
		r8.removeFromCanvas();
		r9.removeFromCanvas();
		r10.removeFromCanvas();
		
			control.gameWon();
		}
	}
	
	
	public void kill(){
		dead = true;
	}
}
