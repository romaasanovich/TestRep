package com.senla.autoservice.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.autoservice.utills.MethodGetter;
import com.senla.autoservice.utills.request.Request;
import com.senla.autoservice.utills.response.Response;

public class ClientAccessThread extends Thread implements Closeable {

    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private final Logger logger = LogManager.getLogger(getClass().getSimpleName());
    
    public ClientAccessThread(final Socket socket) throws IOException {
        this.socket = socket;
        input = new ObjectInputStream(this.socket.getInputStream());
        output = new ObjectOutputStream(this.socket.getOutputStream());
        System.out.println("New client");
        start();
    }

    public void run() {
        Response response;
        Request request;
        while (true) {
            try {
                request = (Request) input.readObject();
                response = MethodGetter.execute(request);
                output.writeObject(response);
                output.flush();
                System.out.println(request.getMethod());
            } catch (IOException | ClassNotFoundException e) {
            	logger.error(e);
            } catch (NoSuchMethodException e) {
            	logger.error(e);
            }

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