package uebungen;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import mywebserver.manager.PluginManagerImpl;
import mywebserver.plugins.*;
import mywebserver.request.WebRequest;
import mywebserver.util.Constants;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class UEB5 {

    private static final String TMP_STATIC_FILES = "deploy/tmp-static-files";

    public void helloWorld() {
        System.out.println("Hello, World");
    }

    public Request getRequest(InputStream inputStream) {
        return new WebRequest(inputStream);
    }

    public PluginManager getPluginManager() {
        PluginManager pluginManager = new PluginManagerImpl();
        pluginManager.add(new TestPlugin());
        pluginManager.add(new StaticFilePlugin());
        pluginManager.add(new TemperaturePlugin());
        pluginManager.add(new NavigationPlugin());
        pluginManager.add(new ToLowerPlugin());
        return pluginManager;
    }

    public Plugin getStaticFilePlugin() {
        return new StaticFilePlugin();
    }

    public void setStatiFileFolder(String s) {

    }

    public String getStaticFileUrl(String s) {
        File dir = new File("." + Constants.FILE_SEPARATOR + TMP_STATIC_FILES);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().equals(s)) {
                return file.getPath();
            }
        }
        return Constants.FILE_SEPARATOR + TMP_STATIC_FILES + Constants.FILE_SEPARATOR + s;
    }
}
