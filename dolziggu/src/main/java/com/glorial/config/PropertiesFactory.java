package com.glorial.config;

import java.io.FileReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFactory {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesFactory.class);
	static Properties prop;
	
	public static Properties getProperties(String filepath) throws Exception {
		Properties prop = new Properties();
		prop.load(new FileReader(filepath));

		return prop;
	}
	
	public static Properties getInstance(){
		if(prop == null){
			try {
				String configPath = PropertiesFactory.class.getResource("/config/config.properties").getPath();
				prop = PropertiesFactory.getProperties(configPath);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("properties load failed" , e);
			}
		}
		
		return prop;
	}
}