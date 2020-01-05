package order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import java.awt.GridLayout;

import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.ButtonGroup;

import dialog.WaitDialog;
import main.CostumerHttpConnector;
import main.DownloadUtility;
import order.Order.SubOrder;

public class SetStatus extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnDenyOrder;
	private JRadioButton rdbtnAcceptOrder;
	private JComboBox<String> comboBox;
	private JButton btnDownload;
	private JButton applyButton;
	private JButton cancelButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panel_2;
	private JLabel label;
	private JLabel label_1;
	private JPanel panel_3;

	private CostumerHttpConnector costumerHttpConnector;
	private SubOrder subOrder;
	private int row;
	private TableModel tableModel;

	private JLabel lblState;
	private JLabel label_2;
	private JRadioButton rdbtnFinish;

	public SetStatus(final SubOrder subOrder, CostumerHttpConnector chc,
			JTable table, int row) {
		setResizable(false);
		this.row = row;
		costumerHttpConnector = chc;
		this.subOrder = subOrder;
		tableModel = table.getModel();
		setTitle("Status");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setPreferredSize(new Dimension(500, 180));
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"), "Order",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0,
							0, 0)));
			contentPanel.add(panel, BorderLayout.WEST);
			panel.setLayout(new BorderLayout(0, 0));
			{
				panel_2 = new JPanel();
				panel.add(panel_2, BorderLayout.WEST);
				panel_2.setLayout(new GridLayout(0, 1, 0, 0));
				{
					label = new JLabel("");
					label.setIcon(new ImageIcon("res\\deny-icon.png"));
					panel_2.add(label);
				}
				{
					label_1 = new JLabel("");
					label_1.setIcon(new ImageIcon("res\\Accept-icon.png"));
					panel_2.add(label_1);
				}
				{
					label_2 = new JLabel("");
					panel_2.add(label_2);
					label_2.setIcon(new ImageIcon(
							"res\\Map-Marker-Push-Pin-1-Right-Azure-icon.png"));
				}
			}
			{
				panel_3 = new JPanel();
				panel.add(panel_3, BorderLayout.CENTER);
				panel_3.setLayout(new GridLayout(0, 1, 0, 0));
				{
					rdbtnDenyOrder = new JRadioButton("Deny");
					rdbtnDenyOrder.setSelected(subOrder.isDenied());
					rdbtnDenyOrder.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							comboBox.setEnabled(true);
							applyButton.setEnabled(true);
						}

					});
					panel_3.add(rdbtnDenyOrder);
					buttonGroup.add(rdbtnDenyOrder);
				}
				{
					rdbtnAcceptOrder = new JRadioButton("Accept");
					rdbtnAcceptOrder.setSelected(subOrder.isAccepted());
					rdbtnAcceptOrder.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							comboBox.setEnabled(false);
							applyButton.setEnabled(true);
						}

					});
					panel_3.add(rdbtnAcceptOrder);
					buttonGroup.add(rdbtnAcceptOrder);
				}
				{
					rdbtnFinish = new JRadioButton("Finish");
					rdbtnFinish.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							comboBox.setEnabled(false);
							applyButton.setEnabled(true);
						}

					});
					buttonGroup.add(rdbtnFinish);
					panel_3.add(rdbtnFinish);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(null, "Deny Reason",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.add(panel_1);
				{
					comboBox = new JComboBox<String>(OrderPage.denyReasons);
					comboBox.setPreferredSize(new Dimension(350, 30));
					comboBox.setSelectedIndex(-1);
					panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					panel_1.add(comboBox);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(null, "Download File",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.add(panel_1);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					btnDownload = new JButton("Download");
					btnDownload.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String username = costumerHttpConnector
									.getUsername();
							String password = costumerHttpConnector
									.getPassword();
							String sub_Id = subOrder.getId();
							String orderp_Id = subOrder.getOrderpId();
							String link = "http://onchapline.ir/files/getFile.php?username="
									+ username
									+ "&password="
									+ password
									+ "&sub_Id="
									+ sub_Id
									+ "&orderp_Id="
									+ orderp_Id;
							new DownloadUtility(link);
						}

					});
					btnDownload.setIcon(new ImageIcon(
							"res\\scroll-down-icon.png"));
					panel_1.add(btnDownload);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(
					0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				applyButton = new JButton("Apply");
				applyButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						lblState.setText("");
						if (rdbtnAcceptOrder.isSelected()) {
							setStatus("1");
						} else if (rdbtnDenyOrder.isSelected()) {
							int index = comboBox.getSelectedIndex();
							if (index == -1) {
								lblState.setText("Please select deny reason !");
							} else if (index == 3) {
								setStatus("9");
							} else {
								setStatus(String.valueOf(index + 3));
							}
						} else if (rdbtnFinish.isSelected()) {
							setStatus("2");
						}
					}

				});
				{
					lblState = new JLabel("");
					lblState.setForeground(Color.RED);
					buttonPane.add(lblState);
				}
				applyButton.setEnabled(false);
				applyButton.setActionCommand("OK");
				buttonPane.add(applyButton);
				getRootPane().setDefaultButton(applyButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		rdbtnAcceptOrder.setEnabled(false);
		rdbtnDenyOrder.setEnabled(false);
		rdbtnFinish.setEnabled(false);
		comboBox.setEnabled(false);
		switch (Integer.parseInt(subOrder.getStatusId())) {
		case 0:
			rdbtnAcceptOrder.setEnabled(true);
			rdbtnDenyOrder.setEnabled(true);
			rdbtnFinish.setEnabled(true);
			break;
		case 1:
			rdbtnAcceptOrder.setSelected(true);
			rdbtnFinish.setEnabled(true);
			rdbtnAcceptOrder.setEnabled(true);
			break;
		case 2:
			rdbtnFinish.setSelected(true);
			break;
		default:
			rdbtnDenyOrder.setSelected(true);
			comboBox.setSelectedItem(OrderPage.getStatus(subOrder.getStatusId()));
			comboBox.setEnabled(false);
			break;
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void setStatus(final String status) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				WaitDialog waitDialog = new WaitDialog(true);
				String response = costumerHttpConnector.setOrderStatus(
						subOrder.getId(), status);
				waitDialog.dispose();
				if (!response.equals("not connected")) {
					subOrder.setStatusId(status);
					tableModel.setValueAt(
							OrderPage.getStatus(subOrder.getStatusId()), row, 5);
					OrderPage.list.getSelectedValue().name();
					OrderPage.list.getSelectedValue().repaint();
					OrderPage.list.repaint();
					lblState.setText("Done");
					rdbtnAcceptOrder.setEnabled(false);
					rdbtnDenyOrder.setEnabled(false);
					rdbtnFinish.setEnabled(false);
					applyButton.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Error !");
				}
			}
		});
		thread.start();
	}

}
