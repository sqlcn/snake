package snake;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;

public class Snake {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 获取屏幕分辨率
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		// System.out.println ("Screen size : " + scrnsize.width + " * " +
		// scrnsize.height);

		mainFrame frame = new mainFrame();
		SnakePanel2 panel = new SnakePanel2();

		frame.setBounds((scrnsize.width - 900) / 2,
				(scrnsize.height - 740) / 2, 900, 740);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("贪吃蛇");

		frame.setMenu();
		// SnakePanel panel = new SnakePanel();
		// MySnake panel = new MySnake();
		frame.setContentPane(panel);

		Image img = toolkit.getImage("resources/right.png");
		frame.setIconImage(img);
		frame.setVisible(true);

	}

}

class mainFrame extends JFrame implements ActionListener {
	MenuItem musicMenu = new MenuItem("");
	MenuItem soundMenu = new MenuItem("");
	SnakePanel2 panel;
//	Music music;

	public void setMenu() {
//		this.panel = panel;
//		music = new Music();
		if (SnakePanel2.musicStatus) {
			musicMenu.setLabel("Music on");
//			music.loadMusic();
//			panel.turnMusic(true);
		} else {
			musicMenu.setLabel("Music off");
//			panel.turnMusic(false);
		}
		if (SnakePanel2.soundStatus) {
			soundMenu.setLabel("Sound on");
		} else {
			soundMenu.setLabel("Sound off");
		}
		// Container cont=frame.getContentPane();
		// cont.setLayout(null);
		// cont.setBounds(200, 0, 200, 25);
		// cont.setBackground(Color.black);
		Menu option = new Menu("Setting");
		option.add(musicMenu);
		option.add(soundMenu);
		musicMenu.addActionListener(this);
		soundMenu.addActionListener(this);
		MenuBar bar = new MenuBar();
		bar.add(option);
		setMenuBar(bar);


	}

	public void actionPerformed(ActionEvent e) {
		// System.out.println(e.getSource());
		if (e.getSource() == musicMenu) {
			if (SnakePanel2.musicStatus) {
				SnakePanel2.musicStatus = false;
				musicMenu.setLabel("Music off");
//				panel.turnMusic(false);
//				music.stop();
			} else {
				SnakePanel2.musicStatus = true;
				musicMenu.setLabel("Music on");
//				panel.turnMusic(true);
//				music.loadMusic();
			}
		} else if (e.getSource() == soundMenu) {
			if (SnakePanel2.soundStatus) {
				SnakePanel2.soundStatus = false;
				soundMenu.setLabel("Sound off");
			} else {
				SnakePanel2.soundStatus = true;
				soundMenu.setLabel("Sound on");
			}
		}
	}
}

class Music {
	String path = new String("resources/");
	String file = new String("nor.mid");
	Sequence seq;
	Sequencer midi;
	boolean sign;

	void loadMusic() {
		try {
			seq = MidiSystem.getSequence(new File(path + file));
			midi = MidiSystem.getSequencer();
			midi.open();
			midi.setSequence(seq);
			midi.start();
			midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		sign = true;
	}

	void stop() {
		midi.stop();
		midi.close();
		sign = false;
	}

	boolean isplay() {
		return sign;
	}

	void setMusic(String e) {
		file = e;
	}
}
