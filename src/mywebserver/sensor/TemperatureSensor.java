package mywebserver.sensor;

import mywebserver.manager.DatabaseManager;
import mywebserver.dao.temperature.ITemperatureDAO;
import mywebserver.dao.temperature.Temperature;
import mywebserver.dao.temperature.TemperatureDAO;
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
    private static final int MEASURE_REPEAT_IN_HOURS = 8;
    private static final long HOUR_TO_MILSEC_CONST = 3600000;
    // NOTE: It should never be true!!! Used only as a flag for random data generating.
    private static final boolean ENABLE_TIME_GENERATOR = false;

    @Override
    public void run() {
        if (!ENABLE_TIME_GENERATOR) {
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {
                measureCurrentTemperature(connection);
            } catch (SQLException | InterruptedException e) {
                LOG.error(e);
            }
        } else {
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {
                generateRandomValues(connection, 10, new SimpleDateFormat("dd/MM/yyyy").parse("03/11/2019"));
            } catch (ParseException | SQLException e) {
                LOG.error(e);
            }
        }
    }

    private void measureCurrentTemperature(Connection connection) throws InterruptedException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        ITemperatureDAO temperatureDAO = new TemperatureDAO();
        while (true) {
            temperatureDAO.insertTemperature(connection,
                    new Temperature(getRandomTemperature(calendar.get(Calendar.MONTH)), new Date()));
            calendar.add(Calendar.HOUR_OF_DAY, MEASURE_REPEAT_IN_HOURS);
            Thread.sleep(MEASURE_REPEAT_IN_HOURS * HOUR_TO_MILSEC_CONST);
        }
    }

    private void generateRandomValues(Connection connection, int numberOfValues, Date beginningDate) {
        ITemperatureDAO temperatureDAO = new TemperatureDAO();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginningDate);
        for (int i = 0; i < numberOfValues; i++) {
            temperatureDAO.insertTemperature(connection,
                    new Temperature(getRandomTemperature(calendar.get(Calendar.MONTH)), calendar.getTime()));
            calendar.add(Calendar.HOUR_OF_DAY, MEASURE_REPEAT_IN_HOURS);
        }
    }

    private static double getRandomTemperature(int month) {
        Random random = new Random();
        int min = 0;
        int max = 35;
        if (month == Calendar.JANUARY || month == Calendar.DECEMBER) {
            min = -10;
            max = 14;
        } else if (month >= Calendar.FEBRUARY && month <= Calendar.MARCH) {
            min = 2;
            max = 17;
        } else if (month >= Calendar.APRIL && month <= Calendar.MAY) {
            min = 5;
            max = 22;
        } else if (month >= Calendar.JUNE && month <= Calendar.AUGUST) {
            min = 12;
            max = 35;
        } else if (month >= Calendar.SEPTEMBER && month <= Calendar.OCTOBER) {
            min = 9;
            max = 29;
        } else if (month >= Calendar.NOVEMBER && month <= Calendar.DECEMBER) {
            min = 2;
            max = 20;
        }
        return BigDecimal.valueOf(min + (max - min) * random.nextDouble())
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
