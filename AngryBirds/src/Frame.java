import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Frame extends JFrame {

	public Frame() {
		setTitle("Angry Birds - Demo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setContentPane(new Panel());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Frame();
			}
		});
	}

}
