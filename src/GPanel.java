package run;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GPanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 400;
	static final int SCREEN_HEIGHT= 400;
	static final int UNIT_SIZE = 20;
	static final int UNIT_SIZE2 = 10;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 4;
	int appleEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean run = false;
	Timer timer;
	Random random;
	
	GPanel(){
		random = new Random();
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		run = true;
		newApple();
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void newApple() {
		appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
		appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(run) {
	
			/*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.setColor(Color.white);
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}*/
		
			g.setColor(Color.red);
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
		
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,20));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score:" + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten))/2, g.getFont().getSize());
			
			for(int i=0;i<bodyParts;i++) {
				if(i==0){
					g.setColor(Color.green);
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
				
				else {
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
			}
		}
		else {
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		g.drawString("Game Over", SCREEN_WIDTH/4, SCREEN_HEIGHT/2);
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,20));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score:" + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten))/2, g.getFont().getSize());
		
		
		
		
		
	}
	
	public void eat() {
		if(x[0] == appleX && y[0] == appleY) {
			bodyParts++;
			appleEaten++;
			newApple();
		}
	}

	public void move() {
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkCollisions() {
		for(int i = 1 ; i <bodyParts ; i++) {
			if(x[0] == x[i] && y[0] == y[i]) {
				run = false;
			}
		}
		if(x[0] < 0) {
			run = false;
		}
		if(x[0] > SCREEN_WIDTH-UNIT_SIZE) {
			run = false;
		}
		if(y[0] < 0) {
			run = false;
		}
		
		if(y[0] > SCREEN_HEIGHT-UNIT_SIZE) {
			run = false;
		}
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';			
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';			
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';			
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';			
				}
				break;
			case KeyEvent.VK_SPACE:
					DELAY = 75;
					new GFrame();
				break;
			}
			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(run) {
			move();
			eat();
			checkCollisions();
		}
		repaint();
		
	}

}
