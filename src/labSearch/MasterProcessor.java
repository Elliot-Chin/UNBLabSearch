package labSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MasterProcessor {

	private List<Software> swList;
	private List<Lab> labList;

	/**
	 * Constructor
	 * 
	 * @param swList  - a list of software
	 * @param labList - a list of labs
	 */
	public MasterProcessor(List<Software> swList, List<Lab> labList) {
		this.swList = swList;
		this.labList = labList;
	}

	/**
	 * searches through each software and return the list of labs that the software
	 * is in
	 * 
	 * @param SoftwareName - the software to search
	 * @return the list of labs that the software is in
	 */
	private List<Lab> searchLab(String SoftwareName) {
		for (Software sw : swList) {
			if (sw.getName().equalsIgnoreCase(SoftwareName)) {
				return sw.getLabList();
			}
		}
		return null;
	}

	/**
	 * searches through each lab and returns the list of software that the lab has
	 * 
	 * @param labName - the lab name to search
	 * @return the list of software that is in the lab
	 */
	private List<Software> searchSoftware(String labName) {
		List<Software> toReturn = new ArrayList<>();
		for (Software s : swList) {
			for (Lab l : s.getLabList()) {
				if (l.getName().equalsIgnoreCase(labName))
					toReturn.add(s);
			}
		}
		return toReturn;
	}

	/**
	 * returns the size of the lab or software list
	 * 
	 * @param sizeOf - the type of list size to return
	 * @return the size of list
	 */
	public int getSize(String sizeOf) {
		return sizeOf.equalsIgnoreCase("Lab") ? labList.size() : swList.size();
	}

	/**
	 * uses both searchSoftware and searchLab to get a resultList and return it
	 * 
	 * @param query - the query to search (lab name / software name)
	 * @return the list of results (list of lab / software)
	 */
	public List<?> search(String query) {
		List<?> searchResults;
		searchResults = searchLab(query);
		if (searchResults == null) {
			try {
				searchResults = searchSoftware(query);
			} catch (NullPointerException e2) {
				return null;
			}
		}
		return searchResults;
	}

	/**
	 * returns the unique list of labs that contains software. LabList contains labs
	 * that does not have software in it yet.
	 * 
	 * @return the list of labs that contains at least 1 software in it
	 */
	public Set<String> getLabListFrmSW() {
		Set<String> toReturn = new HashSet<>();
		for (Software s : swList) {
			for (Lab l : s.getLabList()) {
				toReturn.add(l.getName());
			}
		}
		return toReturn;
	}

	/**
	 * returns the swList
	 * 
	 * @return swList
	 */
	public List<Software> getSWList() {
		return swList;
	}

	/**
	 * returns labList
	 * 
	 * @return labList
	 */
	public List<Lab> getLabList() {
		return labList;
	}

	/**
	 * adding a software / lab to the list and updating the file
	 * 
	 * @param type - the object itself (lab / software)
	 * @throws IOException
	 */
	public void add(Object type) throws IOException {
		if (type instanceof Lab) {
			labList.add((Lab) type);
			FileDealer.writeToLabFile(labList);
			return;
		} else if (type instanceof Software) {
			swList.add((Software) type);
			FileDealer.writeToFile(swList);
			return;
		}
	}

	/**
	 * removes a software / lab from the list and updating the file
	 * 
	 * @param itemName - the item to remove (lab name / software name)
	 * @param type     - the type of object (lab / software)
	 * @throws IOException
	 */
	public void remove(String itemName, String type) throws IOException {
		int labListSize = labList.size();
		int swListSize = swList.size();
		if (type.equalsIgnoreCase("Lab")) {
			for (Lab l : labList) {
				if (l.getName().equalsIgnoreCase(itemName)) { // remove lab from labList
					labList.remove(l);
					break;
				}
			}

			for (Software s : swList) {
				for (Lab l : s.getLabList()) {
					if (l.getName().equalsIgnoreCase(itemName)) {
						s.removeLab(l);
						break;
					}
				}
			}

			if (labList.size() == labListSize) // check if thre is a change in the size, else just stop
				return;
			FileDealer.writeToLabFile(labList);
			return;
		} else {
			for (Software s : swList) {
				if (s.getName().equalsIgnoreCase(itemName)) {
					swList.remove(s);
					break;
				}
			}
			if (swList.size() == swListSize) // check if thre is a change in the size, else just stop
				return;
			FileDealer.writeToFile(swList);
			return;
		}
	}
}
