package mywebserver.manager;

import BIF.SWE1.interfaces.PluginManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class PluginServiceManager extends ServiceManager {

    private static final Logger LOG = LogManager.getLogger(PluginServiceManager.class);
    private static PluginServiceManager pluginServiceManager;
    private PluginManager pluginManager;

    private PluginServiceManager() {
        this.pluginManager = new PluginManagerImpl();
    }

    public static synchronized PluginServiceManager getInstance() {
        if (pluginServiceManager != null) {
            return pluginServiceManager;
        }
        pluginServiceManager = new PluginServiceManager();
        return pluginServiceManager;
    }

    @Override
    public void loadServices(String fileName) throws IOException {
        // go through file and check each file line if relevant plugin is
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String fileLine = "";
        while ((fileLine = br.readLine()) != null) {
            try {
                this.pluginManager.add(fileLine);
                LOG.info("Loading plugin: {}, was successful", fileLine);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                LOG.error("Loading plugin failed: {}", e.getMessage());
            }
        }
        super.loadServices(fileName);
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

}
