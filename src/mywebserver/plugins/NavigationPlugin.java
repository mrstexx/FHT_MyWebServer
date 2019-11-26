package mywebserver.plugins;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import mywebserver.plugins.navigation.NavigationParser;
import mywebserver.plugins.navigation.NavigationStore;
import mywebserver.request.ERequestMethods;
import mywebserver.response.EMimeType;
import mywebserver.response.EStatusCodes;
import mywebserver.response.WebResponse;
import mywebserver.util.Constants;
import mywebserver.util.PluginUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NavigationPlugin implements Plugin {

    private static final Logger LOG = LogManager.getLogger(NavigationPlugin.class);
    private static final String PARAM_LOAD_MAP = "load_map";

    private static Lock lock = new ReentrantLock();

    @Override
    public float canHandle(Request req) {
        if (!req.getMethod().equals(ERequestMethods.POST.getValue())) {
            return 0;
        }
        return PluginUtil.getDefaultPluginProbability(NavigationPlugin.class, req);
    }

    @Override
    public Response handle(Request req) {
        Url url = req.getUrl();
        if (url.getParameter().get(PARAM_LOAD_MAP) != null) {
            return handleLoadMap();
        }
        return handleStreetSearch(req);
    }

    private Response handleLoadMap() {
        Response response = new WebResponse();
        if (lock.tryLock()) {
            try {
                NavigationStore.setNavStore(NavigationParser.loadStore());
                response.setStatusCode(EStatusCodes.OK.getCode());
                response.setContentType(EMimeType.TEXT_PLAIN.getValue());
                response.setContent("OK");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                LOG.error("An error occurred while parsing Navigation XML data", e);
                response.setStatusCode(EStatusCodes.INTERNAL_SERVER_ERROR.getCode());
            } finally {
                lock.unlock();
            }
        } else {
            response.setStatusCode(EStatusCodes.OK.getCode());
            response.setContentType(EMimeType.TEXT_PLAIN.getValue());
            response.setContent("This action has been already started by another user.");
        }
        return response;
    }

    private Response handleStreetSearch(Request req) {
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
        List<String> listOfCities = NavigationStore.getInstance().getStoreValue(streetName);
        if (listOfCities.size() < 1) {
            return "Not found: Bitte geben Sie eine Anfrage ein";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Orte gefunden: ");
        int count = 1;
        for (String city : listOfCities) {
            sb.append("<br/>");
            sb.append(count).append(": ");
            sb.append(city);
            sb.append("<br/>");
            count++;
        }
        return sb.toString();
    }
}
