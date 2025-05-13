import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;

public class RecorderWindow extends JFrame implements ActionListener{

	private SoundRecorder recorder = new SoundRecorder();
	private WavAudioPlayer player = new WavAudioPlayer();
	
	private JPanel contentPane;
	
	private RecordTimer timer;
	private Thread playbackThread;
	
	JButton btnRecordStop;
	JButton btnPauseResume;
	JButton btnSave;
	JButton btnPlay;
	
	JLabel lblRecordTime;
	
	private boolean isPlaying = false;
	private boolean isRecording = false;
	private boolean isPaused = false;


	private String saveFilePath;
	private JLabel label_1;
	private JLabel label;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecorderWindow frame = new RecorderWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Handle click events on the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button == btnRecordStop) {
			if (!isRecording) 
			{
				startRecording();
			} else {
				stopRecording();
			}

		} 
		else if (button == btnPlay) {
			if (!isPlaying) {
				playBack();
			} else {
				stopPlaying();
			}
		}
		else if (button == btnPauseResume) {
			if (isRecording) {
				
				if (!isPaused)
					pauseRecording();
				else
					resumeRecording();
			}
		}
	}

	/**
	 * Start recording sound, the time will count up.
	 */
	private void startRecording() {
		Thread recordThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					isRecording = true;
					btnRecordStop.setText("Stop");
					//btnRecordStop.setIcon(iconStop);
					btnPlay.setEnabled(false);

					recorder.start();

				} 
				catch (LineUnavailableException ex) {
					JOptionPane.showMessageDialog(
							RecorderWindow.this,
							"Error", "Could not start recording sound!",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		recordThread.start();
		timer = new RecordTimer(lblRecordTime);
		timer.start();
	}
	
	private void pauseRecording() {
		isPaused = true;
		try {
			timer.pauseTimer();
			btnPauseResume.setText("Resume");
			//btnRecordStop.setIcon(iconRecord);
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			recorder.pause();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


		} catch (Exception ex) {
			JOptionPane.showMessageDialog(
					RecorderWindow.this, "Error",
					"Error pausing sound recording!",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	private void resumeRecording() {
		isPaused = false;
		try {
			timer.resumeTimer();
			btnPauseResume.setText("Pause");
			//btnRecordStop.setIcon(iconRecord);
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			recorder.resume();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


		} catch (Exception ex) {
			JOptionPane.showMessageDialog(
					RecorderWindow.this, "Error",
					"Error resumeing sound recording!",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	
	private void stopRecording() {
		isRecording = false;
		try {
			timer.cancel();
			btnRecordStop.setText("Record");
			//btnRecordStop.setIcon(iconRecord);
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			recorder.stop();

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			saveFile();

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(
					RecorderWindow.this, "Error",
					"Error stopping sound recording!",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Start playing back the sound.
	 */
	private void playBack() {
		timer = new RecordTimer(lblRecordTime);
		timer.start();
	
		//btnPlay.setIcon(iconStop);
	
		playbackThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					isPlaying = true;
					btnRecordStop.setEnabled(false);
					btnPlay.setText("Stop");
					
					player.play(saveFilePath);
					
					//btnPlay.setIcon(iconPlay);
					isPlaying = false;
					btnPlay.setText("Play");
					btnRecordStop.setEnabled(true);
					timer.reset();
					
				} 
				catch (UnsupportedAudioFileException ex) {
					ex.printStackTrace();
				} 
				catch (LineUnavailableException ex) {
					ex.printStackTrace();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});

		playbackThread.start();
	}

	/**
	 * Stop playing back.
	 */
	private void stopPlaying() {
		timer.reset();
		timer.interrupt();
		player.stop();
		playbackThread.interrupt();
	}

	/**
	 * Save the recorded sound into a WAV file.
	 */
	private void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter wavFilter = new FileFilter() {
			@Override
			public String getDescription() {
				return "Sound file (*.WAV)";
			}

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getName().toLowerCase().endsWith(".wav");
				}
			}
		};

		fileChooser.setFileFilter(wavFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int userChoice = fileChooser.showSaveDialog(this);
		if (userChoice == JFileChooser.APPROVE_OPTION) {
			saveFilePath = fileChooser.getSelectedFile().getAbsolutePath();
			if (!saveFilePath.toLowerCase().endsWith(".wav")) {
				saveFilePath += ".wav";
			}

			File wavFile = new File(saveFilePath);

			try {
				recorder.save(wavFile);

				JOptionPane.showMessageDialog(RecorderWindow.this,
						"Saved recorded sound to:\n" + saveFilePath);

				btnPlay.setEnabled(true);

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(RecorderWindow.this, "Error",
						"Error saving to sound file!",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Create the frame.
	 */
	public RecorderWindow() {
		setBackground(Color.LIGHT_GRAY);
		setTitle("Recorder");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 381, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		
		btnRecordStop = new JButton("Record");
		btnRecordStop.setForeground(Color.RED);
		btnRecordStop.setBounds(135, 186, 102, 42);
		btnPauseResume = new JButton("Pause");
		btnPauseResume.setForeground(Color.MAGENTA);
		btnPauseResume.setBounds(10, 241, 137, 36);
		btnSave = new JButton("Save");
		btnSave.setForeground(Color.GREEN);
		btnSave.setBounds(135, 288, 102, 42);
		btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.CYAN);
		btnPlay.setBounds(208, 241, 137, 36);
		
		lblRecordTime = new JLabel("00:00");
		lblRecordTime.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblRecordTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecordTime.setBounds(20, 61, 335, 112);

		btnRecordStop.addActionListener(this);
		btnPauseResume.addActionListener(this);
		btnPlay.addActionListener(this);
		btnSave.addActionListener(this);
		panel.setLayout(null);
		panel.add(btnRecordStop);
		panel.add(btnPauseResume);
		panel.add(btnPlay);
		panel.add(btnSave);
		panel.add(lblRecordTime);
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("/home/max/eclipse-workspace/MusicMaster/images2.png"));
		label_1.setBounds(532, 152, 180, 225);
		panel.add(label_1);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\CodeRepository\\MusicStudio\\wallpaper9.jpg"));
		label.setBounds(0, 0, 355, 447);
		panel.add(label);
	}
}