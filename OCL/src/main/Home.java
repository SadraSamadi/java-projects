package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JMenuItem;

import javax.swing.JTabbedPane;

import order.OrderPage;
import service.ServicePage;
import dialog.AboutUsDialog;
import dialog.CostumerInfDialog;
import java.awt.Toolkit;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private ServicePage servicePanel;
	private OrderPage orderPanel;

	private JMenuBar menuBar;
	private JMenu mnAbout;
	private JMenuItem mntmAboutUs;
	private JMenuItem mntmAboutCostumer;
	private CostumerHttpConnector costumerHttpConnector;

	public Home(CostumerHttpConnector chc) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("res\\favicon.ico"));
		this.costumerHttpConnector = chc;
		setTitle("OnChapLine - (Beta)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		mntmAboutUs = new JMenuItem("About us");
		mntmAboutUs.setIcon(new ImageIcon("res\\Actions-help-about-icon.png"));
		mntmAboutUs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						new AboutUsDialog();
					}
				}).start();
			}

		});

		mntmAboutCostumer = new JMenuItem("About Costumer");
		mntmAboutCostumer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						new CostumerInfDialog(costumerHttpConnector);
					}

				}).start();
			}

		});
		mntmAboutCostumer.setIcon(new ImageIcon("res\\Actions-help-about-icon.png"));
		mnAbout.add(mntmAboutCostumer);
		mnAbout.add(mntmAboutUs);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		contentPane.setPreferredSize(new Dimension(900, 500));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane);

		pack();
		setLocationRelativeTo(null);
	}

	public void setOrderPage(OrderPage orderPage) {
		orderPanel = orderPage;
		tabbedPane.addTab("Orders", null, orderPanel, null);
	}

	public void setServicePage(ServicePage servicePage) {
		servicePanel = servicePage;
		tabbedPane.addTab("Services", null, servicePanel, null);
	}

}
