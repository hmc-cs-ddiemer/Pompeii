import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import com.fazecast.jSerialComm.SerialPort;

import objectdraw.*;

public class Controller extends WindowController implements KeyListener {

	private Location mouseloc = new Location(0, 0);
	private Timer timer;
	private Bullets theBullets;
	private int GAME = 4;
	private NosePicker nosePickGame;
	private bulletHellGame bulletHell;
	private VisibleImage rocketShip;
	private VisibleImage spaceBackground;
	private mouseTrace mouseMaze;
	private FilledRect start;
	private boolean fourStarted = false;
	private FilledRect buttonNew;
	private FilledRect buttonOK;

	private int whichPlayer = 0;

	// dans maze variables
	private FilledOval ball;
	private FilledRect rect1Wall1;
	private FilledRect rect2Wall1;
	private FilledRect rect1Wall2;
	private FilledRect rect2Wall2;

	private FilledRect rect1Wall4;
	private FilledRect rect2Wall4;
	private FilledRect side1;
	private FilledRect side2;
	private Timer timeText;
	private Text howToPlay;

	// Dan HelloWorld
	private int lCounter = 0;
	private int oCounter = 0;
	private int wordComplete = 0;
	boolean hComplete = false;
	boolean eComplete = false;
	boolean lComplete = false;
	boolean oComplete = false;
	boolean wComplete = false;
	boolean rComplete = false;
	boolean dComplete = false;

	// hurdle game player
	private FilledOval playerHurdle;
	private TheHurdles hurds;

	// target game
	private VisibleImage arrow;
	private Target targe;
	private Arrow theArrow;

	private int[] orderOfGames;
	private int curGame = 0;

	private int green = 2;
	private int blue = 7;

	public void begin() {
		SerialPort ports[] = SerialPort.getCommPorts();

		SerialPort port = ports[4];
		if (port.openPort()) {
			System.out.println("Successfully opened port!!");
		} else {
			System.out.println("tre fails");
			return;
		}
		port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING, 0, 0);
		OutputStream out = port.getOutputStream();
		for (int j = 0; j < 10; j++) {
			try {
				out.write(Main.lightUp(2, 7));

			} catch (IOException e) {
				System.out.println("Somethings weird");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		port.closePort();

		orderOfGames = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		for (int i = 0; i < orderOfGames.length; i++) {
			int randy = new RandomIntGenerator(i, 7).nextValue();
			int temp = orderOfGames[i];
			orderOfGames[i] = orderOfGames[randy];
			orderOfGames[randy] = temp;

		}
		 //orderOfGames = new int[]{6,6,6,6,6,6};
		// System.out.println(Arrays.toString(orderOfGames));
		newPlayer(0);

		// Get ready to handle the arrow keys
		requestFocus();
		addKeyListener(this);
		setFocusable(true);
		canvas.addKeyListener(this);

	}

	public void timerStopped() {
		// game 1 is bullets
		if (GAME == 1) {
			theBullets.winKill();
		} else if (GAME == 2) {
			nosePickGame.stopped();
		} else if (GAME == 3) {
			bulletHell.winKill();
		} else if (GAME == 5) {
			gameLost();
		} else if (GAME == 6) {
			gameLost();
		}
	}

	public void initialize(int player) {
		canvas.clear();
		timer = new Timer(0, 0, canvas, this);
		if (GAME == 1) {
			theBullets = new Bullets(new Location(0, 0), new Location(0, canvas.getHeight()),
					new Location(canvas.getWidth(), canvas.getHeight()), this, timer, canvas);

			howToPlay = new Text("DODGE THE BALLS!", 100, 100, canvas);
			howToPlay.setFontSize(30);

		} else if (GAME == 2) {
			Image nose = getImage("nosehack.png");
			Image finger = getImage("fingerhack.png");
			FilledRect r1 = new FilledRect(0,0,canvas.getWidth(),canvas.getHeight(),canvas);
			r1.setColor(Color.cyan);
			r1.sendToBack();
			nosePickGame = new NosePicker(nose, finger, this, timer, canvas);
			howToPlay = new Text("PICK THE NOSE!", 100, 100, canvas);
			howToPlay.setFontSize(30);
		} else if (GAME == 3) {
			Image asteroid = getImage("asteroidhack.png");
			Image ship = getImage("shiphack.png");
			Image space = getImage("space.png");
			spaceBackground = new VisibleImage(space, 0, 0, 800, 800, canvas);
			spaceBackground.sendToBack();
			rocketShip = new VisibleImage(ship, 250, canvas.getHeight() - 100, 50, 100, canvas);

			bulletHell = new bulletHellGame(asteroid, space, rocketShip, this, timer, canvas);

			howToPlay = new Text("DODGE THE ASTEROIDS", 100, 100, canvas);
			howToPlay.setFontSize(30);
			howToPlay.setColor(Color.white);

		} else if (GAME == 4) {
			start = new FilledRect(0, 200, 50, 25, canvas);
			start.setColor(Color.green);
			new Text("START", 2, 205, canvas);
			
			
			howToPlay = new Text("GO TO START, THEN DO THE MAZE", 0, 100, canvas);
			howToPlay.setFontSize(30);
			howToPlay.setColor(Color.pink);
			timer.stopped();

		} else if (GAME == 5) {
			howToPlay = new Text("Complete the Maze!", 100, 450, canvas);
			howToPlay.setFontSize(30);
			FilledRect r1 = new FilledRect(0,0,canvas.getWidth(),canvas.getHeight(),canvas);
			r1.setColor(Color.GRAY);
			
			// ball object
			ball = new FilledOval(250, 400, 35, 35, canvas);
			ball.setColor(Color.red);

			// permanent sides, dont move
			side1 = new FilledRect(0, 10, 15, 450, canvas);
			side2 = new FilledRect(600, 10, 15, 450, canvas);

			// WALLS
			rect1Wall1 = new FilledRect(10, 10, 300, 15, canvas);
			rect2Wall1 = new FilledRect(375, 10, 225, 15, canvas);

			rect1Wall2 = new FilledRect(10, 130, 100, 15, canvas);
			rect2Wall2 = new FilledRect(180, 130, 420, 15, canvas);

			// leave out for now
			// rect1Wall3 = new FilledRect(10, 375, 355, 15, canvas);
			// rect2Wall3 = new FilledRect(440, 375, 165, 15, canvas);

			// maybe add in later but game is 2 hard 4 5 seconds
			// rect1Wall4 = new FilledRect(10, 495, 305, 15, canvas);
			// rect2Wall4 = new FilledRect(380, 495, 230, 15, canvas);

			rect1Wall4 = new FilledRect(10, 250, 200, 15, canvas);
			rect2Wall4 = new FilledRect(280, 250, 330, 15, canvas);
			rect1Wall1.sendToBack();
			side1.sendToBack();
			r1.sendToBack();

		} else if (GAME == 6) {
			howToPlay = new Text("Type 'hello world'", 100, 100, canvas);
			howToPlay.setFontSize(30);

			FilledRect r1 = new FilledRect(0,0,canvas.getWidth(),canvas.getHeight(),canvas);
			r1.setColor(Color.GRAY);
			r1.sendToBack();
			// timeText = new Timer(450, 450, canvas, this);

		} else if (GAME == 7) {
			howToPlay = new Text("FLAPPY BIRD KNOCKOFF", 100, 550, canvas);
			howToPlay.setFontSize(30);
			Image trackPic = getImage("flappy.png");
			new VisibleImage(trackPic,0,0,canvas.getWidth(),canvas.getHeight(),canvas);
			playerHurdle = new FilledOval(50, 250, 25, 25, canvas);

			hurds = new TheHurdles(playerHurdle, this, canvas);
			timer.stopped();
			howToPlay.sendToFront();

		} else if (GAME == 8) {
			howToPlay = new Text("Shoot the Target", 100, 250, canvas);
			howToPlay.setFontSize(30);
			FilledRect r1 = new FilledRect(0,0,canvas.getWidth(),canvas.getHeight(),canvas);
			r1.setColor(Color.GRAY);
			r1.sendToBack();
			Image arrowPic = getImage("arrow.png");
			Image TargPic = getImage("target.png");

			arrow = new VisibleImage(arrowPic, 400, canvas.getHeight() - 100, 10, 100, canvas);

			targe = new Target(arrow, TargPic, this, canvas);
			theArrow = new Arrow(arrow, targe, this, canvas);
			timer.stopped();
		}

	}

	public void newGame(int player) {
		// GAME = new RandomIntGenerator(1,5).nextValue();
		if(green == 6 || blue == 11){
			FilledRect r1 = new FilledRect(0,0,canvas.getWidth(), canvas.getHeight(),canvas);
			r1.setColor(Color.green);
			howToPlay = new Text("GAME OVER", 100, 100, canvas);
			howToPlay.setFontSize(30);
		} else if(curGame > 7){
			orderOfGames = new int[]{1,2,3,4,5,6,7,8};
			for(int i = 0; i < orderOfGames.length; i++){
				int randy = new RandomIntGenerator(i,7).nextValue();
				int temp = orderOfGames[i];
				orderOfGames[i] = orderOfGames[randy];
				orderOfGames[randy] = temp;
			}
				curGame = 0;
				
			}
		GAME=orderOfGames[curGame];curGame++;

	initialize(player);
	}

	public void newPlayer(int i) {
		GAME = 0;
		canvas.clear();
		FilledRect background = new FilledRect(0, 0, canvas.getWidth(),
				canvas.getHeight(), canvas);
		background.setColor(Color.cyan);
		buttonNew = new FilledRect(canvas.getWidth() / 2 - 100,
				canvas.getHeight() / 2 - 50, 200, 100, canvas);
		buttonNew.setColor(Color.MAGENTA);
		Text playerText = new Text("Player " + (i + 1), buttonNew.getX() + 40,
				buttonNew.getY() + 25, canvas);
		playerText.setFontSize(32);

	}

	public void gameLost() {
		GAME = -1;
		canvas.clear();
		new Text("YOU R BAD", 50, 50, canvas);
		whichPlayer = (whichPlayer + 1) % 2;
		buttonOK = new FilledRect(canvas.getWidth() / 2 - 100,
				canvas.getHeight() / 2 - 50, 200, 100, canvas);
		buttonOK.setColor(Color.RED);
		Text playerText = new Text("OK :(", buttonOK.getX() + 40,
				buttonOK.getY() + 25, canvas);
		playerText.setFontSize(32);
	}

	public void gameWon() {
		GAME = -1;
		canvas.clear();
		new Text("YOU ROCK", 50, 50, canvas);
		whichPlayer = (whichPlayer + 1) % 2;
		buttonOK = new FilledRect(canvas.getWidth() / 2 - 100, canvas.getHeight() / 2 - 50, 200, 100, canvas);
		buttonOK.setColor(Color.YELLOW);
		Text playerText = new Text("YIPPIE :)", buttonOK.getX() + 40, buttonOK.getY() + 25, canvas);
		playerText.setFontSize(32);

		SerialPort ports[] = SerialPort.getCommPorts();

		SerialPort port = ports[4];
		if (port.openPort()) {
			System.out.println("Successfully opened port!!");
		} else {
			System.out.println("tre fails");
			return;
		}
		if (whichPlayer == 0) {
			green++;
		}
		if (whichPlayer == 1) {
			blue++;
		}
		port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING, 0, 0);
		OutputStream out = port.getOutputStream();
		for (int j = 0; j < 10; j++) {
			try {
				out.write(Main.lightUp(green, blue));

			} catch (IOException e) {
				System.out.println("Somethings weird");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		port.closePort();
		if(green == 6 || blue == 11){
			FilledRect r1 = new FilledRect(0,0,canvas.getWidth(), canvas.getHeight(),canvas);
			r1.setColor(Color.green);
			howToPlay = new Text("GAME OVER", 100, 100, canvas);
			howToPlay.setFontSize(30);
		} 
	}

	public void mouseGameWon() {
		Text win = new Text("YOU ROCK", 50, 50, canvas);
		win.setColor(Color.white);
	}

	public void mouseGameLost() {
		Text lost = new Text("YOU SUCK", 50, 50, canvas);
		lost.setColor(Color.white);
	}

	public Location getMouseLoc() {
		return mouseloc;
	}

	public void onMouseClick(Location loc) {
		if (GAME == 0 && buttonNew.contains(loc)) {
			newGame(whichPlayer);
		} else if (GAME == -1 && buttonOK.contains(loc)) {
			newPlayer(whichPlayer);
		}
	}

	public void onMouseMove(Location loc) {
		mouseloc = loc;
		if (GAME == 4 && !fourStarted) {
			if (start.contains(mouseloc)) {
				mouseMaze = new mouseTrace(canvas);
				fourStarted = true;
			}
		}

		if (GAME == 4 && fourStarted) {
			if (mouseMaze.contains(mouseloc)) {
				gameLost();
				fourStarted = false;
			} else if (mouseMaze.winning(mouseloc)) {
				gameWon();
				fourStarted = false;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(e.getKeyCode());
		// moves the finger for the nose pick
		// if hit space
		if (e.getKeyCode() == 32 && GAME == 2) {
			nosePickGame.stopSliding();
		}
		if (e.getKeyCode() == 32 && GAME == 8) {
			theArrow.fire();
		}

		// moves ship left during bullet hell

		if (e.getKeyCode() == 37 && GAME == 3) {
			rocketShip.move(-15, 0);
		} else if (e.getKeyCode() == 39 && GAME == 3) {
			rocketShip.move(15, 0);
		}

		if (GAME == 5) {
			// move up
			if (e.getKeyCode() == 38) {
				if (ball.overlaps(rect1Wall1) || ball.overlaps(rect1Wall2) || ball.overlaps(rect1Wall4)
						|| ball.overlaps(rect2Wall1) || ball.overlaps(rect2Wall2) || ball.overlaps(rect2Wall4)) {
					ball.move(0, 0);

				} else {

					ball.move(0, -25);
				}
			}
			// move down
			if (e.getKeyCode() == 40) {

				ball.move(0, 25);

			}

			// move left
			if (e.getKeyCode() == 37) {
				if (ball.overlaps(side1) || ball.overlaps(side2)) {
					ball.move(0, 0);
				} else {

					ball.move(-25, 0);
				}
			}

			// move right
			if (e.getKeyCode() == 39) {
				if (ball.overlaps(side1) || ball.overlaps(side2)) {
					ball.move(0, 0);
				} else {

					ball.move(25, 0);
				}

			}

			this.gameOverWin(ball.getY());
		}
		if (GAME == 7) {
			if (e.getKeyCode() == 38) {
				playerHurdle.move(0, -20);
			}
			if (e.getKeyCode() == 40) {
				playerHurdle.move(0, 20);
			}
		}
	}

	// dans game over for maze
	public void gameOverWin(double y) {
		if (y <= 10) {
			timer.stopped();
			gameWon();
		}
	}

	// dan's game over for helloworld
	public void helloWorldEndWin() {
		if (hComplete && eComplete && lComplete && oComplete && wComplete && rComplete && dComplete) {
			hComplete = false;
			eComplete = false;
			lComplete = false;
			oComplete = false;
			wComplete = false;
			rComplete = false;
			dComplete = false;
			oCounter = 0;
			lCounter = 0;
			timer.stopped();
			gameWon();

		}

	}

	public void keyReleased(KeyEvent e) {
		if (GAME == 6) {
			if (e.getKeyCode() == 72) {
				Text h = new Text("h", 75, 200, canvas);
				h.setFontSize(45);
				h.setColor(Color.green);
				hComplete = true;
			}
			// press e
			if (e.getKeyCode() == 69) {
				Text el = new Text("e", 100, 200, canvas);
				el.setFontSize(45);
				el.setColor(Color.red);
				eComplete = true;
			}
			// press l
			if (e.getKeyCode() == 76) {
				System.out.println(e.getKeyCode());
				if (lCounter == 2) {
					Text l = new Text("l", 300, 200, canvas);
					l.setFontSize(45);
					l.setColor(Color.blue);
					lComplete = true;

				} else if (lCounter == 1) {
					Text secondl = new Text("l", 150, 200, canvas);
					secondl.setFontSize(45);
					secondl.setColor(Color.blue);

				} else if (lCounter == 0) {
					Text thirdl = new Text("l", 125, 200, canvas);
					thirdl.setFontSize(45);
					thirdl.setColor(Color.blue);

				}
				lCounter += 1;
				System.out.println(lCounter);
				wordComplete += 1;

			}
			// press o
			if (e.getKeyCode() == 79) {

				if (oCounter == 0) {
					System.out.println(oCounter);
					Text secondO = new Text("o", 175, 200, canvas);
					secondO.setFontSize(45);
					secondO.setColor(Color.pink);

				} else if (oCounter == 1) {
					System.out.println(oCounter + "FD");
					Text firstO = new Text("o", 250, 200, canvas);
					firstO.setFontSize(45);
					firstO.setColor(Color.black);

					oComplete = true;
				}
				oCounter += 1;
				wordComplete += 1;
			}

			// press w
			if (e.getKeyCode() == 87) {
				Text w = new Text("w", 220, 200, canvas);
				w.setFontSize(45);
				w.setColor(Color.magenta);
				wordComplete += 1;
				wComplete = true;
			}
			// press r
			if (e.getKeyCode() == 82) {
				Text r = new Text("r", 275, 200, canvas);
				r.setFontSize(45);
				r.setColor(Color.orange);
				wordComplete += 1;
				rComplete = true;
			}
			// press d
			if (e.getKeyCode() == 68) {
				Text d = new Text("d", 325, 200, canvas);
				d.setFontSize(45);
				wordComplete += 1;
				dComplete = true;

			}
			helloWorldEndWin();
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
