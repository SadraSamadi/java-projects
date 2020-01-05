import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Server extends Thread {

	private final int PORT = 12345;

	private ServerSocket serverSocket;
	private JFrame frame;
	private JPanel panel;
	private static TextArea textArea;
	private JButton button;
	public static File logo;
	private static HashMap<String, Joiner> joiners = new HashMap<String, Joiner>();

	public Server() {
		init();
		try {
			serverSocket = new ServerSocket(PORT);
			log(new Date().toString());
			log("Server Started on Adress : "
					+ InetAddress.getLocalHost().getHostAddress() + ":" + PORT);
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		logo = new File("res\\java_logo.jpg");
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		textArea = new TextArea();
		textArea.setEditable(false);
		panel.add(textArea, BorderLayout.CENTER);
		button = new JButton("Save Log");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		panel.add(button, BorderLayout.SOUTH);
		frame = new JFrame();
		frame.setTitle("Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
//		JFileChooser fileChooser = new JFileChooser("Sadra ...");
//		fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
//		fileChooser.showOpenDialog(null);
//		File file = fileChooser.getSelectedFile();
		
		
//		FileDialog open = new FileDialog(new JFrame(), "Open", FileDialog.LOAD);
//		FileDialog save = new FileDialog(new JFrame(), "Save", FileDialog.SAVE);
//		open.setVisible(true);
//		String name = open.getFile();
//		save.setFile(name);
//		save.setDirectory(name);
//		save.setVisible(true);
		
//		if (name != null) {
//			File file = new File(name);
//			if (file.exists())
//				System.out.println(file.getAbsolutePath());
//		}
		
//		FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
//		fd.setDirectory("C:\\");
//		fd.setFile("*.xml");
//		fd.setVisible(true);
//		String filename = fd.getFile();
//		if (filename == null)
//		  System.out.println("You cancelled the choice");
//		else
//		  System.out.println("You chose " + filename);
	}

	public static void log(String text) {
		textArea.append(text + "\n");
	}

	public static void remove(String username) {
		joiners.remove(username);
		log("\"" + username + "\" removed from server ...");
	}

	public static TopPlayers getTopPlayers() {
		ArrayList<Joiner> temp = new ArrayList<Joiner>();
		for (Joiner joiner : joiners.values())
			if (joiner.hasScore())
				temp.add(joiner);
		temp.sort(new Comparator<Joiner>() {

			@Override
			public int compare(Joiner joiner1, Joiner joiner2) {
				return joiner2.getScore() - joiner1.getScore();
			}
		});
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			if (i < temp.size()) {
				names.add(temp.get(i).getUsername());
				scores.add(temp.get(i).getScore());
			} else {
				names.add("");
			}
		}
		TopPlayers topPlayers = new TopPlayers();
		topPlayers.setNames(names);
		topPlayers.setScores(scores);
		return topPlayers;
	}
	
	private boolean usernameExcist(String username) {
		for (String name : new ArrayList<String>(joiners.keySet()))
			if (username.equals(name))
				return true;
		return false;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket newClient = serverSocket.accept();
				DataInputStream dataInputStream = new DataInputStream(
						new BufferedInputStream(newClient.getInputStream()));
				DataOutputStream dataOutputStream = new DataOutputStream(
						new BufferedOutputStream(newClient.getOutputStream()));
				String username = dataInputStream.readUTF();
				while (usernameExcist(username)) {
					dataOutputStream.writeUTF("#UsernameExist#");
					dataOutputStream.flush();
					username = dataInputStream.readUTF();
				}
				dataOutputStream.writeUTF("#UsernameNotExist#");
				dataOutputStream.flush();
				joiners.put(username, new Joiner(newClient, username,
						dataInputStream, dataOutputStream));
				log("\"" + username + "\" added to server ...");
			} catch (IOException e) {
				log("Error: Server couldn't add joiner !!!");
			}
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}
