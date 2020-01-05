import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Client extends Thread implements ActionListener {

	private Socket socket;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	private String username;

	private JPanel panel;
	private JFrame frame;
	private JTextField textField;
	private JTextField contacts;
	private JButton button;
	private TextArea textArea;
	private JLabel label;

	public Client() {
		init();
		try {
			socket = new Socket("localhost", 6543);
			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(username);
			printWriter.flush();
			start();
		} catch (UnknownHostException e) {
			textArea.append(e.toString());
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
		username = JOptionPane.showInputDialog("Please enter an user name :");

		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(400, 440));

		textField = new JTextField();
		textField.setBounds(10, 10, 300, 30);
		panel.add(textField);

		label = new JLabel("Contacts: ");
		label.setBounds(10, 400, 60, 30);
		panel.add(label);

		contacts = new JTextField();
		contacts.setBounds(80, 400, 310, 30);
		panel.add(contacts);

		button = new JButton("Send");
		button.setBounds(320, 10, 70, 30);
		button.addActionListener(this);
		panel.add(button);

		textArea = new TextArea();
		textArea.setBounds(10, 50, 380, 340);
		textArea.setEditable(false);
		panel.add(textArea);

		frame = new JFrame("Messenger_Client (" + username + ")");
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WA());
		frame.setFocusable(true);
		frame.setVisible(true);
	}

	public void send(String text) {
		printWriter.println(text + "#" + contacts.getText());
		printWriter.flush();
	}

	public String receive() throws IOException {
		return bufferedReader.readLine();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		send(textField.getText());
		textArea.append(username + ": " + textField.getText() + "\n");
		textField.setText("");
	}

	@Override
	public void run() {
		while (true) {
			try {
				textArea.append(receive() + "\n");
			} catch (IOException e) {
				textArea.append(e.toString());
			}
		}
	}

	private class WA extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent paramWindowEvent) {
			try {
				printWriter.println("exit");
				printWriter.flush();
				printWriter.close();
			} catch (NullPointerException e) {
				textArea.append(e.toString());
			}
		}

	}

	public static void main(String[] args) {
		new Client();
	}

}