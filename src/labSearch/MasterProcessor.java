package labSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MasterProcessor {

	private List<Software> swList;
	private List<Lab> labList;

	public MasterProcessor(List<Software> swList, List<Lab> labList) {
		this.swList = swList;
		this.labList = labList;
	}

	private List<Lab> searchLab(String SoftwareName) {
		for (Software sw : swList) {
			if (sw.getName().equalsIgnoreCase(SoftwareName)) {
				return sw.getLabList();
			}
		}
		return null;
	}

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

	public int getSize(String sizeOf) {
		return sizeOf.equalsIgnoreCase("Lab") ? labList.size() : swList.size();
	}

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

	public Set<String> getLabListFrmSW() {
		Set<String> toReturn = new HashSet<>();
		for (Software s : swList) {
			for (Lab l : s.getLabList()) {
				toReturn.add(l.getName());
			}
		}
		return toReturn;
	}

	public List<Software> getSWList() {
		return swList;
	}

	public List<Lab> getLabList() {
		return labList;
	}

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

	public void remove(String itemName, String type) throws IOException {
		int labListSize = labList.size();
		int swListSize = swList.size();
		if (type.equalsIgnoreCase("Lab")) {
			for (Lab l : labList) {
				if (l.getName().equalsIgnoreCase(itemName)) { // remove lab from labList
					labList.remove(l);
					return;
				}
			}

			for (Software s : swList) {
				for (Lab l : s.getLabList()) {
					if (l.getName().equalsIgnoreCase(itemName)) {
						s.removeLab(l);
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
					return;
				}
			}
			if (swList.size() == swListSize) // check if thre is a change in the size, else just stop
				return;
			FileDealer.writeToFile(swList);
			return;
		}
	}
}
