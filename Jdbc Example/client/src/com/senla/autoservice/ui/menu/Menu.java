package com.senla.autoservice.ui.menu;

import java.util.ArrayList;

public class Menu {
	private String name;
	private ArrayList<MenuItem> menuItems = new ArrayList<>();

	public Menu(String name) {
		this.name = name;
	}

	ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

	String getName() {
		return name;
	}

	void addItem(final MenuItem item) {
		menuItems.add(item);
	}

	MenuItem getItem(final Integer index) {
		return menuItems.get(index);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("-->").append(name).append("\n");
		for (int i = 0; i < menuItems.size(); i++) {
			sb.append(String.format("%1$-3d %2$s \n", i + 1, menuItems.get(i)));
		}
		return sb.toString();
	}

}
