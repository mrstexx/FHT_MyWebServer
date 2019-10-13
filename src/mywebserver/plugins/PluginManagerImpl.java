package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PluginManagerImpl implements BIF.SWE1.interfaces.PluginManager {

    List<Plugin> plugins;

    public PluginManagerImpl() {
        this.plugins = new ArrayList<>();
    }

    @Override
    public Iterable<Plugin> getPlugins() {
        return this.plugins;
    }

    @Override
    public void add(Plugin plugin) {
        if (plugin != null && !this.plugins.contains(plugin)) {
            this.plugins.add(plugin);
        }
    }

    @Override
    public void add(String plugin) throws
            InstantiationException,
            IllegalAccessException,
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException {
        Class<?> clazz = Class.forName(plugin);
        Constructor<?> ctor = clazz.getConstructor();
        Object object = ctor.newInstance();
        if (!isClassNameContained(plugin)) {
            this.plugins.add((Plugin) object);
        }
    }

    private boolean isClassNameContained(String className) {
        for (Plugin plugin : this.plugins) {
            if (plugin.getClass().getSimpleName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.plugins.clear();
    }
}
