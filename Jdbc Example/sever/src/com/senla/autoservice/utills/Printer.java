package com.senla.autoservice.utills;

import java.util.List;

import com.senla.autoservice.api.bean.AEntity;

public class Printer {
		public static void printMessage(String line) {
			System.out.println(line);
		}
		
		
		public static <T extends AEntity> void printArray(List<T> list) {
			StringBuilder sb = new StringBuilder();
			for (T item : list) {
				sb.append(item).append("\n");
			}
			System.out.println(sb.toString());
		}
}
