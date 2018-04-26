package com.senla.autoservice.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.senla.autoservice.utills.request.Request;
import com.senla.autoservice.utills.response.Response;

public class Client implements Closeable {

	private Socket socket;

	private ObjectInputStream input;

	private ObjectOutputStream output;

	public Client(final String host, final int port) {
		try {
			socket = new Socket(host, port);
			final InputStream inputStream = socket.getInputStream();
			final OutputStream outputStream = socket.getOutputStream();
			output = new ObjectOutputStream(outputStream);
			input = new ObjectInputStream(inputStream);
		} catch (final IOException ignored) {
		}
	}

	public Response getResponce() {
		Response response;
		try {
			response = (Response) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			response = null;
		}
		return response;
	}

	public void sendRequest(final Request request) {
		try {
			output.writeObject(request);
			output.flush();
		} catch (final IOException ignored) {
		}
	}

	@Override
	public void close() throws IOException {
		if (input != null) {
			input.close();
		}
		if (output != null) {
			output.close();
		}
		if (socket != null) {
			socket.close();
		}
	}
}
