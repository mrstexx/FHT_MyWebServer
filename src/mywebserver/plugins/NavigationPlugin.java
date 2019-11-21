package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import mywebserver.api.NavigationAPI;
import mywebserver.response.EMimeType;
import mywebserver.response.EStatusCodes;
import mywebserver.response.WebResponse;
import mywebserver.util.PluginUtil;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NavigationPlugin implements Plugin {

    @Override
    public float canHandle(Request req) {
        float defaultProbability = PluginUtil.getDefaultPluginProbability(NavigationPlugin.class, req);
        Url url = req.getUrl();
        if (url.getExtension().equals("") && url.getParameterCount() < 1) {
            return 0;
        }
        return defaultProbability;
    }

    @Override
    public Response handle(Request req) {
        Response response = new WebResponse();
        String content = req.getContentString();
        // example : street=Hauptplatz -> we take test only
        String decodedContent = URLDecoder.decode(content, StandardCharsets.UTF_8);
        String[] bodyContent = decodedContent.split("=");
        String streetName = "";
        if (bodyContent.length > 1) {
            streetName = bodyContent[1];
        }
        response.setStatusCode(EStatusCodes.OK.getCode());
        response.setContentType(EMimeType.TEXT_PLAIN.getValue());
        response.setContent(getCityResults(streetName));
        return response;
    }

    private String getCityResults(String streetName) {
        List<String> listOfCities = NavigationAPI.getCities(streetName);
        if (listOfCities.size() < 1) {
            return "Not found: Bitte geben Sie eine Anfrage ein";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Orte gefunden");
        for (String city : listOfCities) {
            sb.append(city);
            sb.append("\n");
        }
        return sb.toString();
    }
}
