package com.senla.autoservice.ui.action.placemenu;

import java.io.IOException;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.ui.action.IAction;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.Reader;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.constants.MethodsName;
import com.senla.autoservice.utills.request.RequestBuilder;
import com.senla.autoservice.utills.response.Response;

public class CountOfFreePlacesAction implements IAction {


	@Override
	public void excute(Client client) {
		try {
			String date = Reader.readline();
			client.sendRequest(new RequestBuilder().setMethod(MethodsName.GET_COUNT_FREE_PLACES_ON_DATE).setArgument(date).create());
			final Response resp = client.getResponce();
			Printer.printMessage(resp.getOutput());
		} catch (final IOException e) {
			Printer.printMessage(Constants.ERROR_WRONG_INPUT);
		}
	}
}
