package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class ToLowerPlugin implements Plugin {

    public ToLowerPlugin() {
        
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
