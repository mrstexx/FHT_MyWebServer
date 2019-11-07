package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import mywebserver.api.TemperatureAPI;
import mywebserver.response.EMimeType;
import mywebserver.response.EStatusCodes;
import mywebserver.response.WebResponse;
import mywebserver.util.PluginUtil;
import mywebserver.util.TemperatureUtil;

public class TemperaturePlugin implements Plugin {

    private static final String REST_API_SEGMENT = "GetTemperature";
    private static final float REST_PLUGIN_PROBABILITY = 0.8f;
    private static final int RECORDS_NUMBER_PER_PAGE = 20;
    private static final String PARAM_PAGE = "page";

    @Override
    public float canHandle(Request req) {
        float defaultProbability = PluginUtil.getDefaultPluginProbability(TemperaturePlugin.class, req);
        Url url = req.getUrl();
        String[] segments = url.getSegments();
        if (isRESTRequest(segments)) {
            defaultProbability += REST_PLUGIN_PROBABILITY;
        }
        return defaultProbability < 1 ? defaultProbability : 1;
    }

    @Override
    public Response handle(Request req) {
        Url url = req.getUrl();
        String[] segments = url.getSegments();
        if (isRESTRequest(segments)) {
            return handleRESTRequest(url);
        }
        return handlePageRangeRequest(url);
    }

    private Response handleRESTRequest(Url url) {
        Response response = new WebResponse();
        response.setStatusCode(EStatusCodes.OK.getCode());
        response.setContentType(EMimeType.TEXT_JSON.getValue());
        response.setContent("");
        return response;
    }

    private Response handlePageRangeRequest(Url url) {
        Response response = new WebResponse();
        int pageNumber = 0;
        if (url.getParameterCount() > 0) {
            pageNumber = Integer.parseInt(url.getParameter().get(PARAM_PAGE));
        }
        String content = TemperatureAPI.getTemperaturePageAsJSONString(pageNumber, RECORDS_NUMBER_PER_PAGE);
        response.setStatusCode(EStatusCodes.OK.getCode());
        response.setContentType(EMimeType.TEXT_JSON.getValue());
        response.setContent(content);
        return response;
    }

    private boolean isRESTRequest(String[] segments) {
        for (String segment : segments) {
            if (segment.equals(REST_API_SEGMENT)) {
                return true;
            }
        }
        return false;
    }
}
