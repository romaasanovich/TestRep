package com.senla.autoservice.ui.action.ordermenu;

import java.io.IOException;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.ui.action.IAction;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.Reader;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.constants.MethodsName;
import com.senla.autoservice.utills.request.RequestBuilder;
import com.senla.autoservice.utills.response.Response;

public class ShowOrderCarriedOutOnMasterAction implements IAction {

	@Override
	public void excute(Client client) {
		try {
			client.sendRequest(new RequestBuilder().setMethod(MethodsName.GET_MASTER_ALPHA).create());
			Response resp = client.getResponce();
			Printer.printMessage(resp.getOutput());
			Printer.printMessage("Choose master");
			int id = Reader.readInt();
			client.sendRequest(new RequestBuilder().setMethod(MethodsName.GET_ORDER_BY_MASTER).setArgument(id).create());
			Response responce = client.getResponce();
			Printer.printMessage(responce.getOutput());
		} catch (final IOException e) {
			Printer.printMessage(Constants.ERROR_WRONG_INPUT);
		}
	}
}
