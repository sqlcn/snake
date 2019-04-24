package snake;

// 2D×ø±êÀà

public class SnakeVector2D {
	public static final int NONE = 0;
	 public static final int UP = 1;
	 public static final int RIGHT = 2;
	 public static final int DOWN = 3;
	 public static final int LEFT = 4;
	 public static final int FOOD = 5;
	 public static final int HEAD = 10;

	public int x, y, d;

	public SnakeVector2D(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.d = d;
	}

}
