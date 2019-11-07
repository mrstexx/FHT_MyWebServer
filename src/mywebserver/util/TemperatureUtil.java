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
import java.util.List;

public class TemperatureUtil {

    private static final Logger LOG = LogManager.getLogger(TemperatureUtil.class);
    private static final String PARAM_RESULT = "result";
    private static final String PARAM_VALUE = "value";
    private static final String PARAM_DATE = "date";

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static JSONObject getTemperaturePage(int page, int numberOfRecords) {
        JSONObject temperaturePage = new JSONObject();
        TemperatureDAO temperatureDAO = new TemperatureDAO();
        List<Temperature> temperatureList = new ArrayList<>();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            temperatureList = temperatureDAO.getTemperatureRange(connection, page, numberOfRecords);
        } catch (SQLException e) {
            LOG.error("Getting temperature page failed", e);
        }
        JSONArray temperatureResult = new JSONArray();
        for (Temperature temperature : temperatureList) {
            JSONObject tempObject = new JSONObject();
            tempObject.put(PARAM_VALUE, BigDecimal.valueOf(temperature.getValue())
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue());
            tempObject.put(PARAM_DATE, formatter.format(temperature.getDate()));
            temperatureResult.add(tempObject);
        }
        temperaturePage.put(PARAM_RESULT, temperatureResult);
        return temperaturePage;
    }

}
