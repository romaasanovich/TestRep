package com.senla.autoservice.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.senla.autoservice.utills.constants.Constants;

public class Server {
	
	public static void main(final String[] args) throws IOException {

		System.out.println("Server started!!!");
		try (ServerSocket server = new ServerSocket(Constants.SERVER_PORT)) {
			while (true) {
				final Socket socket = server.accept();
				try {
					new ClientAccessThread(socket);
				} catch (final IOException e) {
					socket.close();
				}
			}
		}
	}
}
