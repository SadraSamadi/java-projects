package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import order.OrderPage;
import service.ServicePage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import java.util.Locale;

public class LoginFrame extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pwdCs;
	private JFormattedTextField usernameField;
	private JButton btnLogIn;
	private JLabel lblForgotPassword;
	private String username;
	private String password;
	private JPanel statePanel;
	private JLabel stateLabel;
	private JCheckBox chckbxSavePassword;

	private CostumerHttpConnector costumerHttpConnector;
	private JProgressBar progressBar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		setResizable(false);
		setLocale(new Locale("fa", "IR"));

		setIconImage(Toolkit.getDefaultToolkit().getImage("res\\secrecy-icon.png"));
		setTitle("Log in");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(360, 180));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 3, 10, 10));

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setIcon(new ImageIcon("res\\user.png"));
		contentPane.add(lblUsername);

		usernameField = new JFormattedTextField("");
		usernameField.setName("");
		usernameField.setToolTipText("");
		usernameField.setColumns(1);
		contentPane.add(usernameField);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setIcon(new ImageIcon("res\\key.png"));
		contentPane.add(lblPassword);

		pwdCs = new JPasswordField("");
		contentPane.add(pwdCs);

		btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(this);

		chckbxSavePassword = new JCheckBox("Save Password ?");
		contentPane.add(chckbxSavePassword);
		contentPane.add(btnLogIn);

		String[] userpass = Saves.ReadLogin();
		if (userpass != null) {
			if (userpass[0].equals("") && userpass[1] == null) {
				chckbxSavePassword.setSelected(false);
			} else {
				chckbxSavePassword.setSelected(true);
			}
			usernameField.setText(userpass[0]);
			pwdCs.setText(userpass[1]);
		}

		lblForgotPassword = new JLabel("Forgot Password ?");
		lblForgotPassword.setForeground(new Color(30, 144, 255));
		lblForgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblForgotPassword.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://onchapline.ir/"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblForgotPassword.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblForgotPassword.setForeground(new Color(30, 144, 255));
			}

		});
		contentPane.add(lblForgotPassword);

		statePanel = new JPanel();
		contentPane.add(statePanel);
		statePanel.setLayout(new BorderLayout(0, 0));

		stateLabel = new JLabel("");
		stateLabel.setForeground(Color.RED);
		stateLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		statePanel.add(stateLabel, BorderLayout.NORTH);

		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setIndeterminate(true);
		statePanel.add(progressBar, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		username = usernameField.getText();
		password = new String(pwdCs.getPassword());
		if (username.equals("")) {
			stateLabel.setText("Please enter username ...");
		} else if (password.equals("")) {
			stateLabel.setText("Please enter password ...");
		} else {
			new Thread(this).start();
		}
	}

	@Override
	public void run() {
		btnLogIn.setEnabled(false);
		stateLabel.setText("Please wait ...");
		progressBar.setVisible(true);
		costumerHttpConnector = new CostumerHttpConnector(username, password);
		if (costumerHttpConnector.isConnected()) {
			Home home = new Home(costumerHttpConnector);
			home.setOrderPage(new OrderPage(costumerHttpConnector));
			home.setServicePage(new ServicePage(costumerHttpConnector));
			if (chckbxSavePassword.isSelected()) {
				Saves.WriteLogin(username, password);
			} else {
				Saves.deleteLogin();
			}
			dispose();
			home.setVisible(true);
		} else {
			stateLabel.setText("not Connected !");
		}
		progressBar.setVisible(false);
		btnLogIn.setEnabled(true);
	}

}
