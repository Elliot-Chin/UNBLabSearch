package labSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import myUtilities.MyUtilities;

public class AddingComponentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField newSWTF;
	private JPanel labListContentPNL;
	private List<JCheckBox> checkBoxList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddingComponentFrame frame = new AddingComponentFrame();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
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
		setBounds(100, 100, 351, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel newSWPNL = new JPanel();
		newSWPNL.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"New Software Name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		newSWPNL.setBounds(4, 13, 321, 55);
		contentPane.add(newSWPNL);
		newSWPNL.setLayout(null);

		newSWTF = new JTextField();
		newSWTF.setFont(new Font("Consolas", Font.PLAIN, 12));
		newSWTF.setBackground(SystemColor.control);
		newSWTF.setBorder(null);
		newSWTF.setBounds(10, 16, 301, 28);
		newSWPNL.add(newSWTF);
		newSWTF.setColumns(10);

		JPanel labListPNL = new JPanel();
		labListPNL
				.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		labListPNL.setBounds(4, 83, 321, 291);
		contentPane.add(labListPNL);
		labListPNL.setLayout(null);

		JScrollPane labListSP = new JScrollPane();
		labListSP.setBorder(null);
		labListSP.setBounds(10, 16, 301, 264);
		labListPNL.add(labListSP);

		labListContentPNL = new JPanel();
		labListContentPNL.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labListSP.setViewportView(labListContentPNL);
		labListContentPNL.setLayout(new GridLayout(0, 2, 0, 8));

		JButton addLabBTN = new JButton("Add Lab");
		addLabBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newLabName = showInputDialogBox("New Lab Name", "New Lab");
				if (MyUtilities.isEmpty(newLabName))
					return;
				
			}
		});
		addLabBTN.setFont(new Font("Consolas", Font.PLAIN, 12));
		addLabBTN.setBounds(4, 385, 89, 23);
		contentPane.add(addLabBTN);

		JButton btnDeleteLab = new JButton("Delete");
		btnDeleteLab.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnDeleteLab.setBounds(114, 385, 89, 23);
		contentPane.add(btnDeleteLab);

		JButton btnDone = new JButton("Done");
		btnDone.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnDone.setBounds(236, 385, 89, 23);
		contentPane.add(btnDone);

		try {
			fillLabListContentPNL(FileDealer.readFromLabFile());
		} catch (IOException e) {
			UNBLabSearchFrame.warning("Error reading from lab file", 1);
			return;
		}
	}

	private void fillLabListContentPNL(List<Lab> labList) {
		checkBoxList = new ArrayList<>();
		for (Lab l : labList) {
			JLabel temp = new JLabel(l.getName());
			temp.setHorizontalTextPosition(JLabel.CENTER);
			temp.setFont(new Font("Consolas", Font.PLAIN, 12));
			labListContentPNL.add(temp);
			JCheckBox tempCB = new JCheckBox();
			checkBoxList.add(tempCB);
			labListContentPNL.add(tempCB);
			// note to self: use the index of the checked checkboxes from the checkbox list
			// to find the lab names, cuz the checkbox index = the labnames in lablist
		}
	}

	private String showInputDialogBox(String borderTitle, String dialogBoxTitle) {
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
}
