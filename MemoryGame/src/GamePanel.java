import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class GamePanel extends JPanel implements Runnable, ActionListener {

	private final int WIDTH = 600;
	private final int HEIGHT = 600;
	private final int N;
	private final String NAME;

	private int score = 0;
	private GridLayout menuBarLayout;
	private JMenuBar menuBar;
	private HashMap<Integer, JButton> cells = new HashMap<Integer, JButton>();
	private ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
	private ImageIcon hide;
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private Thread thread;
	private JProgressBar progressBar;
	private JLabel loadingLabel;
	private JLabel scoreLabel;
	private JButton newGameButton;
	private JButton highScoresButton;
	private JLabel timerLabel;
	private Timer timer;
	private long time = 0;
	private boolean gameStarted = false;
	private boolean firstChoice = true;
	private boolean secondChoiceFinished = true;
	private int id1;
	private int id2;
	private int founded = 0;
	private JFrame frame;
	private Client client;

	public GamePanel(String name, Integer n, JFrame frame, Client client) {
		N = n;
		NAME = name;
		this.frame = frame;
		this.client = client;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridLayout(N, N, 2, 2));
		setBorder(new EmptyBorder(4, 4, 4, 4));
		setFocusable(true);
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(50);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(600, 30));
		menuBar.setBackground(Color.YELLOW);
		menuBar.setBorder(new EmptyBorder(2, 2, 2, 2));
		menuBarLayout = new GridLayout(1, 2);
		menuBar.setLayout(menuBarLayout);
		loadingLabel = new JLabel("Loading ...");
		menuBar.add(loadingLabel);
		menuBar.add(progressBar);
		for (int i = 1; i <= N * N; i += 2) {
			keys.add(i);
			keys.add(i * 2);
		}
		for (int i : keys) {
			JButton button = new JButton();
			button.setActionCommand(String.valueOf(i));
			button.addActionListener(this);
			cells.put(i, button);
		}
		ArrayList<Integer> temp = new ArrayList<Integer>(keys);
		Collections.shuffle(temp);
		for (int i : temp)
			add(cells.get(i));
		frame.setContentPane(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menuBar);
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		int size = cells.get(1).getHeight() - (94 - 7 * N) / 4;
		try {
			hide = new ImageIcon(ImageIO.read(
					new File("res\\question_mark.png")).getScaledInstance(size,
					size, Image.SCALE_SMOOTH));
		} catch (IOException e1) {
		}
		for (int i = 0; i < 50; i++)
			try {
				BufferedImage bufferedImage = ImageIO.read(new File(
						"res\\animal_" + i + ".png"));
				icons.add(new ImageIcon(bufferedImage.getScaledInstance(size,
						size, Image.SCALE_SMOOTH)));
				progressBar.setValue(progressBar.getValue() + 1);
			} catch (IOException e) {
			}
		Collections.shuffle(icons);
		for (int i = 0; i < N * N; i++) {
			cells.get(keys.get(i)).setBackground(Color.WHITE);
			cells.get(keys.get(i)).setIcon(icons.get(i / 2));
		}
		delay(N / 2 * 1000);
		for (JButton btn : cells.values())
			btn.setIcon(hide);
		menuBar.remove(progressBar);
		menuBar.remove(loadingLabel);
		menuBarLayout.setColumns(5);
		menuBar.add(new JLabel("Name : " + NAME));
		scoreLabel = new JLabel("Score : 0");
		menuBar.add(scoreLabel);
		timerLabel = new JLabel("Time : 0");
		menuBar.add(timerLabel);
		newGameButton = new JButton("New Game");
		newGameButton.setActionCommand("ng");
		newGameButton.addActionListener(this);
		menuBar.add(newGameButton);
		highScoresButton = new JButton("High Scores");
		highScoresButton.setActionCommand("hs");
		highScoresButton.addActionListener(this);
		menuBar.add(highScoresButton);
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				timerLabel.setText("Time : " + time++);
			}
		}, 0, 1000);
		revalidate();
		gameStarted = true;
	}

	public static void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameStarted) {
			if (secondChoiceFinished) {
				if (firstChoice) {
					id1 = Integer.parseInt(e.getActionCommand());
					cells.get(id1).setIcon(icons.get(getIndex(id1)));
					firstChoice = false;
				} else {
					id2 = Integer.parseInt(e.getActionCommand());
					if (id1 != id2) {
						cells.get(id2).setIcon(icons.get(getIndex(id2)));
						Thread thread = new Thread(new Runnable() {

							@Override
							public void run() {
								delay(1000);
								if (id1 == 2 * id2 || id1 == id2 / 2) {
									cells.get(id1).setVisible(false);
									cells.get(id2).setVisible(false);
									positiveScore();
									founded++;
								} else {
									cells.get(id1).setIcon(hide);
									cells.get(id2).setIcon(hide);
									negativeScore();
								}
								secondChoiceFinished = true;
								firstChoice = true;
								if (founded == N * N / 2)
									endGame();
							}
						});
						thread.start();
						secondChoiceFinished = false;
					}
				}
			}
		}
	}

	private void endGame() {
		timer.cancel();
		JOptionPane.showMessageDialog(null, "end");
		frame.setJMenuBar(null);
		frame.setContentPane(new EndPanel(client, score));
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	private void positiveScore() {
		score += N;
		scoreLabel.setText("Score : " + score);
	}

	private void negativeScore() {
		score--;
		scoreLabel.setText("Score : " + score);
	}

	private int getIndex(int key) {
		int index = 0;
		for (int i = 0; i < N * N; i++) {
			if (keys.get(i) == key) {
				index = i / 2;
				break;
			}
		}
		return index;
	}

}
