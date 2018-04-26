package com.senla.autoservice.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Prop {

	public static final String PATH_TO_PROP = "D:\\Server\\src\\main\\resources\\Prop.txt";
	private final Logger logger =LogManager.getLogger(getClass().getSimpleName());
	private static java.util.Properties properties = new java.util.Properties();


	public Prop() {
		try (FileInputStream fis = new FileInputStream(PATH_TO_PROP)) {
			properties.load(fis);
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public static String getProp(String key) {
		return properties.getProperty(key, "");
	}

}
