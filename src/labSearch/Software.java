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
		return labList;
	}

	public void removeLab(Lab lab) {
		labList.remove(lab);
	}

	public String toFileFormat() {
		String toReturn = "";
		toReturn += name;
		if (labList.size() > 0)
			toReturn += ",";
		for (int i = 0; i < labList.size(); i++) {
			toReturn += labList.get(i).getName();
			if (i < labList.size() - 1)
				toReturn += ",";
		}
		toReturn += System.lineSeparator();
		return toReturn;
	}

	public String toString() {
		String toReturn = "";
		return toReturn;
	}
}
