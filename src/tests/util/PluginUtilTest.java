package tests.util;

import BIF.SWE1.interfaces.Request;
import mywebserver.plugins.TestPlugin;
import mywebserver.request.WebRequest;
import mywebserver.util.PluginUtil;
import org.junit.Assert;
import org.junit.Test;
import tests.RequestHelper;

public class PluginUtilTest {

    private static final double DELTA = 1e-15;

    @Test
    public void testDefaultPluginProbabilityWithoutExtraParam() throws Exception {
        Request request1 = new WebRequest(RequestHelper.getValidRequestStream("localhost/test?a=1", "GET"));
        float value = PluginUtil.getDefaultPluginProbability(TestPlugin.class, request1);
        org.junit.Assert.assertEquals(value, 0.4f, DELTA);

        Request request2 = new WebRequest(RequestHelper.getValidRequestStream("localhost/tes?a=1", "GET"));
        value = PluginUtil.getDefaultPluginProbability(TestPlugin.class, request2);
        Assert.assertEquals(value, 0f, DELTA);
    }

    @Test
    public void testDefaultPluginProbabilityWithExtraParam() throws Exception {
        Request request1 = new WebRequest(RequestHelper.getValidRequestStream("localhost/test?test_plugin=true", "GET"));
        float value = PluginUtil.getDefaultPluginProbability(TestPlugin.class, request1);
        org.junit.Assert.assertEquals(value, 0.8f, DELTA);

        Request request2 = new WebRequest(RequestHelper.getValidRequestStream("localhost/?test_plugin=true", "GET"));
        value = PluginUtil.getDefaultPluginProbability(TestPlugin.class, request2);
        org.junit.Assert.assertEquals(value, 0.4f, DELTA);
    }

}
