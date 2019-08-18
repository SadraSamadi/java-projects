import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Start extends JFrame implements ActionListener {
	
	public Start() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1, 5, 5));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setPreferredSize(new Dimension(200, 200));
		panel.setBackground(Color.BLACK);
		
		JButton easy = new JButton("Easy");
		JButton medium = new JButton("Medium");
		JButton hard = new JButton("Hard");
		
		easy.setBackground(Color.GREEN);
		easy.setActionCommand("easy");
		easy.addActionListener(this);
		
		medium.setBackground(Color.YELLOW);
		medium.setActionCommand("medium");
		medium.addActionListener(this);
		
		hard.setBackground(Color.RED);
		hard.setActionCommand("hard");
		hard.addActionListener(this);
		
		panel.add(easy);
		panel.add(medium);
		panel.add(hard);
		
		setTitle("Start");
		setLocation(500, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(panel);
		pack();
		setResizable(false);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Start start = new Start();
				start.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final int range;
		final float speed;
		switch (arg0.getActionCommand()) {
		case "easy":
			range = 110;
			speed = 4;
			break;
		case "medium":
			range = 120;
			speed = 3;
			break;
		case "hard":
			range = 130;
			speed = 2;
			break;
		default:
			range = 0;
			speed = 0;
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dispose();
				Game game = new Game(range, speed);
				game.setVisible(true);
			}
		});
	}
	
}
