package labSearch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UNBLabSearchFrame {

	private JFrame frmUnbLabSearch;
	private static final String version = "1.0";
	private JTextField textField;
	private static JLabel warningLBL;

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
		frmUnbLabSearch.setBounds(100, 100, 426, 448);
		frmUnbLabSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUnbLabSearch.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Search Results", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 82, 390, 274);
		frmUnbLabSearch.getContentPane().add(panel);
		panel.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		textArea.setBorder(null);
		textArea.setBounds(10, 16, 352, 210);
		panel.add(textArea);

		warningLBL = new JLabel("");
		warningLBL.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		warningLBL.setHorizontalAlignment(SwingConstants.CENTER);
		warningLBL.setFont(new Font("Consolas", Font.PLAIN, 12));
		warningLBL.setBounds(10, 367, 390, 33);
		frmUnbLabSearch.getContentPane().add(warningLBL);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Search Bar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 16, 390, 55);
		frmUnbLabSearch.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField.setBounds(10, 16, 352, 28);
		panel_1.add(textField);
		textField.setBackground(SystemColor.control);
		textField.setBorder(null);
		textField.setColumns(10);
	}

	public static void warning(String warningText, int i) {
		switch (i) {
		case 0:
			warningLBL.setForeground(Color.BLUE);
			break;
		case 1:
			warningLBL.setForeground(Color.RED);
			break;
		} // end switch
		warningLBL.setText(warningText);
		new Thread(() -> { // create new thread that has a 3 second delay
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
			}
			warningLBL.setText("");
		}).start();
	}
}
