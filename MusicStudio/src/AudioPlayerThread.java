import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class AudioPlayerThread implements Runnable {

	private AudioPlayer ap;
	public boolean isPlaying = false;
	
	public AudioPlayerThread(AudioPlayer ap) {
		this.ap = ap;
	}
	
	public void run() {
		
		try {
			//ap.play();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
