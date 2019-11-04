package mywebserver.model.temperature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class TemperatureDAO implements ITemperatureDAO {

    private static final Logger LOG = LogManager.getLogger(TemperatureDAO.class);
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO temperature (value, date) VALUES(?, ?);";

    @Override
    public List<Temperature> getAllTemperatures(Connection connection) {
        return null;
    }

    @Override
    public List<Temperature> getTemperaturesByDate(Connection connection) {
        return null;
    }

    @Override
    public Temperature getTemperatureByID(Connection connection, long id) {
        return null;
    }

    @Override
    public void insertTemperature(Connection connection, Temperature temperature) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_TEMPLATE);
            preparedStatement.setDouble(1, temperature.getValue());
            preparedStatement.setTimestamp(2, new Timestamp(temperature.getDate().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean deleteTemperature(Connection connection, Temperature temperature) {
        // TO IMPL
        return false;
    }

    @Override
    public boolean updateTemperature(Connection connection, Temperature temperature) {
        // TO IMPL
        return false;
    }
}
