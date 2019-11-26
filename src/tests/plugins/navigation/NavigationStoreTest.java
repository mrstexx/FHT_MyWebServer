package tests.plugins.navigation;

import mywebserver.plugins.navigation.NavigationParser;
import mywebserver.plugins.navigation.NavigationStore;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class NavigationStoreTest {

    @Test
    public void testGetInstance() {
        NavigationStore ns = NavigationStore.getInstance();
        Assert.assertNotNull(ns);
    }

    @Test
    public void testAddStoreValue() {
        NavigationStore ns = NavigationStore.getInstance();
        Assert.assertNotNull(ns);

        Assert.assertEquals("0 values in store", 0, ns.getStoreValue("test").size());

        ns.addStoreValue("test", "Test");
        ns.addStoreValue("test", "Test 2");

        Assert.assertEquals("2 values in store", 2, ns.getStoreValue("test").size());
    }

    @Test
    public void testGetStoreValue() throws IOException, SAXException, ParserConfigurationException {
        NavigationStore ns = NavigationParser.loadStore();
        Assert.assertNotNull(ns);

        Assert.assertEquals("Street found", 1, ns.getStoreValue("Dresdner Straße").size());
        Assert.assertEquals("City found", "Wien", ns.getStoreValue("Dresdner Straße").get(0));
    }

}
