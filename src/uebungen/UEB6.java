package uebungen;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import mywebserver.manager.PluginManagerImpl;
import mywebserver.plugins.*;
import mywebserver.request.WebRequest;

import java.io.InputStream;
import java.time.LocalDate;

public class UEB6 {

    public void helloWorld() {
        System.out.println("Hello, world!");
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

    public Plugin getTemperaturePlugin() {
        return new TemperaturePlugin();
    }

    public Plugin getNavigationPlugin() {
        return new NavigationPlugin();
    }

    public Plugin getToLowerPlugin() {
        return new ToLowerPlugin();
    }

    public String getTemperatureUrl(LocalDate localDate, LocalDate localDate1) {
        return "/temperature?temperature_plugin=true&date=" +
                localDate.getYear() + "-" +
                localDate.getMonthValue() + "-" +
                localDate.getDayOfMonth();
    }

    public String getTemperatureRestUrl(LocalDate localDate, LocalDate localDate1) {
        return "/GetTemperature/" +
                localDate.getYear() + "/" +
                localDate.getMonthValue() + "/" +
                localDate.getDayOfMonth();
    }

    public String getNaviUrl() {
        return "/navigation";
    }

    public String getToLowerUrl() {
        return "/tolower";
    }
}
