package mywebserver.util;

import mywebserver.dao.temperature.Temperature;
import mywebserver.dao.temperature.TemperatureDAO;
import mywebserver.manager.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemperatureUtil {

    private static final Logger LOG = LogManager.getLogger(TemperatureUtil.class);
    private static final String PARAM_RESULT = "result";
    private static final String PARAM_VALUE = "value";
    private static final String PARAM_DATE = "date";
    private static final String PARAM_RECORDS = "recordsNumber";

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static JSONObject getTemperaturePage(int page, int numberOfPageRecords) {
        JSONObject temperaturePage = new JSONObject();
        TemperatureDAO temperatureDAO = new TemperatureDAO();
        List<Temperature> temperatureList = new ArrayList<>();
        long countOfRecords = 0;
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            temperatureList = temperatureDAO.getTemperatureRange(connection, page, numberOfPageRecords);
            countOfRecords = temperatureDAO.getNumberOfRecords(connection);
        } catch (SQLException e) {
            LOG.error("Getting temperature connection page failed", e);
        }
        makeJSONStructure(temperaturePage, temperatureList);
        temperaturePage.put(PARAM_RECORDS, countOfRecords);
        return temperaturePage;
    }

    public static JSONObject getTemperaturesByDate(Date date) {
        JSONObject result = new JSONObject();
        TemperatureDAO temperatureDAO = new TemperatureDAO();
        List<Temperature> temperatureList = new ArrayList<>();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            temperatureList = temperatureDAO.getTemperaturesByDate(connection, date);
        } catch (SQLException e) {
            LOG.error("Getting temperature by date connection failed", e);
        }
        makeJSONStructure(result, temperatureList);
        return result;
    }

    private static void makeJSONStructure(JSONObject result, List<Temperature> temperatureList) {
        JSONArray temperatureResult = new JSONArray();
        for (Temperature temperature : temperatureList) {
            JSONObject tempObject = new JSONObject();
            tempObject.put(PARAM_VALUE, BigDecimal.valueOf(temperature.getValue())
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue());
            tempObject.put(PARAM_DATE, formatter.format(temperature.getDate()));
            temperatureResult.add(tempObject);
        }
        result.put(PARAM_RESULT, temperatureResult);
    }
}
