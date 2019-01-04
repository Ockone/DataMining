package filesSort;

import java.util.HashMap;

public class TestMap {
	public static void main(String argv[]) {
		HashMap<String, Double> fileMap = new HashMap<String, Double>();
		fileMap.put("1", 1.0);
		Double i = fileMap.get("1");
		i++;
		fileMap.put("1", i);
		System.out.println(fileMap.get("1"));
	}

}
