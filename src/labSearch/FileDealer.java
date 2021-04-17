package labSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDealer {

	static final String LAB_FILE = "labList.txt";
	static final String SW_FILE = "softwareList.txt";

	public static void writeToFile(List<Software> swList) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(SW_FILE)));
		for (Software s : swList) {
			bw.write(s.toFileFormat());
		}
		bw.close();
	}

	public static List<Software> readFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(SW_FILE)));
		List<Software> toReturn = new ArrayList<>();
		String line, temp[];
		int index = 0;
		while ((line = br.readLine()) != null) {
			temp = line.split(",");
			toReturn.add(new Software(temp[0]));
			for (int i = 1; i < temp.length; i++) {
				toReturn.get(index).addLab(new Lab(temp[i]));
			}
			index++;
		}
		br.close();
		return toReturn;
	}

	public static void writeToLabFile(List<Lab> labList) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(LAB_FILE)));
		for (Lab l : labList) {
			bw.write(l.toFileFormat());
		}
		bw.close();
	}

	public static List<Lab> readFromLabFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(LAB_FILE)));
		List<Lab> toReturn = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			toReturn.add(new Lab(line));
		}
		br.close();
		return toReturn;
	}

	public static void init() throws IOException {
		if (!new File(LAB_FILE).exists()) {
			new File(LAB_FILE).createNewFile();
		}
		if (!new File(SW_FILE).exists()) {
			new File(SW_FILE).createNewFile();
		}
	}
}
