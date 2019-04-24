package snake;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SndPlay {

	Player player;
	byte[] buffer;
	boolean isPlaying = false;

	// 构造方法
	public SndPlay(File file) {
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			buffer = new byte[(int) file.length()];
			try {
				inStream.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			if (inStream != null)
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	// 播放方法
	public void play() {
		ByteArrayInputStream bytesStream = null;
		bytesStream = new ByteArrayInputStream(buffer);
		try {
			close();
			player = new Player(bytesStream);
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread playThread = new Thread(new Runnable() {
			public void run() {
				try {
					isPlaying = true;
					player.play();
					isPlaying = false;
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// playThread.setDaemon(true);
		playThread.start();
	}

	public void close() {
		if (player != null) {
			isPlaying = false;
			player.close();// 先把之前的实例释放
			player = null;
		}
	}
}
