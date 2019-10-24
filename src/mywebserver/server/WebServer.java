package mywebserver.server;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer {

    private ServerSocket serverSocket;

    public WebServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            ClientHandler clientHandler = new ClientHandler(this.serverSocket.accept());
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    public void stop() throws IOException {
        this.serverSocket.close();
        System.out.println("Closed connections.");
    }
}
