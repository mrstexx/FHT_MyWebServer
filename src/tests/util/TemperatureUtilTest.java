package tests.util;

import mywebserver.util.TemperatureUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TemperatureUtilTest {

    @Test
    public void testGetTemperaturePage() {
        JSONObject tempPage = TemperatureUtil.getTemperaturePage(1, 20);

        Assert.assertFalse(tempPage.isEmpty());

        Assert.assertEquals("Entry Sets", 2, tempPage.entrySet().size());

        JSONArray results = (JSONArray) tempPage.get("result");
        Assert.assertEquals("Number of page records", 20, results.size());
    }

    @Test
    public void testGetTemperaturesByDateExisting() throws ParseException {
        JSONObject tempPage = TemperatureUtil.getTemperaturesByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-02-02"));

        Assert.assertFalse(tempPage.isEmpty());

        Assert.assertEquals("Entry Sets", 1, tempPage.entrySet().size());

        JSONArray results = (JSONArray) tempPage.get("result");
        Assert.assertEquals("Number of page records", 3, results.size());
    }

    @Test
    public void testGetTemperaturesByDateNotExisting() throws ParseException {
        JSONObject tempPage = TemperatureUtil.getTemperaturesByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-02"));

        Assert.assertFalse(tempPage.isEmpty());

        Assert.assertEquals("Entry Sets", 1, tempPage.entrySet().size());

        JSONArray results = (JSONArray) tempPage.get("result");
        Assert.assertEquals("Number of page records", 0, results.size());
    }

    @Test
    public void testGetTemperaturesByStringDateExisting() {
        JSONObject tempPage = TemperatureUtil.getTemperaturesByStringDate("2020/02/02");

        Assert.assertFalse(tempPage.isEmpty());

        Assert.assertEquals("Entry Sets", 1, tempPage.entrySet().size());

        JSONArray results = (JSONArray) tempPage.get("result");
        Assert.assertEquals("Number of page records", 0, results.size());
    }

    @Test
    public void testGetTemperaturesByStringDateNotExisting() {

        JSONObject tempPage = TemperatureUtil.getTemperaturesByStringDate("2009/12/22");

        Assert.assertFalse(tempPage.isEmpty());

        Assert.assertEquals("Entry Sets", 1, tempPage.entrySet().size());

        JSONArray results = (JSONArray) tempPage.get("result");
        Assert.assertEquals("Number of page records", 3, results.size());
    }

}
