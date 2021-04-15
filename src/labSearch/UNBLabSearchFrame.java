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

import myUtilities.MyUtilities;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class UNBLabSearchFrame {

	private JFrame frmUnbLabSearch;
	private static final String version = "1.0";
	private JTextField searchTF;
	private static JLabel warningLBL;
	private static SoftwareProcessor sp;
	private static JTextArea searchResultsTA;
	private final static String RIGHT_ARROW = "\u2192";
	private JScrollPane searchResultsSP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UNBLabSearchFrame window = new UNBLabSearchFrame();
					window.frmUnbLabSearch.setVisible(true);
					window.frmUnbLabSearch.setLocationRelativeTo(null);
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
		frmUnbLabSearch.setBounds(100, 100, 339, 425);
		frmUnbLabSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUnbLabSearch.getContentPane().setLayout(null);
		frmUnbLabSearch.setResizable(false);

		JPanel searchResultsPNL = new JPanel();
		searchResultsPNL.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Search Results", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		searchResultsPNL.setBounds(10, 82, 303, 239);
		frmUnbLabSearch.getContentPane().add(searchResultsPNL);
		searchResultsPNL.setLayout(null);

		searchResultsSP = new JScrollPane();
		searchResultsSP.setBorder(null);
		searchResultsSP.setBounds(10, 16, 283, 212);
		searchResultsPNL.add(searchResultsSP);

		searchResultsTA = new JTextArea();
		searchResultsTA.setEditable(false);
		searchResultsSP.setViewportView(searchResultsTA);
		searchResultsTA.setFont(new Font("Consolas", Font.PLAIN, 12));
		searchResultsTA.setBackground(SystemColor.control);
		searchResultsTA.setBorder(null);

		warningLBL = new JLabel("");
		warningLBL.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		warningLBL.setHorizontalAlignment(SwingConstants.CENTER);
		warningLBL.setFont(new Font("Consolas", Font.PLAIN, 12));
		warningLBL.setBounds(10, 332, 303, 33);
		frmUnbLabSearch.getContentPane().add(warningLBL);

		JPanel searchPNL = new JPanel();
		searchPNL.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Search Bar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		searchPNL.setBounds(10, 16, 303, 55);
		frmUnbLabSearch.getContentPane().add(searchPNL);
		searchPNL.setLayout(null);

		searchTF = new JTextField();
		searchTF.setFont(new Font("Consolas", Font.PLAIN, 12));
		searchTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<?> searchResults = sp.search(searchTF.getText());
				String listType = null;
				try {
					listType = searchResults.get(0).getClass().getSimpleName();
				} catch (IndexOutOfBoundsException e1) { // when the search does not exist
					warning("Lab / Software entered is not found", 1);
					return;
				}
				displaySearch(searchTF.getText().toUpperCase(), searchResults, listType);
				searchResultsTA.setCaretPosition(0);
			}
		});
		searchTF.setBounds(10, 16, 198, 28);
		searchPNL.add(searchTF);
		searchTF.setBackground(SystemColor.control);
		searchTF.setBorder(null);
		searchTF.setColumns(10);

		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTF.setText("");
				getStatus();
				searchTF.requestFocus();
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnNewButton.setBounds(218, 16, 75, 23);
		searchPNL.add(btnNewButton);

//Initial run starts here, setup of frame above
		try {
			sp = new SoftwareProcessor(FileDealer.readFromFile());
		} catch (IOException e1) {
			warning("Unable to read from file", 1);
			return;
		}

		getStatus();
	}

	private static void displaySearch(String title, List<?> resultList, String type) {
		String toDisplay = "";
		if (type.equalsIgnoreCase("Software"))
			toDisplay = MyUtilities.genLine(35, '-', false, 20) + "\nLab: " + title + "\n"
					+ MyUtilities.genLine(35, '-', false, 20) + System.lineSeparator();
		else if (type.equalsIgnoreCase("Lab"))
			toDisplay = MyUtilities.genLine(35, '-', false, 20) + "\nSoftware: " + title + "\n"
					+ MyUtilities.genLine(35, '-', false, 20) + System.lineSeparator();

		for (Object o : resultList) {
			if (type.equalsIgnoreCase("Software")) {
				toDisplay += RIGHT_ARROW + " " + ((Software) o).getName();
			} else if (type.equalsIgnoreCase("Lab")) {
				toDisplay += RIGHT_ARROW + " " + ((Lab) o).getName();
			}
			toDisplay += System.lineSeparator();
		}

		searchResultsTA.setText(toDisplay);
	}

	public static void warning(String warningText, int i) {
		switch (i) {
		case 0:
			warningLBL.setForeground(Color.BLUE);
			break;
		case 1:
			warningLBL.setForeground(Color.RED);
			break;
		}
		warningLBL.setText(warningText);
		new Thread(() -> { // create new thread that has a 3 second delay
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
			}
			warningLBL.setText("");
		}).start();
	}

	private static void getStatus() {
		String toDisplay = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		toDisplay += "Date: " + dtf.format(now) + System.lineSeparator();
		searchResultsTA.setText(toDisplay);
	}
}
