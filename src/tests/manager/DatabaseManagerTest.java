package tests.manager;

import mywebserver.manager.DatabaseManager;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    @Test
    public void testGetInstance() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        Assert.assertNotNull(dbManager);
    }

    @Test
    public void testGetConnection() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseManager.getInstance().getConnection();
        Assert.assertNotNull(con);
        Assert.assertFalse(con.isClosed());
    }

    @Test
    public void testCloseConnection() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseManager.getInstance().getConnection();
        Assert.assertNotNull(con);
        Assert.assertFalse(con.isClosed());
        DatabaseManager.closeConnection(con);
        Assert.assertTrue(con.isClosed());
    }

}
