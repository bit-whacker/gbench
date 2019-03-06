package org.khu.benchmark.gbench;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadToList {

	public static List<String> toPropertyList(String propertyPath, String splitter) {

		List<String> propertyList = new ArrayList<String>();

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

				if (splitter.equalsIgnoreCase("price:")) {
					if (line.trim().equals("0")) {
						System.out.println("Empty String at [" + fileRowCounter + "]!!");
						emptyValues++;
					} else if (line.trim().length() == 0) {
						System.err.println("Invalid String at [" + fileRowCounter + "] value: [" + line + "!!");
						invalidValues++;
					}
					propertyList.add(line.trim());
				} else {
					// . pick property
					String[] parts = line.split(splitter);
					String property = " ";
					if (parts.length == 2) {
						propertyList.add(parts[1].toLowerCase().trim());
					} else if (parts.length != 2 && line.equals(splitter)) {
						propertyList.add(property);
						System.out.println("Empty String at [" + fileRowCounter + "]!!");
						emptyValues++;
					} else {
						System.err.println("Invalid String at [" + fileRowCounter + "]!!");
						invalidValues++;
					}
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
		System.out.println("List Size: [" + propertyList.size() + "]");
		System.out.println("Empty Rows: [" + emptyValues + "]");
		System.out.println("Invalid Rows: " + invalidValues + "]");
		System.out.println("Test Row [24] below:");
		System.out.println(propertyList.get(24));

		return propertyList;
	}

	public static void main(String[] args) {
		
		//String titlePath = PropertyClustering.DATASET_BASE_PATH + "AmazonTitle.csv";
		//toPropertyList(titlePath, PropertyClustering.T_SPLITTER);
		
		//String manuPath = PropertyClustering.DATASET_BASE_PATH + "AmazonManu.csv";
		//toPropertyList(manuPath, PropertyClustering.M_SPLITTER);
		
		//String descPath = PropertyClustering.DATASET_BASE_PATH + "AmazonDesc.csv";
		//toPropertyList(descPath, PropertyClustering.D_SPLITTER);
		
		String pricePath = PropertyClustering.DATASET_BASE_PATH + "AmazonPrice.csv";
		toPropertyList(pricePath, PropertyClustering.P_SPLITTER);

	}
}