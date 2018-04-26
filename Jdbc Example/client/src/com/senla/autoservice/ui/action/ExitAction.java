package com.senla.autoservice.ui.action;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.constants.MethodsName;
import com.senla.autoservice.utills.request.RequestBuilder;
import com.senla.autoservice.utills.response.Response;

public class ExitAction implements IAction {
	@Override
	public void excute(Client client) {
		client.sendRequest(new RequestBuilder().setMethod(MethodsName.EXIT).create());
		final Response resp = client.getResponce();
		Printer.printMessage(resp.getOutput());
	}
}
