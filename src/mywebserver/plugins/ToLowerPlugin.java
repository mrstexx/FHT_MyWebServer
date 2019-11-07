package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import mywebserver.response.EMimeType;
import mywebserver.response.EStatusCodes;
import mywebserver.response.WebResponse;
import mywebserver.util.PluginUtil;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ToLowerPlugin implements Plugin {

    @Override
    public float canHandle(Request req) {
        return PluginUtil.getDefaultPluginProbability(ToLowerPlugin.class, req);
    }

    @Override
    public Response handle(Request req) {
        Response response = new WebResponse();
        String content = req.getContentString();
        // example : value=test -> we take test only
        String decodedContent = URLDecoder.decode(content, StandardCharsets.UTF_8);
        String[] bodyContent = decodedContent.split("=");
        String value = "";
        if (bodyContent.length > 1) {
            value = bodyContent[1];
        }
        response.setStatusCode(EStatusCodes.OK.getCode());
        response.setContentType(EMimeType.TEXT_PLAIN.getValue());
        response.setContent(!value.isEmpty() ? value.trim().toLowerCase() : "Not found: Bitte geben Sie einen Text ein");
        return response;
    }
}
