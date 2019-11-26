package tests.plugins.navigation;

import mywebserver.plugins.navigation.NavigationParser;
import mywebserver.plugins.navigation.NavigationStore;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class NavigationParserTest {

    @Test
    public void testLoadStore() throws IOException, SAXException, ParserConfigurationException {
        NavigationStore ns = NavigationParser.loadStore();
        Assert.assertNotNull(ns);
    }

}
