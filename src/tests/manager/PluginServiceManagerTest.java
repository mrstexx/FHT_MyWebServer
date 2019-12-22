package tests.manager;

import BIF.SWE1.interfaces.PluginManager;
import mywebserver.manager.IServiceManager;
import mywebserver.manager.PluginServiceManager;
import mywebserver.util.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PluginServiceManagerTest {

    @Test
    public void testGetInstance() {
        IServiceManager pluginServiceManager = PluginServiceManager.getInstance();
        Assert.assertNotNull(pluginServiceManager);
    }

    @Test
    public void testNotLoadServices() {
        try {
            PluginServiceManager.getInstance().loadServices(Constants.RESOURCES_PATH);
            Assert.fail();
        } catch (IOException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testLoadServices() {
        try {
            PluginServiceManager.getInstance().loadServices(Constants.PLUGIN_SERVICE_PATH);
            PluginManager pm = PluginServiceManager.getInstance().getPluginManager();
            Assert.assertNotNull(pm);
            Assert.assertSame("Expected 4 plugins", 4, pm.getPlugins().size());
        } catch (IOException e) {
            Assert.fail();
        }
    }

}
