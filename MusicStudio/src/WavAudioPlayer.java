
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * A simple utility class that plays back a sound file.
 * @author www.codejava.net
 *
 */
public class WavAudioPlayer implements LineListener, AudioPlayer {
	
	String filePath;
	/**
	 * this flag indicates whether the playback completes or not.
	 */
	boolean playCompleted;
	
	/**
	 * this flag indicates whether the playback is stopped or not.
	 */
	boolean isStopped;
	
	public WavAudioPlayer() {
	}

	/**
	 * Play a given audio file.
	 * 
	 * @param audioFilePath
	 *            Path of the audio file.
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public void play(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File audioFile = new File(filePath);

		AudioInputStream audioStream = AudioSystem
				.getAudioInputStream(audioFile);

		AudioFormat format = audioStream.getFormat();

		DataLine.Info info = new DataLine.Info(Clip.class, format);

		Clip audioClip = (Clip) AudioSystem.getLine(info);

		audioClip.addLineListener(this);

		audioClip.open(audioStream);

		audioClip.start();
		
		playCompleted = false;
		
		while (!playCompleted) {
			// wait for the playback completes
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				if (isStopped) {
					audioClip.stop();
					break;
				}
			}
		}

		audioClip.close();

	}

	/**
	 * Stop playing back.
	 */
	public void stop() {
		isStopped = true;
	}
	
	/**
	 * Listens to the audio line events to know when the playback completes.
	 */
	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if (type == LineEvent.Type.STOP) {
			playCompleted = true;
		}
	}
}