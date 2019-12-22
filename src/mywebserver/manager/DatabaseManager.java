package mywebserver.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);
    private static final String CONFIG = "db_conf.properties";
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
        if (this.connection != null) {
            // check if all required tables are available, if not create them
            createRequiredDatabaseTables();
        }
        return this.connection;
    }

    private void createRequiredDatabaseTables() throws SQLException {
        createTemperatureTable();
    }

    private void createTemperatureTable() throws SQLException {
        // create table on webserver start if table does not exists
        final String sql = "CREATE TABLE IF NOT EXISTS temperature\n" +
                "(\n" +
                "    temp_id serial PRIMARY KEY,\n" +
                "    value   NUMERIC   NOT NULL,\n" +
                "    date    TIMESTAMP NOT NULL\n" +
                "\n" +
                ");";
        Statement statement = this.connection.createStatement();
        statement.execute(sql);
    }

    /**
     * Method used to get database manager instance
     *
     * @return Database Manager instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (manager == null) {
            return new DatabaseManager();
        }
        return manager;
    }

    /**
     * Method used to get database connection
     *
     * @return Database Connection
     * @throws SQLException           On creating table, possible exception by executing connection statement
     * @throws ClassNotFoundException If database driver is not existing
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return connect();
    }

    /**
     * Method used to close database connection
     *
     * @param connection Connection to be closed
     */
    public static synchronized void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                LOG.info("Database connection successfully closed");
            } catch (SQLException e) {
                LOG.error("Database connection closing failed", e);
            }
        }
    }

}
