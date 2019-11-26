package tests.plugins.navigation;

import mywebserver.plugins.navigation.NodeDetail;
import org.junit.Assert;
import org.junit.Test;

public class NodeDetailTest {

    @Test
    public void testGetterAndSetter() {
        NodeDetail nd = new NodeDetail();
        Assert.assertNull(nd.getCity());
        Assert.assertNull(nd.getStreet());

        nd.setCity("Wien");
        nd.setStreet("Dresdner Strasse");

        Assert.assertEquals("City", "Wien", nd.getCity());
        Assert.assertEquals("Street", "Dresdner Strasse", nd.getStreet());
    }

}
