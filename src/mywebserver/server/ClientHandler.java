package mywebserver.server;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import mywebserver.plugins.StaticFilePlugin;
import mywebserver.request.WebRequest;
import mywebserver.response.WebResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static final Logger LOG = LogManager.getLogger();
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            LOG.info("Client connected on port {}", this.clientSocket.getPort());
            this.inputStream = this.clientSocket.getInputStream();
            this.outputStream = this.clientSocket.getOutputStream();
            Response response = new WebResponse();
            Request request = new WebRequest(this.inputStream);

            Plugin staticFilePlugin = new StaticFilePlugin();
            if (staticFilePlugin.canHandle(request) > 0) {
                response = staticFilePlugin.handle(request);
            }
            response.send(this.outputStream);
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            closeStreams();
        }
    }

    private void closeStreams() {
        try {
            LOG.info("Client connection on {} port closed", this.clientSocket.getPort());
            this.outputStream.flush();
            this.outputStream.close();
            this.inputStream.close();
            this.clientSocket.close();
        } catch (IOException error) {
            LOG.error(error);
        }
    }
}
