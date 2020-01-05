package service;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;

public class ServiceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final String[] categories = { "Р—же 1", "Р—же 2", "Р—же 3", "Р—же 4" };
	public static final byte ADD_MODE = 0;
	public static final byte EDIT_MODE = 1;

	private JPanel contentPane;
	private JSeparator separator;
	private JButton btnOk;
	private JSpinner amount;
	private JSpinner price;
	private JComboBox<String> category;
	private JTextField name;
	private byte mode;
	private JFrame frame = this;
	private JScrollPane scrollPane;
	private JTextPane explain;

	public ServiceFrame() {
		mode = ADD_MODE;
		init();
	}

	public ServiceFrame(Service service) {
		mode = EDIT_MODE;
		init();
		name.setText(service.getName());
		explain.setText(service.getExplain());
		category.setSelectedIndex(Integer.parseInt(service.getCategory()));
		price.setValue(service.getPrice());
		amount.setValue(service.getAmount());
	}

	private void init() {
		setResizable(false);
		setTitle("Service");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(436, 400));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		name = new JTextField();
		name.setBounds(124, 20, 300, 20);
		contentPane.add(name);
		name.setColumns(10);

		separator = new JSeparator();
		separator.setBounds(10, 57, 414, 2);
		contentPane.add(separator);

		JLabel lblName = new JLabel("Name");
		lblName.setIcon(new ImageIcon("res\\tag-icon.png"));
		lblName.setBounds(10, 14, 104, 32);
		contentPane.add(lblName);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 221, 414, 2);
		contentPane.add(separator_1);

		JLabel lblExplain = new JLabel("Explain");
		lblExplain.setIcon(new ImageIcon("res\\bubble-round-chat-icon.png"));
		lblExplain.setBounds(10, 70, 104, 32);
		contentPane.add(lblExplain);

		category = new JComboBox<String>();
		for (String string : categories) {
			category.addItem(string);
		}
		category.setBounds(124, 240, 300, 20);
		contentPane.add(category);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setIcon(new ImageIcon("res\\category-icon.png"));
		lblCategory.setBounds(10, 234, 104, 32);
		contentPane.add(lblCategory);

		price = new JSpinner();
		price.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		price.setBounds(124, 283, 300, 20);
		contentPane.add(price);

		amount = new JSpinner();
		amount.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		amount.setBounds(124, 326, 300, 20);
		contentPane.add(amount);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setIcon(new ImageIcon("res\\total-plan-cost-icon.png"));
		lblPrice.setBounds(10, 277, 104, 32);
		contentPane.add(lblPrice);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setIcon(new ImageIcon("res\\Numbers-icon (1).png"));
		lblAmount.setBounds(10, 320, 104, 32);
		contentPane.add(lblAmount);

		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Service service = new Service(name.getText(), explain.getText(), category.getSelectedIndex() + "",
						(Double) price.getValue(), (Integer) amount.getValue());
				switch (mode) {
				case ADD_MODE:
					ServicePage.addService(service);
					break;
				case EDIT_MODE:
					ServicePage.editService(service);
					break;
				}
				frame.dispose();
			}
		});
		btnOk.setBounds(173, 364, 90, 23);
		contentPane.add(btnOk);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(124, 70, 300, 140);
		contentPane.add(scrollPane);

		explain = new JTextPane();
		scrollPane.setViewportView(explain);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
