package org.khu.benchmark.gbench;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class PropertyClustering {
	// . Separators
	public static final String T_SPLITTER = "title:";
	public static final String D_SPLITTER = "desc:";
	public static final String M_SPLITTER = "manu:";
	public static final String P_SPLITTER = "price:";
	
	private static double GLOBAL_THRESHOLD  = 0.48;
	private static int clusterID = 0;
	public static final String DATASET_BASE_PATH = "/home/bit-whacker/Documents/dataset/amazon_google/AmazonParts/";
	// . type property lists
	private List<String> titleList = new ArrayList<String>();
	
	//. Property with cluster id;
	private Map<Integer, Integer> cprop = new HashMap<Integer, Integer>();
	
	private void readProperty(String propertyPath) {
		// . info variables temps
		int emptyValues = 0;
		int invalidValues = 0;
		int fileRowCounter = 1;

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(propertyPath));

			String line = null;
			while ((line = br.readLine()) != null) {
				if (fileRowCounter == 1) {
					fileRowCounter++;
					continue;
				}
				// . pick property
				String[] parts = line.split(T_SPLITTER);
				String property = " ";
				if (parts.length == 2) {
					titleList.add(parts[1].toLowerCase().trim());
				} else if (parts.length != 2 && line.equals(T_SPLITTER)) {
					titleList.add(property);
					System.out.println("Empty String at [" + fileRowCounter + "]!!");
					emptyValues++;
				} else {
					System.out.println("Invalid String at [" + fileRowCounter + "]!!");
					invalidValues++;
				}

				// . increment the row
				fileRowCounter++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error Opening The File [" + propertyPath + "]!!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error Reading the line [" + fileRowCounter + "]!!");
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Cannot release the connection from bufferedReader!!");
				e.printStackTrace();
			}
		}

		System.out.println("In Memory Data Informaltion");
		System.out.println("---------------------------------");

		System.out.println("Total Rows Readed: [" + fileRowCounter + "]");
		System.out.println("List Size: [" + titleList.size() + "]");
		System.out.println("Empty Rows: [" + emptyValues + "]");
		System.out.println("Invalid Rows: " + invalidValues + "]");
		System.out.println("Test Row [24] below:");
		System.out.println(titleList.get(24));
	}

	private void toWeightedPropertyGraph(String weightedGraph) {
		//BufferedWriter bw = null;
		long startTime = System.currentTimeMillis();

		//try {
			//bw = new BufferedWriter(new FileWriter(weightedGraph));

			// . write the header row
			String com = ",";
			StringBuilder build = new StringBuilder();
			for (int i = 1; i <= titleList.size(); i++) {
				build.append(com).append(i);
			}
			build.append("\n");
			//bw.write(build.toString());

			String r_sep = "\n";
			toNewCluster(0);
			for (int row = 1; row < titleList.size(); row++) {
				String v_sep = "";
				StringBuilder sb = new StringBuilder();
				//bw.write(row + 1 + ",");
				//. double maxSim = 0.0;
				double minDist = 1.1;
				int maxPID = 0;
				for (int col = 0; col < row; col++) {
					// . sb.append(v_sep).append(Similarity.applyDamerau(titleList.get(row),
					// titleList.get(col)));
					// . double sim = Similarity.applyDamerau(titleList.get(row),
					// titleList.get(col));
					/** similarity
					double sim = Similarity.applyJaccard(titleList.get(row), titleList.get(col));
					if(sim > maxSim) {
						maxSim = sim;
						maxPID = col;
					}
					//. sb.append(v_sep).append(sim >= 0.3 ? "1" : "");
					//. v_sep = ",";
				}
				if(maxSim >= GLOBAL_THRESHOLD) {
					int cid = cprop.get(maxPID);
					addToCluster(row, cid);
				}else {
					toNewCluster(row);
				}
				*/
				//. Distance
					double dist = Similarity.applyNormalizedLevenshtein(titleList.get(row), titleList.get(col));
					if(dist < minDist) {
						minDist = dist;
						maxPID = col;
					}
					//. sb.append(v_sep).append(sim >= 0.3 ? "1" : "");
					//. v_sep = ",";
				}
				if(minDist <= GLOBAL_THRESHOLD) {
					int cid = cprop.get(maxPID);
					addToCluster(row, cid);
				}else {
					toNewCluster(row);
				}
			
					
				//sb.append(r_sep);
				//bw.write(sb.toString());
				ObjectMapper om = new ObjectMapper();
				try {
					
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(weightedGraph));
					bw.write(om.writeValueAsString(cprop));
					bw.flush();
					
					bw.close();
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
//		} catch (FileNotFoundException e) {
//			System.out.println("Error Opening The File [" + weightedGraph + "]!!");
//			e.printStackTrace();
//		} catch (IOException e) {
//			// . System.out.println("Error Writing the line [" + fileRowCounter + "]!!");
//			e.printStackTrace();
//		} finally {
//			try {
//				//bw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Cannot release the connection from bufferedReader!!");
//				e.printStackTrace();
//			}
//		}

		long timeTaken = System.currentTimeMillis() - startTime;
		timeTaken = timeTaken / 1000;

		System.out.println("time taken: " + timeTaken + "s");
		int clusters = clusterID - 1;
		System.out.println("number of clusters: [" + clusters + "]");
	}
	
	private int toNewCluster(int pid) {
		cprop.put(pid, clusterID);
		clusterID++;
		return clusterID-1;
	}
	private int addToCluster(int pid, int cid) {
		cprop.put(pid, cid);
		return cid;
	}

	public static void main(String[] args) {
		PropertyClustering pr = new PropertyClustering();

		String propertyPath = DATASET_BASE_PATH + "AmazonTitle.csv";
		pr.readProperty(propertyPath);

		String weightedGraph = DATASET_BASE_PATH + "WeightedTitleGraphNormLevin0" + GLOBAL_THRESHOLD + ".json";
		pr.toWeightedPropertyGraph(weightedGraph);

		System.out.println("Finished Processing!!");
	}
}