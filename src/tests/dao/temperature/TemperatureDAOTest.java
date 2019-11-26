package tests.dao.temperature;

import mywebserver.dao.temperature.Temperature;
import mywebserver.dao.temperature.TemperatureDAO;
import mywebserver.manager.DatabaseManager;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TemperatureDAOTest {

    @Test
    public void testGetAllTemperatures() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseManager.getInstance().getConnection();
        TemperatureDAO dao = new TemperatureDAO();
        List<Temperature> list = dao.getAllTemperatures(con);

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 10000);

        DatabaseManager.closeConnection(con);
    }

    @Test
    public void testGetTemperatureRange() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseManager.getInstance().getConnection();
        TemperatureDAO dao = new TemperatureDAO();
        List<Temperature> list = dao.getTemperatureRange(con, 1, 50);

        Assert.assertNotNull(list);
        Assert.assertEquals(50, list.size());

        DatabaseManager.closeConnection(con);
    }

    @Test
    public void testGetNumberOfRecords() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseManager.getInstance().getConnection();
        TemperatureDAO dao = new TemperatureDAO();
        long numRecords = dao.getNumberOfRecords(con);

        Assert.assertTrue(numRecords > 10000);

        DatabaseManager.closeConnection(con);
    }

    @Test
    public void testGetTemperaturesByDate() throws SQLException, ClassNotFoundException, ParseException {
        Connection con = DatabaseManager.getInstance().getConnection();
        TemperatureDAO dao = new TemperatureDAO();
        List<Temperature> list = dao.getTemperaturesByDate(con, new SimpleDateFormat("yyyy-MM-dd").parse("2017-02-21"));

        Assert.assertEquals(3, list.size());

        list = dao.getTemperaturesByDate(con, new SimpleDateFormat("yyyy-MM-dd").parse("2001-02-21"));
        Assert.assertEquals(0, list.size());

        DatabaseManager.closeConnection(con);
    }

    @Test
    public void testInsertTemperature() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseManager.getInstance().getConnection();
        TemperatureDAO dao = new TemperatureDAO();
        Date date = new Date();
        Temperature temp = new Temperature(10, date);
        dao.insertTemperature(con, temp);

        List<Temperature> temps = dao.getTemperaturesByDate(con, date);

        Assert.assertNotNull(temps);
        Assert.assertTrue(temps.size() >= 1);
        DatabaseManager.closeConnection(con);
    }

}
