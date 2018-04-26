package com.senla.autoservice.utills;

import com.senla.autoservice.properties.Prop;

public class DependencyInjector {

	public static <T> T getInstance(final Class clazz) {
		T response;
		final String clazzName = Prop.getProp(clazz.getName());
		try {
			response = (T) Class.forName(clazzName).getConstructor().newInstance();
		} catch (final Exception e) {
			response = null;
		}
		return response;
	}

}
