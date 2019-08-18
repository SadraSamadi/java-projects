package aa;

import javax.swing.JFrame;

public class Start extends JFrame {

	private static final long serialVersionUID = 1L;

	public Start() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("aa");
		setContentPane(new Panel());
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Start();
			}
		});
	}

}
