package mywebserver.plugins.navigation;

import mywebserver.util.Constants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class NavigationParser {

    private static final String OSM_DATA_PATH = Constants.RESOURCES_PATH + Constants.FILE_SEPARATOR + "wien.xml";

    public static NavigationStore loadStore() throws ParserConfigurationException, SAXException, IOException {
        File file = new File(OSM_DATA_PATH);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        NodeHandler nodeHandler = new NodeHandler();
        saxParser.parse(file, nodeHandler);
        return nodeHandler.getNavStore();
    }

}
