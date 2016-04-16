import objectdraw.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class bullet extends ActiveObject {
	private static final double DIAMETER = 40;
	private static final double MOVE_IT = 18;
	private FilledOval bullet;
	private Controller theControl;
	private Bullets dasBullets;
	private int pause = 49;
	private int totalTime = 5000;
	private boolean dead = false;
	private int id;

	public bullet(Controller controller, Location start, Bullets dasBullets, int id, DrawingCanvas canvas) {
		this.theControl = controller;
		bullet = new FilledOval(start, DIAMETER, DIAMETER, canvas);
		bullet.setColor(Color.PINK);
		this.dasBullets = dasBullets;
		this.id = id;
		start();
	}

	public void run() {
		
		while (!dead) {
			
			Location mouseLoc = theControl.getMouseLoc();
			Location bulletLoc = bullet.getLocation();
			double mXLoc = mouseLoc.getX();
			double mYLoc = mouseLoc.getY();
			double bXLoc = bulletLoc.getX();
			double bYLoc = bulletLoc.getY();

			double deltX = mXLoc - bXLoc;
			double deltY = mYLoc - bYLoc;
			if (deltX != 0) {
				double angle = Math.atan(deltY / deltX);

				double dX = MOVE_IT * Math.cos(angle);
				double dY = MOVE_IT * Math.sin(angle);

				if (mXLoc < bXLoc) {
					dX = -dX;
					dY = -dY;
				}
				double newX = bXLoc + dX;
				double newY = bYLoc + dY;
				if (Math.sqrt(deltX * deltX + deltY * deltY) < 10) {
					bullet.moveTo(theControl.getMouseLoc().getX()-DIAMETER/2,theControl.getMouseLoc().getY()-DIAMETER/2);
				} else {
					bullet.moveTo(newX, newY);
				}
			} else {
				bullet.move(0, MOVE_IT);
			}
			pause(pause);
			totalTime -= pause;
			
		}
		
	}
	
	public boolean contains(Location loc){
		return bullet.contains(loc);
	}

	public void removeBall(){
		bullet.removeFromCanvas();
	}
	
	public void kill() {
		dead= true;
		removeBall();

	}

}
