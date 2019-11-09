package mywebserver.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);
    private static final String CONFIG = "conf.properties";
    private static DatabaseManager manager;

    private String url;
    private String user;
    private String password;

    private Connection connection = null;

    private DatabaseManager() {
        initConnectionData();
    }

    private void initConnectionData() {
        PropertyConfig propertyConfig = new PropertyConfig(CONFIG);
        this.url = propertyConfig.getPropertyValue(EConfigProperties.URL.getValue());
        this.user = propertyConfig.getPropertyValue(EConfigProperties.USER.getValue());
        this.password = propertyConfig.getPropertyValue(EConfigProperties.PASSWORD.getValue());
    }

    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(this.url, this.user, this.password);
        return this.connection;
    }

    private void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                LOG.info("Database connection successfully closed");
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

    public static DatabaseManager getInstance() {
        if (manager == null) {
            return new DatabaseManager();
        }
        return manager;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return connect();
    }

    public void closeConnection() {
        disconnect();
    }

}
