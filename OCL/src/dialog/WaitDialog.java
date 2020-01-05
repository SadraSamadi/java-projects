package dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class WaitDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPleaseWait;
	private JProgressBar progressBar;
	private JPanel panel;

	public WaitDialog(boolean indeterminate) {
		setUndecorated(true);
		setFocusTraversalPolicyProvider(true);
		setType(Type.UTILITY);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setPreferredSize(new Dimension(200, 70));
		contentPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,
				0, 0)));
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, BorderLayout.CENTER);

		lblPleaseWait = new JLabel("Please wait ...");
		panel.add(lblPleaseWait);
		lblPleaseWait.setForeground(Color.WHITE);

		progressBar = new JProgressBar();
		panel.add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setIndeterminate(indeterminate);
		progressBar.setStringPainted(!indeterminate);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setValue(int value) {
		progressBar.setValue(value);
	}

}
