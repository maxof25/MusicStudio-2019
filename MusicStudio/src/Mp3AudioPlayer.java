import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.player.advanced.*;

public class Mp3AudioPlayer implements AudioPlayer {

	AdvancedPlayer playMP3;
	private int pausedOnFrame = 0;
	
	public Mp3AudioPlayer() {
	}
	
	public void play(String filePath) throws UnsupportedAudioFileException,IOException, LineUnavailableException{
		try {
	
			FileInputStream fis = new FileInputStream(filePath);
			playMP3 = new AdvancedPlayer(fis);
			
			playMP3.setPlayBackListener(new PlaybackListener() {
				@Override
				public void playbackFinished(PlaybackEvent event) {
					pausedOnFrame = event.getFrame();	
				}
			});
			playMP3.play();
		}
		catch (Exception ex) {
			
		}
	}
	
	public void resume()
	{
		try {
			if (playMP3 != null)
				playMP3.play();
		}
		catch(Exception ex) {
			
		}
	}
	
	public void stop() {
		if (playMP3 != null)
			playMP3.close();
	}
}
