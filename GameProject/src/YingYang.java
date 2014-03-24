import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.Random;
//Ying Yang Game. 


class YingYang extends JPanel implements ActionListener, MouseListener,
		MouseMotionListener {
	Timer tm, tm2, tm3;// 3 timer objects for 3 action listeners

	Cursor cursor;// Cursor class to change cursor when hover over Yings

	Random rn = new Random();
	// initial colors
	Color cl = Color.green;
	Color cl2 = Color.red;

	Graphics2D g2;
	Ying2 h1, h2;

	// constant values for timers
	private final int time = 30;
	private final int time2 = 60000;
	private final int time3 = 1000;

	boolean stopped = false;// game is not stopped	

	private double X1 = 5; // Initial X coordinate of Ying 1
	private double Y1 = 5; // Initial Y coordinate of Ying 1
	private double X2 = 150; // Initial X coordinate of Ying 2
	private double Y2 = 150; // Initial Y coordinate of Ying 2
	private double W1 = 100; // Initial Width of Ying 1
	private double H1 = 100; // Initial Height of Ying 1
	private double W2 = 75; // Initial Width of Ying 2
	private double H2 = 75; // Initial Height of Ying 2

	private final double MinSize = 60, MaxSize = 80;// min and max size of yings

	int rotationTime = 60;// 60 second counter until rotaion starts

	// min and max values that yings should up to to
	private double MinY;
	private double MaxY;
	private double MaxY2;
	private double MinX;
	private double MaxX;
	private double MaxX2;

	private boolean small = true;// if size limit is reached, small is truerue
	private double rotate = 0;

	// initial speed of x and y of both yings
	private double dx1 = 5;
	private double dy1 = 3;
	private double dx2 = 7;
	private double dy2 = 10;

	private int score = 0;

	private int misses = 10;

	public static final int PANEL_WIDTH = 600;
	public static final int PANEL_HEIGHT = 500;

	// boolean tim = false;

	//constructor add the two two interfaces and initializes two actionlisteners
	public YingYang() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

		tm = new Timer(time, this);

		tm3 = new Timer(time3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (score > 0)
					score--;
				if (rotationTime > 0)
					rotationTime--;
			}
		});


	}
	//this method draws everthing,initializes the last action listener and starts the timers
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.translate(getWidth() / 2.0, getHeight() / 2.0);

		drawBorders();
		drawYings();
		tm2 = new Timer(time2, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				rotate += .5;

			}

		});

		startTimers();

		if (stopped) {
			stopGame();

		}		

	}
	//this method is called every 20 ms. It is in charge of wall hits, speeds, ying hits
	@Override
	public void actionPerformed(ActionEvent e) {

		setMaxes();

		wallHit();

		calcSpeeds();

		yingHits();

		X1 += dx1;
		Y1 += dy1;
		X2 += dx2;
		Y2 += dy2;

		repaint();

	}

	private void setMaxes() {
		MinY = -200;
		MaxY = 250 - W1;
		MinX = -300;
		MaxY2 = 250 - W2;
		MaxX = 300 - W1;
		MaxX2 = 300 - W2;

	}

	private void drawBorders() {
		Font fn = new Font("Serif", Font.BOLD, 20);
		g2.setColor(Color.YELLOW);
		g2.setFont(fn);

		g2.drawString("Score: " + score, -290, -220);
		g2.drawString("Misses Remaining: " + misses, 105, -208);
		g2.drawString("Getting Harder in: " + rotationTime + " Seconds", 35,
				-228);

		g2.draw(new Line2D.Double(-300, 250, 300, 250));
		g2.draw(new Line2D.Double(300, -250, 300, 250));

		g2.draw(new Line2D.Double(-300, -250, -300, 250));
		g2.draw(new Line2D.Double(300, -250, -300, -250));
		g2.draw(new Line2D.Double(-300, -200, 300, -200));
	}

	private void drawYings() {
		h1 = new Ying2(X1, Y1, W1, H1);
		g2.setColor(cl);
		AffineTransform tr = new AffineTransform();

		tr.setToRotation(rotate, X1 + W1 / 2, Y1 + H1 / 2);

		Shape s = tr.createTransformedShape(h1);

		g2.fill(s);
		g2.setColor(Color.yellow);

		h2 = new Ying2(X2, Y2, W2, H2);
		g2.setColor(cl2);

		tr.setToRotation(rotate, X2 + W2 / 2, Y2 + H2 / 2);
		Shape s1 = tr.createTransformedShape(h2);

		g2.fill(s1);
		g2.setColor(Color.white);

	}

	private void wallHit() {
		if (dx1 + X1 <= MinX || dx1 + X1 >= MaxX || dy1 + Y1 <= MinY
				|| dy1 + Y1 >= MaxY)
			cl = new Color(rn.nextInt());
		if (dx2 + X2 <= MinX || dx2 + X2 >= MaxX2 || dy2 + Y2 <= MinY
				|| dy2 + Y2 >= MaxY2)
			cl2 = new Color(rn.nextInt());
	}

	private void calcSpeeds() {
		if (dx1 + X1 < MinX)
			dx1 = (rn.nextDouble() * 10.0) + 1.0;
		else if (dx1 + X1 > MaxX)
			dx1 = (rn.nextDouble() * -10.0) - 1.0;

		if (dy1 + Y1 < MinY)
			dy1 = (rn.nextDouble() * 10.0) + 1.0;
		else if (dy1 + Y1 > MaxY)
			dy1 = (rn.nextDouble() * -10.0) - 1.0;

		if (dx2 + X2 < MinX)
			dx2 = (rn.nextDouble() * 10.0) + 1.0;
		else if (dx2 + X2 > MaxX2)
			dx2 = (rn.nextDouble() * -10.0) - 1.0;

		if (dy2 + Y2 < MinY)
			dy2 = (rn.nextDouble() * 10.0) + 1.0;
		else if (dy2 + Y2 > MaxY2)
			dy2 = (rn.nextDouble() * -10.0) - 1.0;
	}

	private void yingHits() {
		if (h1.intersects(X2, Y2, W2, H2, dx1, dx2, dy1, dy2)) {			
			
			//if yings collide with each other, transfer each ying's speed and direction to the other
			double temp1 = dx1;
			double temp2 = dy1;

			dx1 = dx2;
			dy1 = dy2;
			dx2 = temp1;
			dy2 = temp2;
			
			//if yings collide with each other, change their sizes randomly
			double decrease = .5 + (1 - .5) * rn.nextDouble();
			double increase = 1 + (2 - 1) * rn.nextDouble();

			if (W2 > MinSize && W1 > MinSize && W2 < MaxSize && W1 < MaxSize) {
				if (small == true) {
					W2 *= decrease;
					H2 *= decrease;
					W1 *= increase;
					H1 *= increase;
					small = false;

				} else {
					W2 *= increase;
					H2 *= increase;
					W1 *= decrease;
					H1 *= decrease;
					small = true;
				}
			} else {
				if (W2 <= MinSize) {
					W2 *= increase;
					H2 *= increase;
				} else if (W2 >= MaxSize) {
					W2 *= decrease;
					H2 *= decrease;
				}
				if (W1 <= MinSize) {
					W1 *= increase;
					H1 *= increase;
				} else if (W1 >= MaxSize) {
					W1 *= decrease;
					H1 *= decrease;
				}
			}

		}
	}

	

	private void stopGame() {
		Font fn = new Font("Serif", Font.BOLD, 30);
		g2.setColor(Color.red);
		g2.setFont(fn);

		g2.drawString("GAME OVER!", -100, -150);
		g2.drawString("You Scored " + score + " Points.", -150, -120);
		
		stopTimers();

	}


	private void startTimers() {
		tm.start();
		tm2.start();
		tm3.start();
	}

	private void stopTimers() {
		tm.stop();
		tm2.stop();
		tm3.stop();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		e.translatePoint(-300, -250);
		//if player clicks on ying, give them points according to the speed 
		//and size of yings(more for faster and smaller) else decrease misses by 1 
		if (h1.contains(e.getX(), e.getY()))
			score += (5 * Math.abs((dx1 * dy1))) -(W1*.10) ;
		else if (h2.contains(e.getX(), e.getY()))
			score += (5 * Math.abs((dx2 * dy2))) - (W2*.10) ;
		else
			misses--;

		if (misses <= 0)
			stopped = true;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}
	
	//this method changes cursors when hover over the yings
	@Override
	public void mouseMoved(MouseEvent e) {

		e.translatePoint(-300, -250);
		if (h1.contains(e.getX(), e.getY()))
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		else if (h2.contains(e.getX(), e.getY()))
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		else
			this.setCursor(Cursor.getDefaultCursor());

	}

}
