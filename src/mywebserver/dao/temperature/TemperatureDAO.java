package mywebserver.dao.temperature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemperatureDAO implements ITemperatureDAO {

    private static final Logger LOG = LogManager.getLogger(TemperatureDAO.class);
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO temperature (value, date) VALUES(?, ?);";
    private static final String SELECT_ALL = "SELECT * FROM temperature";
    private static final String SELECT_RANGE = "SELECT * FROM temperature WHERE temp_id > ? AND temp_id <= ?";

    @Override
    public List<Temperature> getAllTemperatures(Connection connection) {
        List<Temperature> temperaturesList = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            temperaturesList = executeGetQuery(preparedStatement);
        } catch (SQLException e) {
            LOG.error("Temperature select all execution failed", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOG.error("Temperature select all prepare statement closing failed", e);
                }
            }
        }
        return temperaturesList;
    }

    @Override
    public List<Temperature> getTemperatureRange(Connection connection, int pageNumber, int numberOfResults) {
        List<Temperature> temperatureList = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_RANGE);
            int fromID = pageNumber <= 1 ? 0 : (pageNumber - 1) * numberOfResults;
            int toID = pageNumber < 1 ? numberOfResults : pageNumber * numberOfResults;
            // including following IDs
            preparedStatement.setInt(1, fromID);
            preparedStatement.setInt(2, toID);
            temperatureList = executeGetQuery(preparedStatement);
        } catch (SQLException e) {
            LOG.error("Temperature select range execution failed", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOG.error("Temperature select range prepare statement close failed", e);
                }
            }
        }
        return temperatureList;
    }

    private List<Temperature> executeGetQuery(PreparedStatement preparedStatement) throws SQLException {
        List<Temperature> temperaturesList = new ArrayList<>();
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            long id = result.getLong(ETemperatureDAO.ID.getValue());
            double temperatureValue = result.getDouble(ETemperatureDAO.VALUE.getValue());
            Timestamp timestamp = result.getTimestamp(ETemperatureDAO.DATE.getValue());
            temperaturesList.add(new Temperature(id, temperatureValue, new Date(timestamp.getTime())));
        }
        result.close();
        return temperaturesList;
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_QUERY_TEMPLATE);
            preparedStatement.setDouble(1, temperature.getValue());
            preparedStatement.setTimestamp(2, new Timestamp(temperature.getDate().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error("Temperature insert failed", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOG.error("Temperature insert prepare statement closing failed", e);
                }
            }
        }
    }

    @Override
    public void deleteTemperature(Connection connection, Temperature temperature) {
        // TO IMPL
    }

    @Override
    public void updateTemperature(Connection connection, Temperature temperature) {
        // TO IMPL
    }
}
