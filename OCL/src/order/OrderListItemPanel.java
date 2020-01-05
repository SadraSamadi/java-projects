package order;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import order.Order.SubOrder;

public class OrderListItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JLabel lblNumber;
	public JLabel lblName;
	public JLabel lblDate;
	public JPanel panel;
	private Order order;

	public OrderListItemPanel(Order order, int number) {
		this.order = order;
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(200, 50));
		setLayout(new BorderLayout(0, 0));
		
		lblNumber = new JLabel(" ." + number);
		lblNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblNumber, BorderLayout.EAST);
		
		lblDate = new JLabel(order.getDate());
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblDate, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblName = new JLabel();
		name();
		lblName.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblName, BorderLayout.CENTER);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblNew = new JLabel("");
		panel.add(lblNew, BorderLayout.WEST);
		if (order.hasNew()) {
			lblNew.setIcon(new ImageIcon("res\\New-icon.png"));
		}
		
	}
	
	private int getFinishedCount() {
		int count = 0;
		for (SubOrder subOrder : order.getSubOrders()) {
			if (Integer.parseInt(subOrder.getStatusId()) >= 2) {
				count++;
			}
		}
		return count;
	}

	public void name() {
		lblName.setText("Finished: " + getFinishedCount() + " / Total: " + order.getSubOrders().size());
	}

}
