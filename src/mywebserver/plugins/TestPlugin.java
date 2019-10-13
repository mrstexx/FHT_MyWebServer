package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import mywebserver.response.EStatusCodes;
import mywebserver.response.WebResponse;
import mywebserver.util.PluginUtil;

public class TestPlugin implements Plugin {

    private static final float PLUGIN_PROBABILITY = 0.2f;

    public TestPlugin() {
    }

    @Override
    public float canHandle(Request req) {
        float pluginProbability = PluginUtil.getDefaultPluginProbability(TestPlugin.class, req);
        if (req.getUrl().getPath().equals("/")) {
            pluginProbability += PLUGIN_PROBABILITY;
        }
        return pluginProbability > 1 ? 1f : pluginProbability;
    }

    @Override
    public Response handle(Request req) {
        Response response = new WebResponse();
        if (req != null && req.isValid()) {
            response.setStatusCode(EStatusCodes.OK.getCode());
            response.setContent("It works!");
        }
        return response;
    }
}
