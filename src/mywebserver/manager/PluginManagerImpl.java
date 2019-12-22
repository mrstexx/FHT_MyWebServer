package mywebserver.manager;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginManagerImpl implements PluginManager {

    private List<Plugin> plugins;

    public PluginManagerImpl() {
        this.plugins = new ArrayList<>();
    }

    @Override
    public List<Plugin> getPlugins() {
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
            InvocationTargetException, MalformedURLException {
        Plugin newPlugin = null;
        // check for all implementations of interface Plugin
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
        Class<?> clazz = Class.forName(plugin, true, classLoader);
        Class<?>[] interfaces = clazz.getInterfaces();
        // iterate all interfaces and create new instances
        for (Class i : interfaces) {
            if (i.toString().equals(Plugin.class.toString())) {
                Constructor<?> ctor = clazz.getConstructor();
                newPlugin = (Plugin) ctor.newInstance();
            }
        }
        // if new instance is not null and if name is not yet contained in plugin list, add it to the list
        if (newPlugin != null && !isClassNameContained(plugin)) {
            this.plugins.add(newPlugin);
            return;
        }
        throw new ClassNotFoundException();
    }

    private boolean isClassNameContained(String className) {
        // check if name is contained in plugins
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
