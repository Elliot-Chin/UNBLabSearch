package labSearch;

public class Lab {

	private String labName;

	public Lab(String labName) {
		this.labName = labName;
	}

	public void setName(String newName) {
		labName = newName;
	}

	public String getName() {
		return labName;
	}

	public String toFileFormat() {
		return labName + System.lineSeparator();
	}
}
