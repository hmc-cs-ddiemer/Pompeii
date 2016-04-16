import java.awt.Color;

import objectdraw.*;


public class mouseTrace {

	private FilledRect r1;
	private FilledRect r2;
	private FilledRect r3;
	private FilledRect r4;
	private FilledRect r5;
	private FilledRect end;
	private FilledRect start;
	
	public mouseTrace(DrawingCanvas canvas){
		
		r1 = new FilledRect(0,canvas.getHeight()-25,canvas.getWidth(),25,canvas);
		r2 = new FilledRect(0,0,canvas.getWidth(),200,canvas);
		r3 = new FilledRect(0,225,150,canvas.getHeight() - 225,canvas);
		r4 = new FilledRect(250,200,150,canvas.getHeight() - 250,canvas);
		r5 = new FilledRect(475,225,150,canvas.getHeight() - 250,canvas);
		start = new FilledRect(0,200,50, 25,canvas);
		start.setColor(Color.green);
		end = new FilledRect(520,200,canvas.getWidth()-480, 25,canvas);
		end.setColor(Color.red);
		new Text("START", 2,205,canvas);
		new Text("END", 550,205,canvas);
	}
	
	public boolean winning(Location loc){
		return end.contains(loc);
	}
	public boolean contains(Location loc){
		return (r1.contains(loc) || r2.contains(loc) || r3.contains(loc) ||r4.contains(loc) || r5.contains(loc)) && (!start.contains(loc));
	}
}
