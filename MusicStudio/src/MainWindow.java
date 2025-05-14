import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow{
	
	//System.out.println("Unfortunately, this program no longer works, due to me not finding the JAR for Javazoom")

	private JFrame frmMusicMaster;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMusicMaster.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMusicMaster = new JFrame();
		frmMusicMaster.setTitle("Music Master");
		frmMusicMaster.setBounds(100, 100, 716, 405);
		frmMusicMaster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmMusicMaster.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblMaster = new JLabel("Master");
		lblMaster.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaster.setForeground(Color.RED);
		lblMaster.setFont(new Font("Yu Gothic UI", Font.PLAIN, 30));
		lblMaster.setBounds(350, 24, 340, 65);
		panel.add(lblMaster);
		
		JLabel lblNewLabel = new JLabel("Music");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(12, 24, 340, 65);
		panel.add(lblNewLabel);
		
		JButton btnRecorder = new JButton("Recorder");
		btnRecorder.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
			    new RecorderWindow().show();
			    
			}
			
		});
		btnRecorder.setForeground(Color.WHITE);
		btnRecorder.setBackground(Color.RED);
		btnRecorder.setBounds(440, 100, 123, 39);
		panel.add(btnRecorder);
		
		JButton btnPlayer = new JButton("Player");
		btnPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//new AudioPlayerWindow1().show();
				AudioPlayerWindow1 frame = new AudioPlayerWindow1();
				frame.setVisible(true);

			}
		});
		btnPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AudioPlayerWindow1().show();
			
			
			}
		});
		btnPlayer.setBackground(Color.RED);
		btnPlayer.setForeground(Color.WHITE);
		btnPlayer.setBounds(440, 183, 123, 39);
		panel.add(btnPlayer);
		
		JButton btnPlaylists = new JButton("Playlists");
		btnPlaylists.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")

			public void actionPerformed(ActionEvent arg0) {

						
					try {
						new PlayListWindow1().show();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
					
		});
		btnPlaylists.setForeground(Color.WHITE);
		btnPlaylists.setBackground(Color.RED);
		btnPlaylists.setBounds(440, 256, 123, 39);
		panel.add(btnPlaylists);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Max\\Pictures\\2016-11\\Music-icon.png"));
		label.setBounds(34, 100, 0, 0);
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\CodeRepository\\MusicStudio\\Music-icon.png"));
		label_1.setBounds(-21, -16, 719, 406);
		panel.add(label_1);
	}
}