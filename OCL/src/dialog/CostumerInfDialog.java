package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JScrollPane;

import main.CostumerHttpConnector;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class CostumerInfDialog extends JDialog implements DocumentListener,
		ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextPane txtpnName;
	private JTextPane txtpnAddress;
	private JTextPane txtpnPassword;
	private JTextPane txtpnEmail;
	private JTextPane txtpnCall;
	private JButton applyButton;
	private JButton cancelButton;

	private CostumerHttpConnector costumerHttpConnector;

	public CostumerInfDialog(final CostumerHttpConnector chc) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"res\\Actions-help-about-icon.png"));
		costumerHttpConnector = chc;
		WaitDialog waitDialog = new WaitDialog(true);
		HashMap<String, String> info = costumerHttpConnector.getInfo();
		waitDialog.dispose();
		if (info == null) {
			JOptionPane.showMessageDialog(null, "not connected !");
		} else {
			setTitle("Costumer Info");
			setResizable(false);
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout(0, 0));
			contentPanel.setPreferredSize(new Dimension(450, 500));
			{
				JPanel westPanel = new JPanel();
				westPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
				contentPanel.add(westPanel, BorderLayout.WEST);
				westPanel.setLayout(new GridLayout(9, 1, 10, 10));
				{
					JLabel lblId = new JLabel("ID");
					lblId.setIcon(new ImageIcon("res\\id.png"));
					westPanel.add(lblId);
				}
				{
					JLabel lblName = new JLabel("Name");
					lblName.setIcon(new ImageIcon("res\\tag-icon.png"));
					westPanel.add(lblName);
				}
				{
					JLabel lblAddress = new JLabel("Address");
					lblAddress.setIcon(new ImageIcon("res\\home.png"));
					westPanel.add(lblAddress);
				}
				{
					JLabel lblScore = new JLabel("Score");
					lblScore.setIcon(new ImageIcon("res\\misc_57.png"));
					westPanel.add(lblScore);
				}
				{
					JLabel lblUsername = new JLabel("Username");
					lblUsername.setIcon(new ImageIcon("res\\user.png"));
					westPanel.add(lblUsername);
				}
				{
					JLabel lblPassword = new JLabel("Password");
					lblPassword.setIcon(new ImageIcon("res\\key.png"));
					westPanel.add(lblPassword);
				}
				{
					JLabel lblEmail = new JLabel("Email");
					lblEmail.setIcon(new ImageIcon("res\\Email-icon.png"));
					westPanel.add(lblEmail);
				}
				{
					JLabel lblCall = new JLabel("Call");
					lblCall.setIcon(new ImageIcon("res\\contact.png"));
					westPanel.add(lblCall);
				}
				{
					JLabel lblBalance = new JLabel("Balance");
					lblBalance
							.setIcon(new ImageIcon("res\\money-bag-icon.png"));
					westPanel.add(lblBalance);
				}
			}
			{
				JPanel centerPanel = new JPanel();
				centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
				contentPanel.add(centerPanel, BorderLayout.CENTER);
				centerPanel.setLayout(new GridLayout(9, 1, 10, 10));
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						JTextPane txtpnId = new JTextPane();
						txtpnId.setEditable(false);
						txtpnId.setText(info.get("Id") + "");
						scrollPane.setViewportView(txtpnId);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						txtpnName = new JTextPane();
						txtpnName.setText(info.get("name"));
						txtpnName.setEditable(false);
						scrollPane.setViewportView(txtpnName);
						txtpnName.getDocument().addDocumentListener(this);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						txtpnAddress = new JTextPane();
						txtpnAddress.setText(info.get("address"));
						scrollPane.setViewportView(txtpnAddress);
						txtpnAddress.getDocument().addDocumentListener(this);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						JTextPane txtpnScore = new JTextPane();
						txtpnScore.setEditable(false);
						txtpnScore.setText(info.get("rate"));
						scrollPane.setViewportView(txtpnScore);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						JTextPane txtpnUsername = new JTextPane();
						txtpnUsername.setEditable(false);
						txtpnUsername.setText(costumerHttpConnector
								.getUsername());
						scrollPane.setViewportView(txtpnUsername);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						txtpnPassword = new JTextPane();
						txtpnPassword.setText(costumerHttpConnector
								.getPassword());
						scrollPane.setViewportView(txtpnPassword);
						txtpnPassword.getDocument().addDocumentListener(this);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						txtpnEmail = new JTextPane();
						txtpnEmail.setText(info.get("email"));
						scrollPane.setViewportView(txtpnEmail);
						txtpnEmail.getDocument().addDocumentListener(this);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						txtpnCall = new JTextPane();
						txtpnCall.setText(info.get("call"));
						scrollPane.setViewportView(txtpnCall);
						txtpnCall.getDocument().addDocumentListener(this);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					centerPanel.add(scrollPane);
					{
						JTextPane txtpnBalance = new JTextPane();
						txtpnBalance.setEditable(false);
						txtpnBalance.setText(info.get("balance"));
						scrollPane.setViewportView(txtpnBalance);
					}
				}
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					applyButton = new JButton("Apply");
					applyButton.setEnabled(false);
					applyButton.setActionCommand("Apply");
					buttonPane.add(applyButton);
					getRootPane().setDefaultButton(applyButton);
					applyButton.addActionListener(this);
				}
				{
					cancelButton = new JButton("Cancel");
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
					cancelButton.addActionListener(this);
				}
			}

			pack();
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setVisible(true);
		}
	}

	private void onValueChange() {
		applyButton.setEnabled(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		onValueChange();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onValueChange();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		onValueChange();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Apply")) {
			editInfo();
		} else if (e.getActionCommand().equals("Cancel")) {
			dispose();
		}
	}

	private void editInfo() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				applyButton.setEnabled(false);
				String password = txtpnPassword.getText();
				String email = txtpnEmail.getText();
				String call = txtpnCall.getText();
				String address = txtpnAddress.getText();
				String name = txtpnName.getText();

				WaitDialog waitDialog = new WaitDialog(true);
				String reply = costumerHttpConnector.editInfo(password, email,
						call, address, name);
				waitDialog.dispose();
				System.out.println(reply);
				if (reply.equals("done")) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "not connected !");
				}
				applyButton.setEnabled(true);
			}
		});
		thread.start();
	}

}
