package uebungen;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import mywebserver.plugins.*;
import mywebserver.request.WebRequest;

import java.io.InputStream;

public class UEB5 {

    public void helloWorld() {
        System.out.println("Hello, World");
    }

    public Request getRequest(InputStream inputStream) {
        return new WebRequest(inputStream);
    }

    public PluginManager getPluginManager() {
        PluginManager pluginManager = new PluginManagerImpl();
        pluginManager.add(new TestPlugin());
        pluginManager.add(new StaticDataPlugin());
        pluginManager.add(new TemperaturePlugin());
        pluginManager.add(new NavigationPlugin());
        pluginManager.add(new ToLowerPlugin());
        return pluginManager;
    }

    public Plugin getStaticFilePlugin() {
        return null;
    }

    public void setStatiFileFolder(String s) {

    }

    public String getStaticFileUrl(String s) {
        return null;
    }
}
