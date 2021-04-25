package labSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import myUtilities.MyUtilities;

public class UNBLabSearchFrame {

	/**
	 * Not private as its used in AddingComponentFrame
	 */
	static JFrame frmUnbLabSearch;
	static MasterProcessor mp;

	/**
	 * Declaring variables
	 */
	private static final String version = "1.0";
	private JTextField searchTF;
	private static JTextArea warningLBL;
	private static JTextArea searchResultsTA;
	private final static String RIGHT_ARROW = "\u2192";
	private JScrollPane searchResultsSP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
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
		frmUnbLabSearch.setTitle("UNB Lab Finder v" + version);
		frmUnbLabSearch.setBounds(100, 100, 339, 425);
		frmUnbLabSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUnbLabSearch.getContentPane().setLayout(null);
		frmUnbLabSearch.setResizable(false);
		frmUnbLabSearch.setIconImage(new ImageIcon("Image/Icon.png").getImage());
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

		warningLBL = new JTextArea();
		warningLBL.setEditable(false);
		warningLBL.setBackground(SystemColor.control);
		warningLBL.setBorder(null);
		warningLBL.addMouseListener(new MouseAdapter() {

			int clickCount = 0; // to keep track of number of clicks

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) { // only count the number of right clicks
					clickCount++;
				}
				if (clickCount == 3) { // check if the number of clicks == 3
					clickCount = 0; // reset the count when its == 3
					String command = showInputDialogBox("Enter Command", "Enter Command"); // display command box to
																							// enter command
					switch (command) { // secret command list
					case "open Lab File":
						try {
							Desktop.getDesktop().open(new File(FileDealer.LAB_FILE));
							return;
						} catch (IOException e1) {
							warning("something is really wrong (can't find file)", 1);
							return;
						}
					case "open SW File":
						try {
							Desktop.getDesktop().open(new File(FileDealer.SW_FILE));
							return;
						} catch (IOException e1) {
							warning("something is really wrong (can't find file)", 1);
							return;
						}
					case "modify":
						AddingComponentFrame acf = new AddingComponentFrame();
						acf.setVisible(true);
						return;
					default: // warning will be displayed if the command entered does not match any of the
								// above (case sensitive)
						warning("Do not do this again!", 1);
						return;
					}
				}
			}
		});
		warningLBL.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		warningLBL.setLineWrap(true);
		warningLBL.setWrapStyleWord(true);
		warningLBL.setFont(new Font("Consolas", Font.PLAIN, 12));
		warningLBL.setBounds(10, 332, 303, 53);
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
				List<?> searchResults = mp.search(searchTF.getText());
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
		btnNewButton.setToolTipText("Clear search bar and search results");
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
			FileDealer.init(); // check for existance of files, if not create the files
		} catch (IOException e1) {
			warning("Unable to create files", 2);
			getStatus();
			return;
		}

		try {
			mp = new MasterProcessor(FileDealer.readFromFile(), FileDealer.readFromLabFile()); // reading the data from
																								// files. Files should
																								// exist at this point.
		} catch (IOException e1) {
			warning("Unable to read from file", 2);
			getStatus(); // show today's date
			return;
		}

		getStatus(); // show today's date
	}

	/**
	 * displays the list of software / labs found
	 * 
	 * @param title      - the entered search query will appear as the title of the
	 *                   search results
	 * @param resultList - a list containing all the lab / software found
	 * @param type       - type of list (Software / Lab)
	 */
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
				toDisplay += RIGHT_ARROW + "   " + ((Software) o).getName() + System.lineSeparator();
			} else if (type.equalsIgnoreCase("Lab")) {
				toDisplay += RIGHT_ARROW + " " + ((Lab) o).getName();
			}
			toDisplay += System.lineSeparator();
		}

		searchResultsTA.setText(toDisplay);
	}

	/**
	 * displays warning text at the bottom of the GUI (in the warning label)
	 * 
	 * @param warningText - the text to display in the warning label
	 * @param i           - the selection for color of text, 1 for red (failed), 2
	 *                    for blue (pass), 3 for yellow (techincal error)
	 */
	public static void warning(String warningText, int i) {
		switch (i) {
		case 0:
			warningLBL.setForeground(Color.BLUE);
			break;
		case 1:
			warningLBL.setForeground(Color.RED);
			break;
		case 2:
			warningLBL.setForeground(Color.ORANGE);
			warningText = "Contact Elliot: " + warningText;
			break;
		}
		warningLBL.setText(warningText);
		new Thread(() -> { // create new thread that has a 3 second delay
			try {
				if (i == 2)
					Thread.sleep(5000);
				else
					Thread.sleep(3000);
			} catch (InterruptedException ex) {
			}
			warningLBL.setText("");
		}).start();
	}

	/**
	 * displays a dialog box that returns a string
	 * 
	 * @param borderTitle    - the title of the border of the component
	 * @param dialogBoxTitle - the title of the dialog box
	 * @return the string entered by the user in the dialog box
	 */
	public static String showInputDialogBox(String borderTitle, String dialogBoxTitle) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(153, 180, 209), new Color(244, 247, 252)), borderTitle,
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		JTextField tf = new JTextField();
		tf.setBackground(SystemColor.control);
		tf.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
		panel.add(tf, BorderLayout.SOUTH);
		tf.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						tf.requestFocus();
					}
				});
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}
		});
		JOptionPane.showMessageDialog(null, panel, dialogBoxTitle, JOptionPane.PLAIN_MESSAGE);
		String toReturn = tf.getText();
		return toReturn;
	}

	/**
	 * sets the current date in the search results panel
	 */
	private static void getStatus() {
		String toDisplay = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		toDisplay += "Date: " + dtf.format(now) + System.lineSeparator();
		searchResultsTA.setText(toDisplay);
	}
}
