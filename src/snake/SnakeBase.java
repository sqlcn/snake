package snake;

import java.util.Random;
import java.util.Vector;

public class SnakeBase {

	// public static final int UP = 2001;
	// public static final int RIGHT = 2002;
	// public static final int DOWN = 2003;
	// public static final int LEFT = 2004;
	// public static final int FOOD = 105;
	// public static final int HEAD = 10000;
	static final int START_LEN = 3;

	int map[][];
	int width, height;
	int length;
	SnakeVector2D food = new SnakeVector2D(0, 0, 0);
	Vector bodyList = new Vector();

	SnakeBase() {
		this(30, 20);
	}

	SnakeBase(int width, int height) {
		if (width < 10)
			width = 10;
		if (height < 10)
			height = 10;
		map = new int[width][height];
		this.width = width;
		this.height = height;
		init();
	}

	public void init() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = 0;
			}
		}
		bodyList.removeAllElements();
		SnakeVector2D head = new SnakeVector2D(width / 2, height / 2,
				SnakeVector2D.RIGHT);
		bodyList.addElement(head);
		for(int i=1;i<SnakeBase.START_LEN;i++){
			bodyList.addElement(new SnakeVector2D(head.x,head.y,head.d));
		}
		length = bodyList.size();
		addFood();
	}

	public boolean addFood() {
		boolean result = false;
		int fx, fy;
		Random rand = new Random();
		for (int i = 0; i < width * height - length; i++) {
			fx = rand.nextInt(width);
			fy = rand.nextInt(height);
			// 遍历整条蛇身，看食物是否出现在身体上
			int j;
			for (j = 0; j < length; j++) {
				SnakeVector2D temp = (SnakeVector2D) bodyList.elementAt(j);
				if (fx == temp.x && fy == temp.y) {
					break;
				}
			}
			if (j >= length) {
				food.x = fx;
				food.y = fy;
				food.d = SnakeVector2D.NONE;
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean eatFood() {
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		if (head.x == food.x && head.y == food.y) {
			SnakeVector2D tail = (SnakeVector2D) bodyList.elementAt(length - 1);
			SnakeVector2D newTail = new SnakeVector2D(tail.x, tail.y, tail.d);
			bodyList.addElement(newTail);
			length = bodyList.size();
//			System.out.println(length);
			return true;
		}
		return false;
	}

	public boolean eatSelf() {
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		for (int i = 1; i < length; i++) {
			SnakeVector2D body = (SnakeVector2D) bodyList.elementAt(i);
			if (body.x == head.x && body.y == head.y) {
				return true;
			}
		}
		return false;
	}

	public boolean move(boolean type) {
		for (int i = length - 1; i > 0; i--) {
			SnakeVector2D s1 = (SnakeVector2D) bodyList.elementAt(i);
			SnakeVector2D s2 = (SnakeVector2D) bodyList.elementAt(i - 1);
			s1.x = s2.x;
			s1.y = s2.y;
			s1.d = s2.d;
		}

		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		int dir = head.d;// -SnakeVector2D.HEAD;
//		System.out.println(dir);
		if (dir == SnakeVector2D.UP) {
			head.y--;
			if (head.y < 0) {
				// 为真表示可穿过屏幕循环
				if (type)
					head.y = height - 1;
				else
					return false;
			}
		} else if (dir == SnakeVector2D.DOWN) {
			head.y++;
			if (head.y >= height) {
				// 为真表示可穿过屏幕循环
				if (type)
					head.y = 0;
				else
					return false;
			}
		} else if (dir == SnakeVector2D.LEFT) {
			head.x--;
			if (head.x < 0) {
				// 为真表示可穿过屏幕循环
				if (type)
					head.x = width - 1;
				else
					return false;
			}
		} else if (dir == SnakeVector2D.RIGHT) {
			head.x++;
			if (head.x >= width) {
				// 为真表示可穿过屏幕循环
				if (type)
					head.x = 0;
				else
					return false;
			}
		}

		// bodyList.setElementAt(head, 0);
		// System.out.println(head.x+","+head.y);
		return true;
	}

	public int[][] getSnake() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = SnakeVector2D.NONE;
			}
		}
		map[food.x][food.y] = SnakeVector2D.FOOD;
		for (int i = 1; i < length; i++) {
			SnakeVector2D body = (SnakeVector2D) bodyList.elementAt(i);
			map[body.x][body.y] = body.d;
		}
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		map[head.x][head.y] = head.d + SnakeVector2D.HEAD;
		return map;
	}

	public void turn(int dir) {
		SnakeVector2D head = (SnakeVector2D) bodyList.elementAt(0);
		if (dir == SnakeVector2D.UP && head.d != SnakeVector2D.DOWN)
			head.d = SnakeVector2D.UP;
		else if (dir == SnakeVector2D.DOWN && head.d != SnakeVector2D.UP)
			head.d = SnakeVector2D.DOWN;
		else if (dir == SnakeVector2D.LEFT && head.d != SnakeVector2D.RIGHT)
			head.d = SnakeVector2D.LEFT;
		else if (dir == SnakeVector2D.RIGHT && head.d != SnakeVector2D.LEFT)
			head.d = SnakeVector2D.RIGHT;
	}
}
