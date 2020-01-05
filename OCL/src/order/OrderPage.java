package order;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import main.CostumerHttpConnector;

import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dialog.WaitDialog;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import order.Order.SubOrder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;

public class OrderPage extends JPanel implements ListSelectionListener, ListCellRenderer<OrderListItemPanel> {

	private static final long serialVersionUID = 1L;

	public static final String[] denyReasons = { "آدرس مقصد ناخوانا است", "حجم سفارش با فایل ارسالی همخوانی ندارد",
			"اطلاعات کافی برای سفارش در فایل ضمیمه قرار داده نشده است", "به دلایلی سفارش مورد نظر رد شد" };

	public static Vector<Order> orders;
	private static Vector<OrderListItemPanel> vector;
	private static CostumerHttpConnector costumerHttpConnector;
	public static JList<OrderListItemPanel> list;
	private static JScrollPane scrollPane;

	private JSplitPane splitPane;

	private static int index;

	private JPanel rightPanel;

	public OrderPage(CostumerHttpConnector chc) {
		
		costumerHttpConnector = chc;

		setLayout(new BorderLayout(0, 0));

		splitPane = new JSplitPane();
		splitPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(splitPane, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(210, 2));
		scrollPane.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setLeftComponent(scrollPane);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateList();
			}

		});
		btnRefresh.setIcon(new ImageIcon("res\\arrow-refresh-icon.png"));
		scrollPane.setColumnHeaderView(btnRefresh);
		rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new GridLayout(1, 1, 0, 0));

		list = new JList<OrderListItemPanel>();
		list.setValueIsAdjusting(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new EmptyBorder(1, 1, 1, 1));
		list.addListSelectionListener(this);
		list.setCellRenderer(this);
		list.setCursor(new Cursor(Cursor.HAND_CURSOR));

		updateList();
	}

	public static void updateList() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				if (setOrders()) {
					vector = new Vector<OrderListItemPanel>();
					for (int i = 0; i < orders.size(); i++) {
						vector.addElement(new OrderListItemPanel(orders.get(i), i + 1));
					}
					list.setListData(vector);
					scrollPane.setViewportView(list);
				} else {
					JOptionPane.showMessageDialog(null, "Error !");
				}
			}
		});
		thread.start();
	}

	@SuppressWarnings("unchecked")
	private static boolean setOrders() {
		WaitDialog waitDialog = new WaitDialog(true);
		ArrayList<HashMap<String, Object>> tmpOrders = costumerHttpConnector.getOrders();
		waitDialog.dispose();
		if (tmpOrders == null) {
			return false;
		} else {
			WaitDialog waitDialog2 = new WaitDialog(true);
			orders = new Vector<Order>();
			for (HashMap<String, Object> o : tmpOrders) {
				if (o == null) {
					waitDialog2.dispose();
					return false;
				} else {
					Order order = new Order();
					order.setId((String) o.get("Id"));
					order.setUser((String) o.get("user"));
					order.setCostumer((String) o.get("costumer"));
					order.setDate((String) o.get("date"));
					order.setAddress((String) o.get("address"));
					ArrayList<SubOrder> subOrders = new ArrayList<Order.SubOrder>();
					for (HashMap<String, String> so : (ArrayList<HashMap<String, String>>) o.get("sub")) {
						SubOrder subOrder = new Order().new SubOrder();
						subOrder.setAmount(Integer.parseInt(so.get("amount")));
						subOrder.setId(so.get("Id"));
						subOrder.setOrderpId(so.get("orderp_Id"));
						subOrder.setPer(Integer.parseInt(so.get("per")));
						subOrder.setRate(so.get("rate"));
						subOrder.setServiceId(so.get("service"));
						subOrder.setStatusId(so.get("status"));
						HashMap<String, String> map = costumerHttpConnector.getServiceById(subOrder.getServiceId());
						double unitPrice = 0;
						if (map != null) {
							unitPrice = Double.parseDouble(map.get("price"));
							subOrder.setServiceName(map.get("name"));
						} else {
							waitDialog2.dispose();
							JOptionPane.showMessageDialog(null, "Error !");
							break;
						}
						subOrder.setNew(subOrder.getStatusId().equals("0"));
						subOrder.setPrice(unitPrice * subOrder.getAmount() * subOrder.getPer());
						int statusId = Integer.parseInt(subOrder.getStatusId());
						subOrder.setAccepted(statusId == 1);
						subOrder.setDenied(statusId > 2);
						subOrders.add(subOrder);
					}
					order.setSubOrders(subOrders);
					orders.addElement(order);
				}
			}
			waitDialog2.dispose();
			return true;
		}
	}

	public static String getStatus(String status) {
		switch (Integer.parseInt(status)) {
		case 0:
			return "در حال بررسی";
		case 1:
			return "سفارش تایید شد";
		case 2:
			return "سفارش انجام شده و آماده ارسال است";
		case 3:
			return denyReasons[0];
		case 4:
			return denyReasons[1];
		case 5:
			return denyReasons[2];
		case 9:
			return denyReasons[3];
		default:
			return "";
		}
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends OrderListItemPanel> list, OrderListItemPanel item,
			int index, boolean isSelected, boolean cellHasFocus) {
		item.setBackground(isSelected ? new Color(100, 149, 237) : Color.WHITE);
		item.lblName.setForeground(isSelected ? Color.WHITE : Color.BLACK);
		item.lblNumber.setForeground(isSelected ? Color.YELLOW : Color.RED);
		item.lblDate.setForeground(isSelected ? Color.WHITE : Color.GRAY);
		item.panel.setBackground(isSelected ? new Color(100, 149, 237) : Color.WHITE);
		return item;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		index = list.getSelectedIndex();
		if (index >= 0) {
			rightPanel.removeAll();
			rightPanel.add(new OrderRightPanel(orders.get(index), costumerHttpConnector));
			rightPanel.revalidate();
			rightPanel.repaint();
		}
	}

}
