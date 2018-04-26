package com.senla.autoservice.ui.action.ordermenu;

import java.io.IOException;
import java.util.Date;

import com.senla.autoservice.client.Client;
import com.senla.autoservice.ui.action.IAction;
import com.senla.autoservice.utills.Convert;
import com.senla.autoservice.utills.Printer;
import com.senla.autoservice.utills.Reader;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.constants.MethodsName;
import com.senla.autoservice.utills.request.RequestBuilder;
import com.senla.autoservice.utills.response.Response;

public class StatusBronedAction implements IAction {

	@Override
	public void excute(Client client) {
		try {
			String date = Reader.readline();
			Date fDate = Convert.fromStrToDate(date);
			date = Reader.readline();
			Date sDate = Convert.fromStrToDate(date);
			client.sendRequest(new RequestBuilder().setMethod(MethodsName.GET_ORDERS_PERIOD_TIME)
					.setArgument(fDate).setArgument(sDate).create());
			final Response resp = client.getResponce();
			Printer.printMessage(resp.getOutput());
		} catch (final IOException e) {
			Printer.printMessage(Constants.ERROR_WRONG_INPUT);
		}
	}
}
