package com.senla.autoservice.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.autoservice.utills.constants.Constants;;

public class Prop {
	private final Logger logger =LogManager.getLogger(getClass().getSimpleName());
	private static java.util.Properties properties = new java.util.Properties();


	public Prop() {
		try (FileInputStream fis = new FileInputStream(Constants.PATH_TO_PROP)) {
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
