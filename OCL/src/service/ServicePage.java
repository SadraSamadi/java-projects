package service;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.CostumerHttpConnector;

import javax.swing.JSplitPane;

import dialog.WaitDialog;

public class ServicePage extends JPanel implements ListCellRenderer<ServiceListItemsPanel>, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private static JScrollPane scrollPane;
	private JToolBar toolBar;
	private JLabel addLabel;
	private static JLabel editLabel;
	private static JLabel removeLabel;
	private static JList<ServiceListItemsPanel> list;
	private static Vector<ServiceListItemsPanel> vector;
	private static Vector<Service> services;
	private static JPanel rightPanel;
	private JLabel selectAllLabel;

	private JSplitPane splitPane;
	private static CostumerHttpConnector costumerHttpConnector;

	private static Service editedService;
	private JLabel lblRefresh;
	private static int index;

	public ServicePage(CostumerHttpConnector chc) {

		costumerHttpConnector = chc;

		setLayout(new BorderLayout(0, 0));

		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
		splitPane.setLeftComponent(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setCursor(new Cursor(Cursor.HAND_CURSOR));

		list = new JList<ServiceListItemsPanel>();
		list.setValueIsAdjusting(true);
		list.setBackground(Color.WHITE);
		list.setCellRenderer(this);
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					new ServiceFrame(services.get(list.getSelectedIndex()));
				}
			}

		});

		rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new GridLayout(1, 1, 0, 0));
		list.addListSelectionListener(this);

		toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		toolBar.setPreferredSize(new Dimension(0, 24));

		addLabel = new JLabel("");
		addLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				new ServiceFrame();
			}

		});
		addLabel.setToolTipText("Add Service");
		addLabel.setIcon(new ImageIcon("res\\add-icon.png"));
		toolBar.add(addLabel);

		editLabel = new JLabel("");
		editLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		editLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (editLabel.isEnabled()) {
					new ServiceFrame(services.get(list.getSelectedIndex()));
				}
			}

		});
		editLabel.setEnabled(false);
		editLabel.setToolTipText("Edit Service");
		editLabel.setIcon(new ImageIcon("res\\Pencil-icon.png"));
		toolBar.add(editLabel);

		removeLabel = new JLabel("");
		removeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		removeLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (removeLabel.isEnabled()) {
					removeService(false);
				}
			}

		});
		removeLabel.setToolTipText("Remove Service");
		removeLabel.setEnabled(false);
		removeLabel.setIcon(new ImageIcon("res\\Apps-Dialog-Remove-icon.png"));
		toolBar.add(removeLabel);
		toolBar.addSeparator();

		selectAllLabel = new JLabel("");
		selectAllLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		selectAllLabel.setToolTipText("Select all services");
		selectAllLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				list.setSelectionInterval(0, list.getModel().getSize() - 1);
			}

		});
		selectAllLabel.setIcon(new ImageIcon("res\\select-by-adding-to-selection-icon.png"));
		toolBar.add(selectAllLabel);

		lblRefresh = new JLabel("");
		lblRefresh.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				updateList();
			}

		});
		lblRefresh.setIcon(new ImageIcon("res\\arrow-refresh-icon.png"));
		lblRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRefresh.setToolTipText("Refresh");
		toolBar.add(lblRefresh);

		updateList();

	}

	protected static void updateList() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				WaitDialog waitDialog = new WaitDialog(true);
				services = new Vector<Service>();
				ArrayList<HashMap<String, String>> servicesArray = costumerHttpConnector.getServices();
				if (servicesArray != null) {
					for (HashMap<String, String> s : servicesArray) {
						Service service = new Service();
						service.setId(s.get("Id"));
						service.setCostumer(s.get("costumer"));
						service.setName(s.get("name"));
						service.setCategory(s.get("category"));
						service.setExplain(s.get("explain"));
						service.setPrice(Double.parseDouble(s.get("price")));
						service.setAmount(Integer.parseInt(s.get("amount")));
						services.addElement(service);
					}
				}
				vector = new Vector<ServiceListItemsPanel>();
				for (int i = 0; i < services.size(); i++) {
					vector.addElement(
							new ServiceListItemsPanel(i + 1, services.get(i).getName(), services.get(i).getCategory()));
				}
				list.setListData(vector);
				scrollPane.setViewportView(list);
				editLabel.setEnabled(false);
				removeLabel.setEnabled(false);
				waitDialog.dispose();
			}
		});
		thread.start();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends ServiceListItemsPanel> list,
			ServiceListItemsPanel item, int index, boolean isSelected, boolean cellHasFocus) {
		item.setBackground(isSelected ? new Color(100, 149, 237) : Color.WHITE);
		item.getLblNumber().setForeground(isSelected ? Color.YELLOW : Color.RED);
		item.getLblName().setForeground(isSelected ? Color.WHITE : Color.BLACK);
		item.getLblCategory().setForeground(isSelected ? Color.WHITE : Color.GRAY);
		return item;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		removeLabel.setEnabled(true);
		editLabel.setEnabled(true);
		index = list.getSelectedIndex();
		if (index >= 0) {
			String name = services.get(index).getName();
			String explain = services.get(index).getExplain();
			double price = services.get(index).getPrice();
			String category = services.get(index).getCategory();
			int amount = services.get(index).getAmount();
			ServiceRightPanel serviceRightPanel = new ServiceRightPanel(name, explain, category, price, amount);
			rightPanel.removeAll();
			rightPanel.add(serviceRightPanel);
			revalidate();
			repaint();
		}
	}

	public static synchronized void addService(final Service service) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				WaitDialog waitDialog = new WaitDialog(true);
				String name = service.getName();
				String catagory = service.getCategory();
				String price = String.valueOf(service.getPrice());
				String amount = String.valueOf(service.getAmount());
				String explain = service.getExplain();
				String response = costumerHttpConnector.newService(name, catagory, price, amount, explain);
				waitDialog.dispose();
				if (response.equals("done")) {
					updateList();
				} else {
					JOptionPane.showMessageDialog(null, "Error !");
				}
			}
		});
		thread.start();

	}

	public static synchronized void editService(final Service service) {
		editedService = service;
		removeService(true);
		rightPanel.removeAll();
		rightPanel.repaint();
		rightPanel.revalidate();
	}

	private static synchronized void removeService(final boolean editMode) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				WaitDialog waitDialog = new WaitDialog(false);
				int[] indices = list.getSelectedIndices();
				for (int i = indices.length - 1; i >= 0; i--) {
					String response = costumerHttpConnector.deleteService(services.get(indices[i]).getId());
					if (response.equals("done")) {
						waitDialog.setValue((indices.length - i) / indices.length * 100);
					} else {
						JOptionPane.showMessageDialog(null, "Error !");
						break;
					}
				}
				waitDialog.dispose();
				if (editMode)
					addService(editedService);
				else
					updateList();
				removeLabel.setEnabled(false);
				editLabel.setEnabled(false);
			}
		});
		if (editMode) {
			thread.start();
		} else {
			int response = JOptionPane.showConfirmDialog(null, "Are you sure ?", "Confirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				thread.start();
			}
		}

	}

	public static String getCategoryText(String categoryId) {
		return ServiceFrame.categories[Integer.parseInt(categoryId)];
	}

}
