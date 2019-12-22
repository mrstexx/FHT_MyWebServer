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

    /**
     * Load navigation store. It load all xml to navigation store.
     * Not safe to be used for multithreading. Recommended usage with Locks.
     *
     * @return Navigation store with all city-street informations
     * @throws ParserConfigurationException Parsing exception
     * @throws SAXException                 Possible exception while parsing data
     * @throws IOException                  If file does not exist
     */
    public static synchronized NavigationStore loadStore() throws ParserConfigurationException, SAXException, IOException {
        File file = new File(OSM_DATA_PATH);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        NodeHandler nodeHandler = new NodeHandler();
        saxParser.parse(file, nodeHandler);
        return nodeHandler.getNavStore();
    }

}
