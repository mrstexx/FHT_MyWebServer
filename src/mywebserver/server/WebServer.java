package mywebserver.server;

import mywebserver.manager.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer {

    private static final Logger LOG = LogManager.getLogger();
    private ServerSocket serverSocket;
    private DatabaseManager dbManager;

    public WebServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        this.dbManager = new DatabaseManager();
    }

    public void start() {
        if (this.dbManager.connect() == null) {
            return;
        }
        LOG.info("Waiting for client to connect ...");
        while (true) {
            try {
                ClientHandler clientHandler = new ClientHandler(this.serverSocket.accept());
                new Thread(clientHandler).start();
                LOG.info("A new client is connected: {}", this.serverSocket);
            } catch (IOException e) {
                LOG.error("Unable to process client request\n", e);
            }
        }
    }

    public void stop() throws IOException {
        LOG.info("Server connection on port {} closed.", this.serverSocket.getLocalPort());
        this.serverSocket.close();
        dbManager.disconnect();
    }

}
