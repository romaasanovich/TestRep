package com.senla.autoservice.ui.menu;

import java.util.Map;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.utills.Printer;

public class Navigator {
	private Menu currentMenu;

	private final Map<MenuType, Menu> menuList;

	Navigator(final Map<MenuType, Menu> menuList) {
		this.menuList = menuList;
		currentMenu = menuList.get(MenuType.Root);
		printMenu();
	}

	private void printMenu() {
		Printer.printMessage(currentMenu.toString());
	}

	void navigate(final Integer id,Client client) {
		final MenuItem item = currentMenu.getItem(id - 1);
		item.doAction(client);
		currentMenu = item.getNextMenu();
		printMenu();
	}
}
