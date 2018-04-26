package com.senla.autoservice.ui.action.ordermenu;

import java.io.IOException;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.ui.action.IAction;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.Reader;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.constants.MethodsName;
import com.senla.autoservice.utills.request.RequestBuilder;
import com.senla.autoservice.utills.response.Response;

public class CloneOrderAction implements IAction {

	@Override
	public void excute(Client client) {
		try {
			Printer.printMessage(Autoservice.getInstance().getOrdersByPrice() + "\n");
			Printer.printMessage("Choose order");
			int id = Reader.readInt();

			client.sendRequest(new RequestBuilder().setMethod(MethodsName.CLONE_ORDER).setArgument((int)id).create());
			final Response resp = client.getResponce();
			Printer.printMessage(resp.getOutput());
			
		} catch (final IOException e) {
			Printer.printMessage(Constants.ERROR_WRONG_INPUT);
		}
	}

}
