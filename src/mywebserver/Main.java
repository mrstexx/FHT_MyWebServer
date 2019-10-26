package mywebserver;

import mywebserver.server.WebServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

    private static final int DEFAULT_PORT = 8080;
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        WebServer webServer = null;
        try {
            LOG.info("Starting webserver at 127.0.0.1:" + DEFAULT_PORT);
            webServer = new WebServer(DEFAULT_PORT);
            webServer.start();
        } catch (IOException e) {
            LOG.error(e);
        } finally {
            try {
                LOG.info("Server stopped.");
                assert webServer != null;
                webServer.stop();
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }
}
