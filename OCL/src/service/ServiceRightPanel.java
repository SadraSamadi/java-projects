package service;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class ServiceRightPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JLabel categoryLabel;
	private JLabel priceLabel;
	private JLabel amountLabel;
	private JScrollPane scrollPane;
	private JTextPane explainArea;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField txtCategory;
	private JTextField txtAmount;
	private JTextField txtPrice;

	public ServiceRightPanel(String name, String explain, String category,
			double price, int amount) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));

		JPanel northtPanel = new JPanel();
		northtPanel.setBackground(Color.DARK_GRAY);
		northtPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(northtPanel, BorderLayout.NORTH);
		northtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		nameLabel = new JLabel(name);
		nameLabel.setToolTipText("Name");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		northtPanel.add(nameLabel);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.LIGHT_GRAY);
		southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(1, 3, 5, 5));

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		southPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		categoryLabel = new JLabel("Category");
		categoryLabel.setIcon(new ImageIcon("res\\category-icon.png"));
		panel.add(categoryLabel, BorderLayout.WEST);
		categoryLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

		txtCategory = new JTextField();
		txtCategory.setEditable(false);
		txtCategory.setText(category);
		panel.add(txtCategory, BorderLayout.SOUTH);
		txtCategory.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		southPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		amountLabel = new JLabel("Amount");
		amountLabel.setIcon(new ImageIcon("res\\Numbers-icon (1).png"));
		panel_1.add(amountLabel, BorderLayout.WEST);
		amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtAmount = new JTextField();
		txtAmount.setEditable(false);
		txtAmount.setText(amount + "");
		panel_1.add(txtAmount, BorderLayout.SOUTH);
		txtAmount.setColumns(10);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		southPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		priceLabel = new JLabel("Price");
		priceLabel.setIcon(new ImageIcon("res\\total-plan-cost-icon.png"));
		panel_2.add(priceLabel, BorderLayout.WEST);
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		txtPrice.setText(price + "");
		panel_2.add(txtPrice, BorderLayout.SOUTH);
		txtPrice.setColumns(10);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.LIGHT_GRAY);
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(0, 1, 0, 0));

		scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		centerPanel.add(scrollPane);

		explainArea = new JTextPane();
		explainArea.setToolTipText("Explain");
		explainArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		explainArea.setText(explain);
		explainArea.setEditable(false);
		scrollPane.setViewportView(explainArea);
	}

}
