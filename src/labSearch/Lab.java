package labSearch;

public class Lab {

	private String labName;

	/**
	 * Constructor
	 * 
	 * @param labName - the lab name
	 */
	public Lab(String labName) {
		this.labName = labName;
	}

	/**
	 * set the new name for the lab
	 * 
	 * @param newName - the new name for the lab
	 */
	public void setName(String newName) {
		labName = newName;
	}

	/**
	 * returns the name of the lab
	 * 
	 * @return the name of the lab
	 */
	public String getName() {
		return labName;
	}

	/**
	 * format the string to write to file
	 * 
	 * @return the formatted string to write to file
	 */
	public String toFileFormat() {
		return labName + System.lineSeparator();
	}
}
