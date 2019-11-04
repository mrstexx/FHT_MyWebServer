package mywebserver.model.temperature;

import java.sql.Connection;
import java.util.List;

public interface ITemperatureDAO {

    List<Temperature> getAllTemperatures(Connection connection);

    List<Temperature> getTemperaturesByDate(Connection connection);

    Temperature getTemperatureByID(Connection connection, long id);

    void insertTemperature(Connection connection, Temperature temperature);

    boolean deleteTemperature(Connection connection, Temperature temperature);

    boolean updateTemperature(Connection connection, Temperature temperature);

}
