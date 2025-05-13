import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import javazoom.jl.player.*;
import javazoom.jl.player.advanced.*;

public class AudioPlayerWindow1 extends JFrame {

	private JFrame frame;
	
	private JList      lstPlaylist; 
	private JComboBox  cbPlayListNames;
	private JButton    btnPlay;
	
	private LinkedList<String> playLists;
    private LinkedList<String> playListSongs;
    
    private int songPlayed;
	private Thread playbackThread;
	private Mp3AudioPlayer player = new Mp3AudioPlayer();
	private boolean isPlaying = false;
	private boolean isPaused = false;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AudioPlayerWindow1 window = new AudioPlayerWindow1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AudioPlayerWindow1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 381, 496);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("Player");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JSlider VolumeSlider = new JSlider();
		VolumeSlider.setBounds(10, 420, 345, 26);
		panel.add(VolumeSlider);
		
		lstPlaylist = new JList();
		lstPlaylist.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		lstPlaylist.setBounds(35, 76, 294, 209);
		panel.add(lstPlaylist);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(257, 296, 98, 25);
		panel.add(btnNext);
		btnNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.setBounds(10, 296, 97, 25);
		panel.add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				play();
			}
		});
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(117, 296, 130, 25);
		panel.add(btnPlay);
		btnPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				if (!isPlaying)
					play();
			    else if (isPlaying && isPaused) 
			    	resume();
				else
					pause();
			}
		});
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/home/max/eclipse-workspace/MusicMaster/images4.png"));
		label.setBounds(129, 76, 255, 245);
		panel.add(label);
		
		
		String filePath = FileUtils.PLAYLIST_FILE; 
		playLists = FileUtils.ReadListFromFile(filePath);
		cbPlayListNames = new JComboBox(playLists.toArray());
		cbPlayListNames.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cbPlayListNames.setBounds(35, 28, 288, 25);
		panel.add(cbPlayListNames);
		
		
		cbPlayListNames.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String filePath = cbPlayListNames.getSelectedItem().toString();
				displayPlayList(filePath);
			}
		});
		
		
	     cbPlayListNames.setSelectedIndex(0);
	     
	     JLabel label_1 = new JLabel("");
	     label_1.setIcon(new ImageIcon("C:\\CodeRepository\\MusicStudio\\wallpaper10.jpg"));
	     label_1.setBounds(0, -37, 365, 531);
	     panel.add(label_1);
	}

	private void play() {
		if (lstPlaylist.getSelectedIndex() < 0) return;
		try{
			playbackThread = new Thread(new Runnable() {
				@Override
				public void run() {
					
					try{
						
						isPlaying = true;
						isPaused = false;
						btnPlay.setText("Pause");
						
						String filepath = lstPlaylist.getSelectedValue().toString();
						FileInputStream fis = new FileInputStream(filepath);
						
						player.play(filepath);
						
						isPlaying = false;
						btnPlay.setText("Play");
						///timer.reset();
						
						
					}
					catch(Exception exc){
					    exc.printStackTrace();
					}
				}});
			
			playbackThread.start();
					
		}
		catch(Exception exc){
		    exc.printStackTrace();
		    System.out.println("Failed to play the file.");
		    JOptionPane.showMessageDialog(null, "ERROR");
		}
	}		
	
	private void pause() {
		player.stop();
		btnPlay.setText("Pay");
		isPaused = true;
	}
	
	private void resume() {
		player.resume();
		btnPlay.setText("Pause");
		isPaused= false;
	}
	public void stop() {
		player.stop();
		playbackThread.interrupt();
		btnPlay.setText("Play");
		isPlaying = false;
	}
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
	
	private void displayPlayList(String filePath) 
	{
		 
		playListSongs = FileUtils.ReadListFromFile(filePath);
			
		lstPlaylist.setListData(playListSongs.toArray());	
		 
	}
}
