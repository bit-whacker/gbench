package org.khu.benchmark.gbench;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.khu.benchmark.gbench.similarity.Similarity;

public class ThresholdClustering {
	private int clusterID = 0;
	private Map<Integer, Integer> cluster = new HashMap<Integer, Integer>();

	public Map<Integer, Integer> cluster(List<String> propertyList, double threshold, Similarity.SIMILARITY_MEASURE sm) {
		long startTime = System.currentTimeMillis();
		clusterID = 0;
		
		String com = ",";
		StringBuilder build = new StringBuilder();
		for (int i = 1; i <= propertyList.size(); i++) {
			build.append(com).append(i);
		}
		build.append("\n");

		toNewCluster(0);
		for (int row = 1; row < propertyList.size(); row++) {
			double maxSim = 0.0;
			int maxPID = 0;
			for (int col = 0; col < row; col++) {
				double sim = Similarity.measure(propertyList.get(row), propertyList.get(col), sm);
				if (sim > maxSim) {
					maxSim = sim;
					maxPID = col;
				}
			}
			if (maxSim >= threshold) {
				int cid = cluster.get(maxPID);
				addToCluster(row, cid);
			} else {
				toNewCluster(row);
			}
		}

		long timeTaken = System.currentTimeMillis() - startTime;
		timeTaken = timeTaken / 1000;

		System.out.println("time taken: " + timeTaken + "s");
		int clusters = clusterID - 1;
		System.out.println("number of clusters: [" + clusters + "]");
		
		return cluster;
	}

	private int toNewCluster(int pid) {
		cluster.put(pid, clusterID);
		clusterID++;
		return clusterID - 1;
	}

	private int addToCluster(int pid, int cid) {
		cluster.put(pid, cid);
		return cid;
	}
	
	/**
	 * writes the contents of the Object in json format to the FilePath File.
	 * @param Object
	 * @param FilePath
	 */
	public void writeToJsonString(Object o, String oPath) {    
		ObjectMapper om = new ObjectMapper();
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(oPath));
			bw.write(om.writeValueAsString(o));

			bw.flush();
			bw.close();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Integer, List<Integer>> groupClusters(Map<Integer, Integer> listCluster) {
		Map<Integer, List<Integer>> groups = new HashMap<Integer, List<Integer>>();
		for(int pID : listCluster.keySet()) {
			int cID = listCluster.get(pID);
			if(!groups.containsKey(cID)) {
				groups.put(cID, new ArrayList<Integer>());
			}
			((List<Integer>)groups.get(cID)).add(pID);
		}
		return groups;
	}
	
	public void writeToAdjacencyList(Map<Integer, Integer> listCluster, String adjacencyFile){
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(adjacencyFile));
			for(int pID : listCluster.keySet()) {
				int cID = listCluster.get(pID);
				String writeFormat = pID + ",T" + cID + "\n";
				bw.write(writeFormat);
			}
			
			bw.flush();
			bw.close();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}