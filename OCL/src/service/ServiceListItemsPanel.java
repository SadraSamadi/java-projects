package service;

import java.awt.Dimension;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.SwingConstants;

public class ServiceListItemsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblName;
	private JLabel lblCategory;
	private JLabel lblNumber;

	public ServiceListItemsPanel(int number, String name, String category) {
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(250, 50));
		setLayout(new BorderLayout(0, 0));

		lblName = new JLabel(name);
		lblName.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblName.setForeground(Color.BLACK);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblName, BorderLayout.NORTH);

		lblCategory = new JLabel(ServicePage.getCategoryText(category));
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCategory.setForeground(Color.GRAY);
		add(lblCategory, BorderLayout.CENTER);

		lblNumber = new JLabel(" ." + number);
		lblNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumber.setForeground(Color.RED);
		add(lblNumber, BorderLayout.EAST);

	}

	public JLabel getLblName() {
		return lblName;
	}

	public JLabel getLblCategory() {
		return lblCategory;
	}

	public JLabel getLblNumber() {
		return lblNumber;
	}

}
