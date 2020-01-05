import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Formatter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class EndPanel extends JPanel {

	private final int WIDTH = 640;
	private final int HEIGHT = 480;
	private final Color BACKGROUND_COLOR = Color.BLUE;

	private JLabel logoLabel;
	private BufferedImage logo;
	private JLabel[] playersLabel = new JLabel[10];
	private Client client;
	private int score;

	public EndPanel(Client client, int score) {
		this.client = client;
		this.score = score;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(BACKGROUND_COLOR);
		setLayout(new GridLayout(1, 2, 4, 4));
		setBorder(new EmptyBorder(4, 4, 4, 4));
		addList();
		addLogo();

	}

	private void addList() {
		JPanel leftPanel = new JPanel(new GridLayout(1, 1));
		TitledBorder titledBorder = BorderFactory
				.createTitledBorder("Top 10 Player");
		titledBorder.setTitleColor(Color.ORANGE);
		leftPanel.setBorder(titledBorder);
		leftPanel.setBackground(BACKGROUND_COLOR);

		JPanel listPanel = new JPanel(new GridLayout(10, 1, 4, 4));
		listPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		listPanel.setBackground(BACKGROUND_COLOR);
		client.write("#list#");
		client.write(String.valueOf(score));
		TopPlayers topPlayers = client.getTopPleayers();
		for (int i = 0; i < playersLabel.length; i++) {
			String tempName = topPlayers.getNames().get(i);
			if (!tempName.equals("")) {
				int tempScore = topPlayers.getScores().get(i);
				StringBuilder string = new StringBuilder();
				Formatter formatter = new Formatter(string);
				formatter.format("%2d)  Name :  %-18s  |  Score :  %18d", i, tempName, tempScore);
				playersLabel[i] = new JLabel(string.toString());
//				playersLabel[i] = new JLabel((i + 1) + ")  Name :  " + tempName
//						+ "  |  Score :  " + tempScore);
			} else {
				playersLabel[i] = new JLabel(
						(i + 1)
								+ ")  Name :  ------------------  |  Score :  ------------------");
			}
			
			playersLabel[i].setForeground(Color.YELLOW);
			listPanel.add(playersLabel[i]);
		}
		leftPanel.add(listPanel);
		add(leftPanel);
	}

	private void addLogo() {
		JPanel rightPanel = new JPanel(new GridLayout(1, 1));
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Server Image");
		titledBorder.setTitleColor(Color.ORANGE);
		rightPanel.setBorder(titledBorder);
		rightPanel.setBackground(BACKGROUND_COLOR);
		try {
			logo = ImageIO.read(client.getLogo());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logoLabel = new JLabel(new ImageIcon(logo));
		logoLabel.setBounds(335, 15, logo.getWidth(), logo.getHeight());
		rightPanel.add(logoLabel);
		add(rightPanel);
	}

}
