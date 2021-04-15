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

	private static final String LAB_FILE = "";

	public void writeToFile(List<Software> swList) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(LAB_FILE)));
		for (Software s : swList) {
			bw.write(s.toFileFormat());
		}
		bw.close();
	}

	public List<Software> readFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(LAB_FILE)));
		List<Software> toReturn = new ArrayList<>();
		String line, temp[];
		int index = 0;
		while ((line = br.readLine()) != null) {
			temp = line.split(",");
			toReturn.add(new Software(temp[0]));
			for (int i = 1; i < temp.length; i++) {
				toReturn.get(index).addLab(new Lab(temp[i]));
			}
		}
		return toReturn;
	}
}
