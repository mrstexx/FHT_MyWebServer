package mywebserver.dao.temperature;

import java.sql.Connection;
import java.util.List;

public interface ITemperatureDAO {

    List<Temperature> getAllTemperatures(Connection connection);

    List<Temperature> getTemperatureRange(Connection connection, int pageNumber, int numberOfResults);

    List<Temperature> getTemperaturesByDate(Connection connection);

    Temperature getTemperatureByID(Connection connection, long id);

    void insertTemperature(Connection connection, Temperature temperature);

    void deleteTemperature(Connection connection, Temperature temperature);

    void updateTemperature(Connection connection, Temperature temperature);

}
