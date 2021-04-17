package labSearch;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.io.IOException;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AddingComponentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField newSWTF;
	private JPanel labListContentPNL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddingComponentFrame frame = new AddingComponentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddingComponentFrame() {
		setTitle("Add New Software | Lab");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel newSWPNL = new JPanel();
		newSWPNL.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"New Software Name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		newSWPNL.setBounds(4, 13, 422, 55);
		contentPane.add(newSWPNL);
		newSWPNL.setLayout(null);

		newSWTF = new JTextField();
		newSWTF.setFont(new Font("Consolas", Font.PLAIN, 12));
		newSWTF.setBackground(SystemColor.control);
		newSWTF.setBorder(null);
		newSWTF.setBounds(10, 16, 402, 28);
		newSWPNL.add(newSWTF);
		newSWTF.setColumns(10);

		JPanel labListPNL = new JPanel();
		labListPNL
				.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		labListPNL.setBounds(4, 83, 436, 428);
		contentPane.add(labListPNL);
		labListPNL.setLayout(null);

		JScrollPane labListSP = new JScrollPane();
		labListSP.setBounds(6, 16, 424, 406);
		labListPNL.add(labListSP);

		labListContentPNL = new JPanel();
		labListContentPNL.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labListSP.setViewportView(labListContentPNL);
		labListContentPNL.setLayout(new GridLayout(0, 2, 0, 0));

		try {
			fillLabListContentPNL(FileDealer.readFromLabFile());
		} catch (IOException e) {
			UNBLabSearchFrame.warning("Error reading from lab file", 1);
			return;
		}
	}

	private void fillLabListContentPNL(List<Lab> labList) {
		for (Lab l : labList) {
			labListContentPNL.add(new JLabel(l.getName()));
			labListContentPNL.add(new JCheckBox());
		}
	}
}
