package mywebserver;

import mywebserver.server.WebServer;

import java.io.IOException;

public class Main {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        WebServer webServer = null;
        try {
            System.out.println("Starting server at localhost:" + DEFAULT_PORT);
            webServer = new WebServer(DEFAULT_PORT);
            webServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Server stopped.");
                assert webServer != null;
                webServer.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
