package labSearch;

import java.util.ArrayList;
import java.util.List;

public class Software {

	private String name;
	private List<Lab> labList;

	/**
	 * Constructor. Creates an empty lab list.
	 * 
	 * @param name - the name of the software
	 */
	public Software(String name) {
		this.name = name;
		labList = new ArrayList<>();
	}

	/**
	 * adds a lab to the labList
	 * 
	 * @param lab - the lab to be added to the list
	 */
	public void addLab(Lab lab) {
		labList.add(lab);
	}

	/**
	 * sets the new name for the software
	 * 
	 * @param newName
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * returns the name of the software
	 * 
	 * @return the name of the software
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the list of labs that the software is in
	 * 
	 * @return the list of labs that the software is in
	 */
	public List<Lab> getLabList() {
		return labList;
	}

	/**
	 * remove a lab that the software is not in anymore
	 * 
	 * @param lab - the lab to remove the software from
	 */
	public void removeLab(Lab lab) {
		labList.remove(lab);
	}

	/**
	 * format the string to be written to a file
	 * 
	 * @return a formatted string to be written to the file
	 */
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
}
