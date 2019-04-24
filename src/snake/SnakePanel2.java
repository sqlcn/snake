package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SnakePanel2 extends JPanel implements KeyListener, ActionListener {

	Image imgTitle, imgUp, imgDown, imgLeft, imgRight, imgBody, imgFood;
	AudioStream asBG;

	final int keyBufLen = 3;
	int keyBuf[] = new int[keyBufLen];
	int keyPh, keyPt;

	boolean isStarted = false;
	boolean isFailed = false;

	SndPlay playBG, playSnd1, playSnd2;

	public static boolean musicStatus = false;
	public static boolean soundStatus = true;

	final static int width = 34;
	final static int height = 24;
	SnakeBase snake;
	int[][] map;

	Timer timer;

	private int score;
	private int ticker_step;

	public SnakePanel2() {
		timer = new Timer(30, this);
		setup();
		init();
	}

	private void setup() {
		try {
			imgTitle = ImageIO.read(new File("resources/title.jpg"));
			imgUp = ImageIO.read(new File("resources/up.png"));
			imgDown = ImageIO.read(new File("resources/down.png"));
			imgLeft = ImageIO.read(new File("resources/left.png"));
			imgRight = ImageIO.read(new File("resources/right.png"));
			imgBody = ImageIO.read(new File("resources/body.png"));
			imgFood = ImageIO.read(new File("resources/food.png"));

			// AudioFormat audioFormat = null;
			// SourceDataLine sourceDataLine = null;
			// AudioInputStream audioInputStream = null;
			// try {
			// audioInputStream = AudioSystem.getAudioInputStream(new File(
			// "U:/AndroidWorkSpace/snake/bg1.wav"));
			// audioFormat = audioInputStream.getFormat();
			// // 转换MP3文件编码
			// if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
			// {
			// audioFormat = new AudioFormat(
			// AudioFormat.Encoding.PCM_SIGNED,
			// audioFormat.getSampleRate(), 16,
			// audioFormat.getChannels(),
			// audioFormat.getChannels() * 2,
			// audioFormat.getSampleRate(), false);
			// audioInputStream = AudioSystem.getAudioInputStream(
			// audioFormat, audioInputStream);
			// }
			// } catch (UnsupportedAudioFileException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// // 打开输出设备
			// DataLine.Info dataLineInfo = new DataLine.Info(
			// SourceDataLine.class, audioFormat,
			// AudioSystem.NOT_SPECIFIED);
			// try {
			// sourceDataLine = (SourceDataLine) AudioSystem
			// .getLine(dataLineInfo);
			// sourceDataLine.open(audioFormat);
			// sourceDataLine.start();
			// // if (sourceDataLine == null) {
			// // sourceDataLine.open(audioFormat);
			// // sourceDataLine.start();
			// // } else {
			// // sourceDataLine.stop();
			// //
			// // sourceDataLine.drain();
			// // sourceDataLine.open(audioFormat);
			// // sourceDataLine.start();
			// // }
			// } catch (LineUnavailableException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// PlayThread playThread = new PlayThread(audioInputStream,
			// sourceDataLine);
			// Thread thread = new Thread(playThread);
			//
			// thread.start();
			//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playBG = new SndPlay(new File("resources/bg1.mp3"));
		playSnd1 = new SndPlay(new File("resources/snd1.wav"));
		playSnd2 = new SndPlay(new File("resources/snd2.mp3"));

		this.setFocusable(true);
		this.addKeyListener(this);
	}

	private void init() {
		// 初始化键盘和缓冲区指针；
		snake = new SnakeBase(width, height);
		map = snake.getSnake();
		keyPh = 0;
		keyPt = 0;
		score = 0;
		ticker_step = 0;
		playBG.play();
		// timer.start();
		// AudioPlayer.player.start(asBG);
	}

	public void turnMusic(boolean sw) {
		if(sw){	//turn on music
			if(!playBG.isPlaying){
				playBG.play();
			}
		}else{
			playBG.close();
		}

	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Image imageBuf = createImage(850, 600);

		g.drawImage(imgTitle, 25, 15, null);
		Graphics g2d = (Graphics2D) imageBuf.getGraphics();
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, 850, 600);

		map = snake.getSnake();
		Image head;
		for (int x = 0; x < snake.width; x++) {
			for (int y = 0; y < snake.height; y++) {
				int e = map[x][y];
				int dx = 0, dy = 0;
				if (e > SnakeVector2D.HEAD) {
					int dir = e - SnakeVector2D.HEAD;
					switch (dir) {
					case SnakeVector2D.UP:
						head = imgUp;
						dy = -5 * ticker_step;
						break;
					case SnakeVector2D.DOWN:
						head = imgDown;
						dy = 5 * ticker_step;
						break;
					case SnakeVector2D.LEFT:
						head = imgLeft;
						dx = -5 * ticker_step;
						break;
					default:
						head = imgRight;
						dx = 5 * ticker_step;
						break;
					}
					// System.out.println(dy);
					g2d.drawImage(head, x * 25 + dx, y * 25 + dy, null);
				} else if (e == SnakeVector2D.FOOD) {
					g2d.drawImage(imgFood, x * 25 + dx, y * 25 + dy, null);
				} else if (e != SnakeVector2D.NONE) {
					switch (e) {
					case SnakeVector2D.UP:
						dy = -5 * ticker_step;
						break;
					case SnakeVector2D.DOWN:
						dy = 5 * ticker_step;
						break;
					case SnakeVector2D.LEFT:
						dx = -5 * ticker_step;
						break;
					default:
						dx = 5 * ticker_step;
						break;
					}
					g2d.drawImage(imgBody, x * 25 + dx, y * 25 + dy, null);
				}
			}
		}
		g.drawImage(imageBuf, 25, 75, null);
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
		g.drawString("Length:" + snake.length, 750, 50);
	}

	public void actionPerformed(ActionEvent e) {
		if (isStarted) {
			// System.out.println("Act");
			if (ticker_step == 4) {
				System.out.println(" ");
				if (!snake.move(true) || snake.eatSelf()) {
					isStarted = false;
					isFailed = true;
				} else if (snake.eatFood()) {
					score += 10;
					playSnd2.play();
					snake.addFood();
				}
				keyTest();
			}
			repaint();
			if (++ticker_step >= 5) {
				ticker_step = 0;
			}
		}
	}

	private void keyTest() {
		if (keyPh != keyPt) {
			int key = keyBuf[keyPh];
			if (++keyPh >= keyBufLen)
				keyPh = 0;
			if (key == KeyEvent.VK_UP)
				snake.turn(SnakeVector2D.UP);
			else if (key == KeyEvent.VK_DOWN)
				snake.turn(SnakeVector2D.DOWN);
			else if (key == KeyEvent.VK_LEFT)
				snake.turn(SnakeVector2D.LEFT);
			else if (key == KeyEvent.VK_RIGHT)
				snake.turn(SnakeVector2D.RIGHT);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			isStarted = !isStarted;
			if (isStarted) {
				timer.start();
			}

			if (isFailed) {
				init();
				isFailed = false;
			}
			return;
		}
		keyBuf[keyPt] = keyCode;
		if (++keyPt >= keyBufLen)
			keyPt = 0;
	}

	public void keyReleased(KeyEvent e) {
	}

}
