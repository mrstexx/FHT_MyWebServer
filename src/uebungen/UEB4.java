package uebungen;

import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import mywebserver.plugins.PluginManagerImpl;
import mywebserver.plugins.TestPlugin;
import mywebserver.request.WebRequest;
import mywebserver.response.WebResponse;

import java.io.InputStream;

public class UEB4 {

    public void helloWorld() {
        System.out.println("Hello, World!");
    }

    public Request getRequest(InputStream inputStream) {
        return new WebRequest(inputStream);
    }

    public Response getResponse() {
        return new WebResponse();
    }

    public PluginManager getPluginManager() {
        PluginManager pluginManager = new PluginManagerImpl();
        pluginManager.add(new TestPlugin());
        return pluginManager;
    }
}
