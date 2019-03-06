package org.khu.benchmark.gbench.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private static Properties properties;
	private static final String configFile = "config.properties";

	private static void loadProperties() {
		try {
			properties = new Properties();
			InputStream inStream = Configuration.class.getClassLoader().getResourceAsStream(configFile);
			if (inStream != null) {
				properties.load(inStream);
			}
		} catch (IOException e) {
			System.err.println("Cannot Load Properties File. See exception below:");
			e.printStackTrace();
		}
	}
	
	public static String get(String key){
		if(properties == null){
			loadProperties();
		}
		
		return properties.getProperty(key);
	}
	public static void main(String[] args) {
		System.out.println(Configuration.get("dataset.base.path"));
	}

}
