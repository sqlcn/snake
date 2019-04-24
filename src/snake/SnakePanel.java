package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {
	ImageIcon title = new ImageIcon("title.jpg");
	ImageIcon right = new ImageIcon("right.png");
	ImageIcon up = new ImageIcon("up.png");
	ImageIcon left = new ImageIcon("left.png");
	ImageIcon down = new ImageIcon("down.png");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon food = new ImageIcon("food.png");

	int[] snakex = new int[750];
	int[] snakey = new int[750];
	int step;
	int len;
	String fangxiang;
	Random rand = new Random();
	int foodx;
	int foody;
	int score;

	boolean isStarted = false;
	boolean isFailed = false;

	Timer timer, t2;

	public SnakePanel() {
		this.setFocusable(true);
		this.addKeyListener(this);
		setup();
		timer.start();
//		t2.start();
	}

	public void setup() {
		len = 3;
		fangxiang = "R";
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		step = 0;
		foodx = rand.nextInt(34) * 25 + 25;
		foody = rand.nextInt(24) * 25 + 75;
		score = 0;
		timer = new Timer(100, this);
//		timer.setActionCommand("t1");
//		t2 = new Timer(400, this);
//		t2.setActionCommand("t2");
	}

	public void paint(Graphics g) {
		// this.setBackground(Color.BLACK);
		title.paintIcon(this, g, 25, 11);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(25, 75, 850, 600);

		// 画蛇头
		if (fangxiang.equals("R")) {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fangxiang.equals("L")) {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fangxiang.equals("D")) {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fangxiang.equals("U")) {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}
		// 画身体
		for (int i = 1; i < len; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		// 画食物
		food.paintIcon(this, g, foodx, foody);

		if (!isStarted && !isFailed) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press 'SPACE' To Start / Pause !", 200, 300);
		}
		if (isFailed) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Fail ! Press 'SPACE' To Restart !", 200, 300);
		}
		g.setColor(Color.RED);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Score:" + score, 750, 30);
		g.drawString("Length:" + len, 750, 50);
//		System.out.println(snakex[0]+","+snakey[0]+"--"+snakex[5]+","+snakey[5]);
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			isStarted = !isStarted;
			if (isFailed) {
				setup();
				isFailed = false;
			}
		} else if (keyCode == KeyEvent.VK_UP && fangxiang != "D") {
			fangxiang = "U";
		} else if (keyCode == KeyEvent.VK_DOWN && fangxiang != "U") {
			fangxiang = "D";
		} else if (keyCode == KeyEvent.VK_LEFT && fangxiang != "R") {
			fangxiang = "L";
		} else if (keyCode == KeyEvent.VK_RIGHT && fangxiang != "L") {
			fangxiang = "R";
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		// timer.start();
//		if(e.getSource()==t2)
//			System.out.println("t2");
//		if(e.getSource()==timer)
//			System.out.println("timer");
		if (isStarted) {
			for (int i = len - 1; i > 0; i--) {
				snakex[i] = snakex[i - 1];
				snakey[i] = snakey[i - 1];
			}
			if (fangxiang.equals("R")) {
				snakex[0] = snakex[0] + 25;
				if (snakex[0] > 850)
					snakex[0] = 25;
			} else if (fangxiang.equals("L")) {
				snakex[0] = snakex[0] - 25;
				if (snakex[0] < 25)
					snakex[0] = 850;
			} else if (fangxiang.equals("D")) {
				snakey[0] = snakey[0] + 25;
				if (snakey[0] > 650)
					snakey[0] = 75;
			} else if (fangxiang.equals("U")) {
				snakey[0] = snakey[0] - 25;
				if (snakey[0] < 75)
					snakey[0] = 650;
			}
			// 如果吃到了食物
			if (snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score++;
				foodx = rand.nextInt(34) * 25 + 25;
				foody = rand.nextInt(24) * 25 + 75;
			}
			// 如果吃到了自己的身体
			for (int i = 1; i < len; i++) {
				if (snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
					isFailed = true;
					isStarted = false;
				}
			}
			repaint();
		}
	}
}
