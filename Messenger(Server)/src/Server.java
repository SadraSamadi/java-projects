import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Server extends Thread {

	private ServerSocket serverSocket;
	private int port = 6543;
	private static Map<String, Joiner> joiners = new HashMap<String, Joiner>();

	private JPanel panel;
	private JFrame frame;
	public static TextArea textArea;
	private JButton btn;

	public Server() {
		init();
		try {
			serverSocket = new ServerSocket(port);
			textArea.append(new Date().toString() + "\n");
			textArea.append("Server started --> \\"
					+ InetAddress.getLocalHost().getHostAddress() + ":" + port
					+ "\n");
			start();
		} catch (IOException e) {
			textArea.append(e.toString());
		}
	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		textArea = new TextArea();
		textArea.setEditable(false);
		panel.add(textArea, BorderLayout.CENTER);

		btn = new JButton("Save Log");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				saveLog();
			}
		});
		panel.add(btn, BorderLayout.SOUTH);

		frame = new JFrame("Messenger_Server");
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.setVisible(true);
	}

	protected void saveLog() {
		try {
			File log = new File("log.txt");
			log.setWritable(true);
			FileWriter fileWriter = new FileWriter(log);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(textArea.getText());
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			textArea.append(e.toString());
		} catch (IOException e) {
			textArea.append(e.toString());
		}
	}

	public static void sentTo(String text, String sender) {
		String[] temp = text.split("#");
		if (temp.length > 1) {
			String message = temp[0];
			String contacts = temp[1];
			String[] usernames = contacts.split(",");
			for (String username : usernames) {
				if (joiners.containsKey(username)) {
					joiners.get(username).send(message);
					textArea.append(message + " --> " + username + "\n");
				} else {
					joiners.get(sender).send(
							"Server: \"" + username + "\" not exist !");
					textArea.append("Server: \"" + username
							+ "\" not exist ! --> " + sender + "\n");
				}
			}
		}
	}

	public static void removeJoiner(String username) {
		joiners.remove(username);
		textArea.append(username + " removed ...\n");
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket client = serverSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				String username = br.readLine();
				joiners.put(username, new Joiner(client, username));
				textArea.append(username + " joined ...\n");
			} catch (IOException e) {
				textArea.append(e.toString());
			}
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}