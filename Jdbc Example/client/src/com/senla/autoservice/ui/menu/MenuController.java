package com.senla.autoservice.ui.menu;

import java.io.IOException;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.Reader;
import com.senla.autoservice.utills.constants.Constants;

public class MenuController {

	private final Builder builder;
	private final Navigator navigator;
	private Client client;
	public MenuController() {
		client = new Client(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
		Autoservice.getInstance();
		
		builder = new Builder();
		navigator = new Navigator(builder.buildMenu());
	}

	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				final Integer input = Reader.readInt();
				if (input == 0) {
					flag = false;
					break;
				}
				navigator.navigate(input,client);
			} catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
			
				Printer.printMessage(Constants.ERROR_WRONG_INPUT);
			}
		}
	}
}
