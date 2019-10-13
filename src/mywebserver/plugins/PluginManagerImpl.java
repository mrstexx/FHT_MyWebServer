package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;

public class PluginManagerImpl implements BIF.SWE1.interfaces.PluginManager {

    public PluginManagerImpl() {
    }

    @Override
    public Iterable<Plugin> getPlugins() {
        return null;
    }

    @Override
    public void add(Plugin plugin) {

    }

    @Override
    public void add(String plugin) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

    }

    @Override
    public void clear() {

    }
}
