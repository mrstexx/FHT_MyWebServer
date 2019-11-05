package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import mywebserver.util.PluginUtil;

public class TemperaturePlugin implements Plugin {

    @Override
    public float canHandle(Request req) {
        return PluginUtil.getDefaultPluginProbability(ToLowerPlugin.class, req);
    }

    @Override
    public Response handle(Request req) {
        return null;
    }
}
