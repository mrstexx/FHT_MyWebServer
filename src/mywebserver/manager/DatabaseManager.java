package mywebserver.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);
    private static final String CONFIG = "conf.properties";

    private String url;
    private String user;
    private String password;

    private Connection connection = null;

    public DatabaseManager() {
        initConnectionData();
    }

    private void initConnectionData() {
        PropertyConfigManager propertyConfigManager = new PropertyConfigManager(CONFIG);
        this.url = propertyConfigManager.getPropertyValue(EConfigProperties.URL.getValue());
        this.user = propertyConfigManager.getPropertyValue(EConfigProperties.USER.getValue());
        this.password = propertyConfigManager.getPropertyValue(EConfigProperties.PASSWORD.getValue());
    }

    public Connection connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            LOG.info("Connection to database succeeded.");
        } catch (SQLException e) {
            LOG.error("Connection to database failed", e);
        }
        return this.connection;
    }

    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                LOG.info("Database connection successfully closed");
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

}
