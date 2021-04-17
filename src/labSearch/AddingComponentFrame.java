package labSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField newSWTF;
	private JPanel labListContentPNL;
	private List<JCheckBox> checkBoxList;

	/**
	 * Create the frame.
	 */
	public AddingComponentFrame() {

		setTitle("Add New Software | Lab");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 351, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

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
		labListPNL.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Lab List",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		labListPNL.setBounds(4, 83, 321, 291);
		contentPane.add(labListPNL);
		labListPNL.setLayout(null);

		JScrollPane labListSP = new JScrollPane();
		labListSP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labListSP.setBorder(null);
		labListSP.setBounds(10, 22, 301, 258);
		labListSP.setAutoscrolls(true);
		labListPNL.add(labListSP);
		labListSP.getVerticalScrollBar().setUnitIncrement((int) Math.ceil(UNBLabSearchFrame.mp.getSize("Lab") / 7));

		labListContentPNL = new JPanel();
		labListContentPNL.setFont(new Font("Consolas", Font.PLAIN, 12));
		labListSP.setViewportView(labListContentPNL);
		labListContentPNL.setLayout(new GridLayout(0, 2, 0, 8));

		JButton addLabBTN = new JButton("Add Lab");
		addLabBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addLabBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newLabName = showInputDialogBox("New Lab Name", "New Lab");
				if (MyUtilities.isEmpty(newLabName))
					return;
				try {
					UNBLabSearchFrame.mp.add(new Lab(newLabName));
					dispose();
					AddingComponentFrame tempNewFrame = new AddingComponentFrame();
					tempNewFrame.setVisible(true);
					setLocation(UNBLabSearchFrame.frmUnbLabSearch.getX() + UNBLabSearchFrame.frmUnbLabSearch.getY(),
							UNBLabSearchFrame.frmUnbLabSearch.getY());

				} catch (IOException e1) {
					UNBLabSearchFrame.warning("Error writing lab to file", 2);
					return;
				}
			}
		});
		addLabBTN.setFont(new Font("Consolas", Font.PLAIN, 12));
		addLabBTN.setBounds(4, 385, 89, 23);
		contentPane.add(addLabBTN);

		JButton btnDeleteLab = new JButton("Delete");
		btnDeleteLab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String toDelete = UNBLabSearchFrame.showInputDialogBox("Enter [SW | LB] [NAME] to delete", "Delete");
				if (MyUtilities.isEmpty(toDelete))
					return;
				String temp[] = toDelete.split("\\s+", 2);
				if (temp.length != 2) {
					UNBLabSearchFrame
							.warning("Please enter [SW] (software) or [LB] Lab followed by the [NAME] to delete", 1);
					return;
				}
				String option = null;
				if (temp[0].equals("SW"))
					option = "Software";
				else if (temp[0].equals("LB"))
					option = "Lab";
				else {
					UNBLabSearchFrame
							.warning("Please enter [SW] (software) or [LB] Lab followed by the [NAME] to delete", 1);
					return;
				}
				try {
					UNBLabSearchFrame.mp.remove(temp[1], option);
					dispose();
					AddingComponentFrame tempNewFrame = new AddingComponentFrame();
					tempNewFrame.setVisible(true);
					setLocation(UNBLabSearchFrame.frmUnbLabSearch.getX() + UNBLabSearchFrame.frmUnbLabSearch.getY(),
							UNBLabSearchFrame.frmUnbLabSearch.getY());
				} catch (IOException e1) {
					UNBLabSearchFrame.warning("Unable to write to file", 3);
					return;
				}
			}
		});
		btnDeleteLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeleteLab.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnDeleteLab.setBounds(114, 385, 89, 23);
		contentPane.add(btnDeleteLab);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			List<Integer> indexList = new ArrayList<>();
			List<Lab> addToLabList = new ArrayList<>(); // this arrayList takes the list of new labs to add software to

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < checkBoxList.size(); i++) { // loop to get all the index of checked boxes
					if (checkBoxList.get(i).isSelected()) {
						indexList.add(i);
					}
				}

				if (indexList.size() > 0) { // means there is at least 1 lab selected
					if (MyUtilities.isEmpty(newSWTF)) {
						UNBLabSearchFrame.warning("Please enter new software name before adding to lab", 1);
						return;
					}
				} else if (indexList.size() == 0) { // if no labs are selected
					UNBLabSearchFrame.warning("Please select at least 1 lab to be added to", 1);
					return;
				}

				for (int i = 0; i < indexList.size(); i++) { // this creates all the labs objects
					addToLabList.add(new Lab(UNBLabSearchFrame.mp.getLabList().get(indexList.get(i)).getName()));
				}

				Software s = new Software(newSWTF.getText());
				for (Lab l : addToLabList) { // add the selected labs to the sw
					s.addLab(l);
				}

				try {
					UNBLabSearchFrame.mp.add(s);
				} catch (IOException e1) {
					UNBLabSearchFrame.warning("Unable to add software to file", 2);
					return;
				}
				dispose();
				return;
			}
		});
		btnDone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDone.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnDone.setBounds(236, 385, 89, 23);
		contentPane.add(btnDone);

		try {
			fillLabListContentPNL(FileDealer.readFromLabFile());
		} catch (IOException e) {
			UNBLabSearchFrame.warning("Error reading from lab file", 2);
			return;
		}

		setLocation(UNBLabSearchFrame.frmUnbLabSearch.getX() + UNBLabSearchFrame.frmUnbLabSearch.getY(),
				UNBLabSearchFrame.frmUnbLabSearch.getY());
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
