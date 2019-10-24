package mywebserver.server;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import mywebserver.plugins.StaticFilePlugin;
import mywebserver.request.WebRequest;
import mywebserver.response.WebResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Client connected.");
            this.inputStream = this.clientSocket.getInputStream();
            this.outputStream = this.clientSocket.getOutputStream();
            Response response = new WebResponse();

            Request request = new WebRequest(this.inputStream);
            System.out.println("Received request");

            Plugin staticFilePlugin = new StaticFilePlugin();
            if (staticFilePlugin.canHandle(request) > 0) {
                response = staticFilePlugin.handle(request);
            }
            response.send(this.outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.outputStream.flush();
                this.outputStream.close();
                this.inputStream.close();
                this.clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
