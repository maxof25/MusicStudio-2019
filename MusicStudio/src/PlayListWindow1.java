import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Font;

public class PlayListWindow1 extends JFrame {

	private JFrame frame;

	private JPanel     contentPane;
	private JTextField txtNewListName;
	private JList      lstPlaylist; 
	private JComboBox  cbPlayListNames;
	
	
	private LinkedList<String> playLists;
    private LinkedList<String> playListSongs;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayListWindow1 frame = new PlayListWindow1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

   
	private void displayPlayList(String filePath) 
	{
		 
		playListSongs = FileUtils.ReadListFromFile(filePath);
			
		lstPlaylist.setListData(playListSongs.toArray());	
		 
	}
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public PlayListWindow1() throws IOException {
		setTitle("PlayList Editor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 724, 454);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		DefaultMutableTreeNode root;
		File fileRoot = new File(FileUtils.FOLDER_ROOT);
		root = new DefaultMutableTreeNode(new FileNode(fileRoot));
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
			 
		
		/************************************************************************************
		 *  BUTTON SAVE
		 ************************************************************************************/
		
		JButton btnSearchBy = new JButton("Search By...");
		btnSearchBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser j = new JFileChooser();
				j.showSaveDialog(null);
			}
		});
		btnSearchBy.setBounds(54, 361, 99, 23);
		panel.add(btnSearchBy);
		JButton btnSave = new JButton("Save");
		btnSave.setBackground(Color.CYAN);
		btnSave.setBounds(201, 361, 99, 23); 
		panel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String filePath = cbPlayListNames.getSelectedItem().toString();
				 
				if (!txtNewListName.getText().equals("")) {
					filePath = txtNewListName.getText();
				}
				
				
                FileUtils.SaveListToFile(filePath, playListSongs);
                
				if (!txtNewListName.getText().equals("")) {
					
					playLists.add(txtNewListName.getText());
					// save the file
					FileUtils.SaveListToFile(FileUtils.PLAYLIST_FILE, playLists);
					
					// Update the combo box
					cbPlayListNames.setModel(new DefaultComboBoxModel(playLists.toArray()));
					cbPlayListNames.setSelectedItem(txtNewListName.getText());
					
					txtNewListName.setText("");
					txtNewListName.setEnabled(false);
					
				}
				
			}
		});
		
		/*/************************************************************************************
		 *  BUTTON DELETE
		 ************************************************************************************/
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.MAGENTA);
		btnDelete.setBounds(435, 361, 89, 23);
		panel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filePath = cbPlayListNames.getSelectedItem().toString();
				
				playLists.remove(filePath);
				
				// Save into the file
				FileUtils.SaveListToFile(FileUtils.PLAYLIST_FILE, playLists);
				
				// Update the combo box
				
				cbPlayListNames.setModel(new DefaultComboBoxModel(playLists.toArray()));
				cbPlayListNames.setSelectedIndex(0);
			}
		});
		
		DefaultListModel listModel = new DefaultListModel();
		lstPlaylist = new JList();
		lstPlaylist.setBorder(new LineBorder(new Color(0, 0, 0), 5));
        
		lstPlaylist.setBounds(201, 129, 323, 205);
		panel.add(lstPlaylist);
		
	
		/*/************************************************************************************
		 *  COMBO BOX
		 ************************************************************************************/
		String filePath = FileUtils.PLAYLIST_FILE; 
		playLists = FileUtils.ReadListFromFile(filePath);
			
		cbPlayListNames = new JComboBox(playLists.toArray());
		cbPlayListNames.setFont(new Font("Tahoma", Font.PLAIN, 21));
		cbPlayListNames.setBounds(201, 29, 323, 30);
		panel.add(cbPlayListNames);
		cbPlayListNames.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String filePath = cbPlayListNames.getSelectedItem().toString();
				displayPlayList(filePath);
			}
		});
		

		txtNewListName = new JTextField();
		txtNewListName.setBounds(310, 99, 214, 20);
		panel.add(txtNewListName);
		txtNewListName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(221, 97, 79, 21);
		panel.add(lblName);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBackground(Color.GREEN);
		btnCreate.setBounds(54, 33, 89, 23);
		panel.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    playListSongs = new LinkedList<String>();
			    txtNewListName.setText("NAME ME");
			    lstPlaylist.setListData(new String[]{});			    
			}});
			
		
		JButton btnSongUp = new JButton("\u2191");
		btnSongUp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSongUp.setForeground(Color.RED);
		btnSongUp.setBounds(534, 171, 46, 30);
		panel.add(btnSongUp);
		btnSongUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int selectedIndex = lstPlaylist.getSelectedIndex();
				 
				 if (selectedIndex < 1) return;
				 
				 String item = playListSongs.remove(selectedIndex).toString();
				 playListSongs.add(selectedIndex-1, item);
				 
				 lstPlaylist.setListData(playListSongs.toArray());	
			}
		});
		
		JButton btnSongDown = new JButton("\u2193");
		btnSongDown.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSongDown.setForeground(Color.BLUE);
		btnSongDown.setBounds(534, 212, 46, 30);
		panel.add(btnSongDown);
		btnSongDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int selectedIndex = lstPlaylist.getSelectedIndex();
				 
				 if (selectedIndex < 0 || selectedIndex > playListSongs.size()-2) return;
				 
				 String item = playListSongs.remove(selectedIndex).toString();
				 playListSongs.add(selectedIndex+1, item);
				 
				 lstPlaylist.setListData(playListSongs.toArray());	
			}
		});
		
		JButton btnSongDelete = new JButton("X");
		btnSongDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSongDelete.setBounds(534, 264, 46, 23);
		panel.add(btnSongDelete);
		btnSongDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int selectedIndex = lstPlaylist.getSelectedIndex();
				 
				 if (selectedIndex < 0 || selectedIndex > playListSongs.size()-1) return;
				 
				 playListSongs.remove(selectedIndex);
				 
				 lstPlaylist.setListData(playListSongs.toArray());	
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 90, 169, 244);
		panel.add(scrollPane);
		
		JTree treeFileBrowser = new JTree(treeModel);
		treeFileBrowser.setBorder(new LineBorder(Color.MAGENTA, 5, true));
		scrollPane.setViewportView(treeFileBrowser);
		treeFileBrowser.setShowsRootHandles(true);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/home/max/eclipse-workspace/MusicMaster/new-folder-music.png"));
		label.setBounds(344, -4, 374, 257);
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\CodeRepository\\MusicStudio\\musicicon3.png"));
		label_1.setBounds(516, 204, 409, 235);
		panel.add(label_1);
		CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
        Thread t = new Thread(ccn);
        t.start();
       /* try {
		//	t.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/        
        
        treeFileBrowser.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() ==2 ) {
        			 DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeFileBrowser.getLastSelectedPathComponent();
                      if (node == null) return;
                      
                      FileNode nodeInfo = (FileNode) node.getUserObject();
                      if (nodeInfo.getFile().isFile() && nodeInfo.getFile().getName().contains(".mp3"))
                      {
                    	  playListSongs.add(nodeInfo.getFile().getAbsolutePath());
                    	  lstPlaylist.setListData(playListSongs.toArray());	
                      }
        		}
        	}
        });
        
        cbPlayListNames.setSelectedIndex(0);
        
        JLabel label_2 = new JLabel("");
        label_2.setIcon(new ImageIcon("C:\\CodeRepository\\MusicStudio\\MusicIcon2.jpg"));
        label_2.setBounds(506, -4, 212, 205);
        panel.add(label_2);
        
        JLabel label_3 = new JLabel("");
        label_3.setIcon(new ImageIcon("C:\\CodeRepository\\MusicStudio\\131751-red-and-white-low-poly-abstract-background-design.jpg"));
        label_3.setBounds(0, 1, 481, 424);
        panel.add(label_3);
	}
	}
	