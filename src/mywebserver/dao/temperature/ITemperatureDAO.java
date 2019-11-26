package mywebserver.dao.temperature;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface ITemperatureDAO {

    /**
     * Get list of all temperatures from database
     *
     * @param connection Database connection
     * @return The list of all temperature measures
     */
    List<Temperature> getAllTemperatures(Connection connection);

    /**
     * Method used to get temperature range (data for one page)
     *
     * @param connection      Database connection
     * @param pageNumber      Number of client page
     * @param numberOfResults Number of results to be queried
     * @return List of temperatures
     */
    List<Temperature> getTemperatureRange(Connection connection, int pageNumber, int numberOfResults);

    /**
     * Get list of all temperature for specific date
     *
     * @param connection Database connection
     * @param date       Date to be searched for
     * @return List of all temperatures object from specific date
     */
    List<Temperature> getTemperaturesByDate(Connection connection, Date date);

    /**
     * Get temperature by temperature ID
     *
     * @param connection Database connection
     * @param id         ID of the temperature
     * @return Temperature object
     */
    Temperature getTemperatureByID(Connection connection, long id);

    /**
     * Get number of temperature measures
     *
     * @param connection Database connection
     * @return Number of temperature measures
     */
    long getNumberOfRecords(Connection connection);

    /**
     * Insert new temperature object
     *
     * @param connection  Database connection
     * @param temperature Temperature object
     */
    void insertTemperature(Connection connection, Temperature temperature);

    /**
     * Delete temperature
     *
     * @param connection  Database connection
     * @param temperature Temperature object
     * @hidden
     */
    void deleteTemperature(Connection connection, Temperature temperature);

    /**
     * Update temperature by temperature object
     *
     * @param connection  Database connection
     * @param temperature Temperature object
     * @hidden
     */
    void updateTemperature(Connection connection, Temperature temperature);

}
