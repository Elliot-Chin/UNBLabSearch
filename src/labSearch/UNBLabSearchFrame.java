package labSearch;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class UNBLabSearchFrame {

	private JFrame frmUnbLabSearch;
	private static final String version = "1.0";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UNBLabSearchFrame window = new UNBLabSearchFrame();
					window.frmUnbLabSearch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UNBLabSearchFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUnbLabSearch = new JFrame();
		frmUnbLabSearch.setTitle("UNB Lab Search v" + version);
		frmUnbLabSearch.setBounds(100, 100, 451, 443);
		frmUnbLabSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
