package labSearch;

import java.util.ArrayList;
import java.util.List;

public class Software {

	private String name;
	private List<Lab> labList;

	public Software(String name) {
		this.name = name;
		labList = new ArrayList<>();
	}

	public void addLab(Lab lab) {
		labList.add(lab);
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public String getName() {
		return name;
	}

	public List<Lab> getLabList() {
		return getLabList();
	}

	public void removeLab(String labName) {
		for (Lab l : labList)
			if (labName.equalsIgnoreCase(l.getName())) {
				labList.remove(l);
				return;
			}
	}

	public String toFileFormat() {
		String toReturn = "";
		toReturn += name + ",";
		for (Lab l : labList) {
			toReturn += l.getName() + ",";
		}
		toReturn += System.lineSeparator();

		return toReturn;
	}
}
