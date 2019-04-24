package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MySnake extends JPanel implements ActionListener, KeyListener {

	ImageIcon right = new ImageIcon("right.png");
	ImageIcon up = new ImageIcon("up.png");
	ImageIcon left = new ImageIcon("left.png");
	ImageIcon down = new ImageIcon("down.png");
	ImageIcon body = new ImageIcon("body.png");

	Vector bodyList = new Vector();
	int Bodylength;
	Timer timer;
	int keyPress = 0;
	int moveStep = 0;
	boolean isFailed, isStarted;

	void init(SnakeVector2D pos) {
//		Bodylength = 0;
		bodyList.removeAllElements();
		bodyList.addElement(pos);
		// System.out.println("1 : "+pos.x);
		// pos.x = pos.x-25;
		// System.out.println("2 : "+pos.x);
		bodyList.addElement(new SnakeVector2D(pos.x - 25, 100,
				SnakeVector2D.RIGHT));
		// pos.x = pos.x-25;
		// System.out.println("3 : "+pos.x);
		bodyList.addElement(new SnakeVector2D(pos.x - 50, 100,
				SnakeVector2D.RIGHT));
		// System.out.println("---"+bodyList);
		Bodylength = bodyList.size();
		keyPress = 0;
		moveStep = 0;
		isFailed = false;
		isStarted = true;
		timer = new Timer(40, this);
		timer.start();
		
		this.setFocusable(true);
		this.addKeyListener(this);
	}

	public MySnake() {
		SnakeVector2D pos = new SnakeVector2D(100, 100, SnakeVector2D.RIGHT);
		init(pos);
	}

	public MySnake(SnakeVector2D pos) {
		init(pos);
	}

	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(25, 75, 850, 600);
		drawSnake(this, g);
	}

	// 增加一节身体
	public void addBody(SnakeVector2D pos) {
		bodyList.insertElementAt(pos, 0);
		Bodylength = bodyList.size();

		SnakeVector2D tmpSV;
		for (int i = Bodylength - 1; i > 0; i--) {
			tmpSV = (SnakeVector2D) bodyList.elementAt(i - 1);
			bodyList.setElementAt(tmpSV, i);
		}
	}

	// 判断是否吃到自己，吃到返回true，没有就返回false
	public boolean eatSlef() {
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		SnakeVector2D body;
		for (int i = 1; i < Bodylength; i++) {
			body = (SnakeVector2D) bodyList.elementAt(i);
			if (head.x == body.x && head.y == body.y) {
				return true;
			}
		}
		return false;
	}

	// 判断是否吃到食物，吃到返回true，没有就返回false
	public boolean eatFood(SnakeVector2D food) {
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		if (head.x == food.x && head.y == food.y) {
			return true;
		}
		return false;
	}

	// 蛇身平滑移动
	public void moveSnake() {
		SnakeVector2D unit;
		for (int i = 0; i < Bodylength; i++) {
			unit = (SnakeVector2D) bodyList.elementAt(i);
			switch (unit.d) {
			case SnakeVector2D.UP:
				unit.y -= 5;
				break;
			case SnakeVector2D.RIGHT:
				unit.x += 5;
				break;
			case SnakeVector2D.DOWN:
				unit.y += 5;
				break;
			case SnakeVector2D.LEFT:
				unit.x -= 5;
				break;
			default:
				break;
			}
			// bodyList.setElementAt(unit, i);
		}
	}

	public void drawSnake(JPanel sp, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		SnakeVector2D bodyV;
		// Image img = up.getImage();
		// g2.rotate(Math.PI * head.d / 2);
		switch (head.d) {
		case SnakeVector2D.UP:
			up.paintIcon(sp, g2, head.x, head.y);
			break;
		case SnakeVector2D.RIGHT:
			right.paintIcon(sp, g2, head.x, head.y);
			break;
		case SnakeVector2D.DOWN:
			down.paintIcon(sp, g2, head.x, head.y);
			break;
		case SnakeVector2D.LEFT:
			left.paintIcon(sp, g2, head.x, head.y);
			break;
		default:
			break;
		}
		for (int i = 1; i < Bodylength; i++) {
			bodyV = (SnakeVector2D) bodyList.get(i);
			// System.out.println(i+" : "+bodyV.x);
			body.paintIcon(sp, g2, bodyV.x, bodyV.y);
		}
		// g2.drawImage(img, head.x, head.y, ImageObserver.FRAMEBITS);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (isStarted) {
			moveSnake();
			moveStep++;
			if (moveStep > 4) {
				moveStep = 0;
				// ���ƶ���һ���
				for (int i = Bodylength - 1; i > 0; i--) {
					SnakeVector2D tail = (SnakeVector2D) bodyList.elementAt(i);
					SnakeVector2D prv = (SnakeVector2D) bodyList
							.elementAt(i - 1);
					tail.d = prv.d;
				}
				SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
				if (head.x >= 850 || head.x <= 25 || head.y >= 650
						|| head.y <= 75) {
					isFailed = true;
					isStarted = false;
				}
				if (keyPress != 0) {
					// System.out.println("act : " + keyPress);
					switch (keyPress) {
					case KeyEvent.VK_UP:
						if (head.d != SnakeVector2D.DOWN)
							head.d = SnakeVector2D.UP;
						break;
					case KeyEvent.VK_RIGHT:
						if (head.d != SnakeVector2D.LEFT)
							head.d = SnakeVector2D.RIGHT;
						break;
					case KeyEvent.VK_DOWN:
						if (head.d != SnakeVector2D.UP)
							head.d = SnakeVector2D.DOWN;
						break;
					case KeyEvent.VK_LEFT:
						if (head.d != SnakeVector2D.RIGHT)
							head.d = SnakeVector2D.LEFT;
						break;
					default:
						break;
					}
					// bodyList.setElementAt(head, 0);
					keyPress = 0;
				}
			}
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		keyPress = e.getKeyCode();
		if (keyPress == KeyEvent.VK_SPACE) {
			isStarted = !isStarted;
			keyPress = 0;
			repaint();
		}
//		System.out.println("key : " + keyPress);
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
