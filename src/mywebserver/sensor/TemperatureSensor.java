package mywebserver.sensor;

import mywebserver.manager.DatabaseManager;
import mywebserver.model.temperature.ITemperatureDAO;
import mywebserver.model.temperature.Temperature;
import mywebserver.model.temperature.TemperatureDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TemperatureSensor implements Runnable {

    private static final Logger LOG = LogManager.getLogger(TemperatureSensor.class);
    private static final int MIN_TEMP = 0;
    private static final int MAX_TEMP = 35;
    private static final int MEASURE_REPEAT_IN_HOURS = 11;

    @Override
    public void run() {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            generateRandomValues(connection, 4000, new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2014"));
        } catch (ParseException | SQLException e) {
            LOG.error(e);
        }
    }

    private void measureCurrentTemperature(Connection connection) {
        // TO BE IMPL
    }

    private void generateRandomValues(Connection connection, int numberOfValues, Date beginningDate) {
        ITemperatureDAO temperatureDAO = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginningDate);
        for (int i = 0; i < numberOfValues; i++) {
            temperatureDAO = new TemperatureDAO();
            temperatureDAO.insertTemperature(connection, new Temperature(getRandomTemperature(), calendar.getTime()));
            // measure every 6 hours
            calendar.add(Calendar.HOUR_OF_DAY, MEASURE_REPEAT_IN_HOURS);
        }
    }

    private static double getRandomTemperature() {
        Random random = new Random();
        return BigDecimal.valueOf(MIN_TEMP + (MAX_TEMP - MIN_TEMP) * random.nextDouble())
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
