import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class msg extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 909015554671713588L;

	/** The Constant PROXY. */
	private static final Proxy PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8580));

	/** The Constant CHARSET. */
	private static final String CHARSET = "UTF-8";

	/** The Constant SPEC. */
	// private static final String SPEC =
	// "http://localhost:63342/Test/index.php";
	private static final String SPEC = "http://remmargorp.ir/";

	/** The username. */
	private String username;

	private JPanel contentPane;
	private JTextField txtMsg;
	private JTextField txtTarget;
	private JTextArea txtrMessages;
	private JButton btnSend;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel lblTarget;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					msg frame = new msg();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public msg() {
		username = JOptionPane.showInputDialog("username");
		setTitle(username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		txtMsg = new JTextField();
		txtMsg.setText("msg");
		contentPane.add(txtMsg, BorderLayout.NORTH);
		txtMsg.setColumns(10);

		btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String response = sendMessageTo(txtMsg.getText(), txtTarget.getText());
				if (response.equals("1")) {
					txtrMessages.append(username + ": " + txtMsg.getText() + "\n");
					txtMsg.setText("");
				}

			}
		});
		contentPane.add(btnSend, BorderLayout.EAST);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		txtrMessages = new JTextArea();
		scrollPane.setViewportView(txtrMessages);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		txtTarget = new JTextField();
		panel.add(txtTarget);
		txtTarget.setText("target");
		txtTarget.setColumns(10);

		lblTarget = new JLabel("Target : ");
		panel.add(lblTarget, BorderLayout.WEST);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				String response = checkNewMsg();
				if (!response.equals("no_msg")) {
					String[] msgs = response.split("##");
					for (int i = 0; i < msgs.length; i++) {
						txtrMessages.append(txtTarget.getText() + ": " + msgs[i] + "\n");
					}
				} else {
					// txtrMessages.append("null\n");
				}

			}
		}, 5000, 3000);
	}

	private String request(String params, boolean useProxy) {
		HttpURLConnection connection = null;
		PrintWriter writer = null;
		BufferedReader reader = null;
		String response = "";
		try {
			connection = (HttpURLConnection) (useProxy ? new URL(SPEC).openConnection(PROXY)
					: new URL(SPEC).openConnection());
			connection.setDoOutput(true);
			writer = new PrintWriter(connection.getOutputStream());
			writer.println(params);
			writer.flush();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					response += line;
				}
			} else {
				response = "HTTP_PROBLEM";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			response = "MalformedURLException";
		} catch (IOException e) {
			e.printStackTrace();
			response = "IOException";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (writer != null) {
				writer.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

	/**
	 * Encode.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	private String encode(String s) {
		try {
			return URLEncoder.encode(s, CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}

	private String sendMessageTo(String message, String target) {
		String params = "username=" + encode(username) + "&action=" + encode("send") + "&msg=" + encode(message)
				+ "&target=" + encode(target) + "&";
		return request(params, false);
	}

	private String checkNewMsg() {
		String params = "username=" + encode(username) + "&action=" + encode("check_new_msg") + "&";
		return request(params, false);
	}

}
