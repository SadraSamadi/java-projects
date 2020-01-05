package order;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import main.CostumerHttpConnector;
import order.Order.SubOrder;

import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import dialog.WaitDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderRightPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Order order;
	private CostumerHttpConnector costumerHttpConnector;
	private JTable table;

	String[] columnNames = { "", "Service Name", "Rate", "Amount", "Per", "Price", "Status" };

	String[][] data;
	private JPanel panel_1;
	private JPanel panel;
	private JTextField txtAddress;
	private JButton btnAcceptAll;
	private JButton btnFinishAll;

	public OrderRightPanel(final Order order, CostumerHttpConnector chc) {
		this.order = order;
		costumerHttpConnector = chc;
		setLayout(new BorderLayout(0, 0));

		data = new String[order.getSubOrders().size()][columnNames.length];
		for (int i = 0; i < order.getSubOrders().size(); i++) {
			SubOrder subOrder = order.getSubOrders().get(i);
			String[] row = new String[columnNames.length];
			row[0] = String.valueOf(i);
			row[1] = subOrder.getServiceName();
			row[2] = subOrder.getRate();
			row[3] = subOrder.getAmount() + "";
			row[4] = subOrder.getPer() + "";
			row[5] = subOrder.getPrice() + "";
			row[6] = OrderPage.getStatus(subOrder.getStatusId());
			data[i] = row;
		}

		table = new JTable(new TM());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(25);
		table.addMouseListener(new MA());
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(250);

		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(table);
		add(jScrollPane, BorderLayout.CENTER);

		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(2, 2, 2, 2));
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(1, 2, 2, 2));

		btnAcceptAll = new JButton("Accept all");
		btnAcceptAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						btnAcceptAll.setEnabled(false);
						WaitDialog waitDialog = new WaitDialog(true);
						for (int row = 0; row < order.getSubOrders().size(); row++) {
							SubOrder subOrder = order.getSubOrders().get(row);
							if (subOrder.getStatusId().equals("0")) {
								String reply = costumerHttpConnector.setOrderStatus(subOrder.getId(), "2");
								if (reply.equals("done")) {
									subOrder.setStatusId("1");
									table.getModel().setValueAt(OrderPage.getStatus("1"), row, 5);
								} else {
									waitDialog.dispose();
									JOptionPane.showMessageDialog(null, "Error !");
									break;
								}
							}
						}
						OrderPage.list.getSelectedValue().name();
						OrderPage.list.getSelectedValue().repaint();
						OrderPage.list.repaint();
						waitDialog.dispose();
						btnAcceptAll.setEnabled(true);
					}
				}).start();
			}

		});
		btnAcceptAll.setIcon(new ImageIcon("res\\Accept-icon.png"));
		panel_1.add(btnAcceptAll);

		btnFinishAll = new JButton("Finish all");
		btnFinishAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						btnFinishAll.setEnabled(false);
						WaitDialog waitDialog = new WaitDialog(true);
						for (int row = 0; row < order.getSubOrders().size(); row++) {
							SubOrder subOrder = order.getSubOrders().get(row);
							if (subOrder.getStatusId().equals("0") || subOrder.getStatusId().equals("1")) {
								String reply = costumerHttpConnector.setOrderStatus(subOrder.getId(), "2");
								if (reply.equals("done")) {
									subOrder.setStatusId("2");
									table.getModel().setValueAt(OrderPage.getStatus("2"), row, 5);
								} else {
									waitDialog.dispose();
									JOptionPane.showMessageDialog(null, "Error !");
									break;
								}
							}
						}
						OrderPage.list.getSelectedValue().name();
						OrderPage.list.getSelectedValue().repaint();
						OrderPage.list.repaint();
						waitDialog.dispose();
						btnFinishAll.setEnabled(true);
					}
				}).start();
			}

		});
		btnFinishAll.setIcon(new ImageIcon("res\\Map-Marker-Push-Pin-1-Right-Azure-icon.png"));
		panel_1.add(btnFinishAll);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Address", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		txtAddress = new JTextField(order.getAddress());
		txtAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAddress.setEditable(false);
		panel.add(txtAddress, BorderLayout.CENTER);
	}

	private class MA extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() > 1) {
				int i = table.getSelectedRow();
				new SetStatus(order.getSubOrders().get(i), costumerHttpConnector, table, i);
			}
		}

	}

	private class TM extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int i, int j) {
			return data[i][j];
		}

		@Override
		public Class<?> getColumnClass(int i) {
			return getValueAt(0, i).getClass();
		}

		@Override
		public String getColumnName(int i) {
			return columnNames[i];
		}

		@Override
		public boolean isCellEditable(int i, int j) {
			return false;
		}

		@Override
		public void setValueAt(Object value, int i, int j) {
			data[i][j] = (String) value;
			fireTableCellUpdated(i, j);
		}

	}

}