package labSearch;

import java.util.ArrayList;
import java.util.List;

public class SoftwareProcessor {

	private List<Software> swList;

	public SoftwareProcessor(List<Software> swList) {
		this.swList = swList;
	}

	public List<Lab> searchLab(String SoftwareName) {
		for (Software sw : swList) {
			if (sw.getName().equalsIgnoreCase(SoftwareName)) {
				return sw.getLabList();
			}
		}
		return null;
	}

	public List<Software> searchSoftware(String labName) {
		List<Software> toReturn = new ArrayList<>();
		for (Software s : swList) {
			for (Lab l : s.getLabList()) {
				if (l.getName().equalsIgnoreCase(labName))
					toReturn.add(s);
			}
		}
		return toReturn;
	}
}
