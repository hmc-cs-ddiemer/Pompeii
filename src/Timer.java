import objectdraw.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Timer extends ActiveObject {
	private int timer = 5000;
	private int pause = 10;
	private Text timerText;
	Controller controller;
	private boolean stopped = false;
	
	public Timer(double x, double y, DrawingCanvas canvas, Controller controller){
		timerText = new Text(timer, x, y, canvas);
		timerText.setColor(Color.green);
		timerText.setFontSize(40);
		this.controller = controller;
		start();
	}
	


	public void run(){
		while(getTimer() > 0 && !stopped){
			timer -= pause;
			timerText.setText(getTimer());
			pause(pause);
		}
		if(!stopped){
		controller.timerStopped();
		}
		timerText.setText(" ");
	}
	
	public void stopped(){
		stopped = true;
	}
	public int getTimer() {
		return timer;
	}
	

	
	
}
