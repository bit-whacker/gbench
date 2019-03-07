package org.khu.benchmark.gbench;

import java.util.List;
import java.util.Map;

import org.khu.benchmark.gbench.similarity.Similarity;
import org.khu.benchmark.gbench.util.Configuration;

/**
 * Hello world!
 *
 */
public class App {
	public static final String T_SPLITTER = "title:";
	public static final String D_SPLITTER = "desc:";
	public static final String M_SPLITTER = "manu:";
	public static final String P_SPLITTER = "price:";

	public static void main(String[] args) {
		System.out.println("Hello World!");

		String inFileName = "AmazonDesc";
		String splitter = PropertyClustering.D_SPLITTER;
		Similarity.SIMILARITY_MEASURE sm_type = Similarity.SIMILARITY_MEASURE.QuadGram4;
		double threshold = 0.7;
		
		
		String inFilePath = Configuration.get("dataset.base.path") + inFileName+".csv";
		List<String> pList = ReadToList.toPropertyList(inFilePath, splitter);
		
		
		String clusterOutPath = Configuration.get("dataset.base.path") + "clusters/tests/"+ inFileName + "_" + sm_type.toString() + "_" + threshold + ".json";
		
		ThresholdClustering thresholdCluster = new ThresholdClustering();
		Map<Integer, Integer> listClusters = thresholdCluster.cluster(pList, threshold, sm_type);
		Map<Integer, List<Integer>> groups = thresholdCluster.groupClusters(listClusters);
		
		thresholdCluster.writeToJsonString(groups, clusterOutPath);
		
		System.err.println("ListClusterSize: " + listClusters.size());
		System.err.println("GroupSize: " + groups.size());
		
		String adjacencyListFile = Configuration.get("dataset.base.path") + "clusters/tests/"+ inFileName + "_" + sm_type.toString() + "_" + threshold + ".csv";
		thresholdCluster.writeToAdjacencyList(listClusters, adjacencyListFile);
		System.out.println("Finished Processing!!");
	}
}
