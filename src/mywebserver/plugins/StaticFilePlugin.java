package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import mywebserver.response.EMimeType;
import mywebserver.response.EStatusCodes;
import mywebserver.response.WebResponse;
import mywebserver.util.Constants;
import mywebserver.util.PluginUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StaticFilePlugin implements Plugin {

    private static final float PLUGIN_PROBABILITY = 0.1f;

    public StaticFilePlugin() {
        // Empty contr body
    }

    @Override
    public float canHandle(Request req) {
        float pluginProbability = PluginUtil.getDefaultPluginProbability(StaticFilePlugin.class, req);
        String path = req.getUrl().getPath();
        long numberOfSlashes = path.chars().filter(character -> character == '/').count();
        if (numberOfSlashes > 1) {
            pluginProbability += PLUGIN_PROBABILITY;
        }
        return pluginProbability > 1 ? 1f : pluginProbability;
    }

    @Override
    public Response handle(Request req) {
        Response response = new WebResponse();
        Url url = req.getUrl();
        File file = new File(url.getPath());
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                response.setContentType(EMimeType.TEXT_HTML.getValue());
                response.setStatusCode(EStatusCodes.OK.getCode());
                response.setContent(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setContentType(EMimeType.TEXT_HTML.getValue());
            response.setStatusCode(EStatusCodes.NOT_FOUND.getCode());
            try {
                response.setContent(new FileInputStream(new File(Constants.STATIC_FOLDER_PATH, "404.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
