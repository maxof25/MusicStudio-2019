import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public interface AudioPlayer 
{
	void play(String path) throws UnsupportedAudioFileException,IOException, LineUnavailableException ;
	//This is a test with new Ubuntu set up
}
