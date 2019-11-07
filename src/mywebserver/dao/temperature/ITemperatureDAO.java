package mywebserver.dao.temperature;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface ITemperatureDAO {

    List<Temperature> getAllTemperatures(Connection connection);

    List<Temperature> getTemperatureRange(Connection connection, int pageNumber, int numberOfResults);

    List<Temperature> getTemperaturesByDate(Connection connection, Date date);

    Temperature getTemperatureByID(Connection connection, long id);

    long getNumberOfRecords(Connection connection);

    void insertTemperature(Connection connection, Temperature temperature);

    void deleteTemperature(Connection connection, Temperature temperature);

    void updateTemperature(Connection connection, Temperature temperature);

}
