package com.senla.autoservice.ui.action.mastermenu;

import java.io.IOException;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.ui.action.IAction;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.Reader;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.constants.MethodsName;
import com.senla.autoservice.utills.request.RequestBuilder;
import com.senla.autoservice.utills.response.Response;

public class ShowMasterCarrOutOnMasterAction implements IAction {


	@Override
	public void excute(Client client) {
		try {
			client.sendRequest(new RequestBuilder().setMethod(MethodsName.GET_ORDER_BY_PRICE).create());
			Response resp = client.getResponce();
			Printer.printMessage(resp.getOutput());
			Printer.printMessage("Choose order");
			int id = Reader.readInt();
			client.sendRequest(new RequestBuilder().setMethod(MethodsName.GET_MASTER_BY_ORDER).setArgument(id).create());
			resp = client.getResponce();
			Printer.printMessage(resp.getOutput());
		} catch (final IOException e) {
			Printer.printMessage(Constants.ERROR_WRONG_INPUT);
		}

	}
}
