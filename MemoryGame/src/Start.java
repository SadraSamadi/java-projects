import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Start extends JPanel implements ActionListener {

	private final int WIDTH = 400;
	private final int HEIGHT = 300;
	private final Integer[] levels = { 2, 4, 6, 8, 10 };
	private final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color LABEL_COLOR = Color.WHITE;

	// Components
	private JButton startButton;
	private JLabel nameLabel;
	private JLabel levelLabel;
	private JLabel ipLabel;
	private JTextField nameField;
	private JComboBox<Integer> levelField;
	private JTextField ipField;
	private static JLabel stateLebel;
	private JPanel statePanel;

	private Client client;
	private boolean clientCreated = false;

	// Frame
	private static JFrame frame;

	// Initializing
	public Start() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setBackground(BACKGROUND_COLOR);
		startButton = new JButton(new ImageIcon("res\\start_btn.png"));
		startButton.setBounds(136, 240, 129, 65);
		startButton.addActionListener(this);
		add(startButton);
		nameLabel = new JLabel("Username :");
		nameLabel.setBounds(5, 25, 150, 20);
		nameLabel.setForeground(LABEL_COLOR);
		add(nameLabel);
		levelLabel = new JLabel("Level :");
		levelLabel.setBounds(5, 75, 150, 20);
		levelLabel.setForeground(LABEL_COLOR);
		add(levelLabel);
		ipLabel = new JLabel("Server IP :");
		ipLabel.setBounds(5, 125, 150, 20);
		ipLabel.setForeground(LABEL_COLOR);
		add(ipLabel);
		nameField = new JTextField("Sadra");
		nameField.setBounds(150, 20, 250, 30);
		add(nameField);
		levelField = new JComboBox<Integer>(levels);
		levelField.setBounds(150, 70, 250, 30);
		add(levelField);
		ipField = new JTextField("localhost");
		ipField.setBounds(150, 120, 250, 30);
		add(ipField);
		statePanel = new JPanel();
		TitledBorder titledBorder = BorderFactory.createTitledBorder("State");
		titledBorder.setTitleColor(Color.ORANGE);
		statePanel.setBorder(titledBorder);
		statePanel.setLayout(null);
		statePanel.setBounds(5, 165, 400, 70);
		statePanel.setBackground(BACKGROUND_COLOR);
		add(statePanel);
		stateLebel = new JLabel(
				"Please fill the fields and click on \"Start\" button ...");
		stateLebel.setBounds(15, 20, 370, 35);
		stateLebel.setForeground(LABEL_COLOR);
		statePanel.add(stateLebel);
		frame.addWindowListener(new WA());
		frame.setFocusable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (nameField.getText().equals("")) {
			state("Please enter a username !!!");
		} else if (ipField.getText().equals("")) {
			state("Please enter server IP !!!");
		} else {
			try {
				if (!clientCreated) {
					state("Connecting to server ...");
					client = new Client(ipField.getText());
					clientCreated = true;
				}
				String name = nameField.getText();
				client.write(name);
				String answer = client.read();
				if (answer.equals("#UsernameExist#")) {
					state("Username exist ! Please enter another username ...");
				} else if (answer.equals("#UsernameNotExist#")) {
					int level = (Integer) levelField.getSelectedItem();
					new GamePanel(name, level, frame, client);
				}
			} catch (UnknownHostException e1) {
				state("Server not found !!!");
				clientCreated = false;
			} catch (IOException e1) {
				state("Couldn't connect to server !!!");
				clientCreated = false;
			}
		}
	}

	public static void state(String state) {
		stateLebel.setText(state);
	}

	private class WA extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			if (client != null)
				client.write("#exit#");
		}

	}

	public static void main(String[] args) {
		frame = new JFrame();
		frame.setTitle("Memory Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Start());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
