package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class TemperaturePlugin implements Plugin {

    public TemperaturePlugin() {
        
    }

    @Override
    public float canHandle(Request req) {
        return 0;
    }

    @Override
    public Response handle(Request req) {
        return null;
    }
}
