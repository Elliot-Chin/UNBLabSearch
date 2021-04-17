package labSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SoftwareProcessor {

	private List<Software> swList;

	public SoftwareProcessor(List<Software> swList) {
		this.swList = swList;
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

	public Set<String> getLabList() {
		Set<String> toReturn = new HashSet<>();
		for (Software sw : swList) {
			toReturn.add(sw.getName());
		}
		return toReturn;
	}
}
