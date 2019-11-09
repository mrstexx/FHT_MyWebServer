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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TemperatureUtil {

    private static final Logger LOG = LogManager.getLogger(TemperatureUtil.class);
    private static final String PARAM_RESULT = "result";
    private static final String PARAM_VALUE = "value";
    private static final String PARAM_DATE = "date";
    private static final String PARAM_RECORDS = "recordsNumber";
    private static final String PARAM_ERROR = "error";

    private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
    private static DateTimeFormatter strictDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.GERMANY)
            .withResolverStyle(ResolverStyle.STRICT);

    public static JSONObject getTemperaturePage(int page, int numberOfPageRecords) {
        JSONObject temperaturePage = new JSONObject();
        TemperatureDAO temperatureDAO = new TemperatureDAO();
        List<Temperature> temperatureList = new ArrayList<>();
        long countOfRecords = 0;
        Connection connection = null;
        try {
            connection = DatabaseManager.getInstance().getConnection();
            temperatureList = temperatureDAO.getTemperatureRange(connection, page, numberOfPageRecords);
            countOfRecords = temperatureDAO.getNumberOfRecords(connection);
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error("Getting temperature connection page failed", e);
        }
        DatabaseManager.closeConnection(connection);
        makeJSONStructure(temperaturePage, temperatureList);
        temperaturePage.put(PARAM_RECORDS, countOfRecords);
        return temperaturePage;
    }

    public static JSONObject getTemperaturesByDate(Date date) {
        JSONObject result = new JSONObject();
        TemperatureDAO temperatureDAO = new TemperatureDAO();
        List<Temperature> temperatureList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseManager.getInstance().getConnection();
            temperatureList = temperatureDAO.getTemperaturesByDate(connection, date);
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error("Getting temperature by date connection failed", e);
        }
        DatabaseManager.closeConnection(connection);
        makeJSONStructure(result, temperatureList);
        return result;
    }

    public static JSONObject getTemperaturesByStringDate(String dateValue) {
        Date date = getDateFromString(dateValue);
        if (date != null) {
            return getTemperaturesByDate(date);
        }
        return getFailureJSONStructure();
    }

    private static Date getDateFromString(String dateValue) {
        Date date = null;
        try {
            strictDateFormatter.parse(dateValue);
            date = dateFormatter.parse(dateValue);
        } catch (DateTimeParseException | ParseException e) {
            LOG.warn("Parsing string date was not successful");
        }
        return date;
    }

    private static JSONObject getFailureJSONStructure() {
        JSONObject resultObject = new JSONObject();
        resultObject.put(PARAM_ERROR, "An error occurred due to wrong request.");
        return resultObject;
    }

    private static void makeJSONStructure(JSONObject result, List<Temperature> temperatureList) {
        JSONArray temperatureResult = new JSONArray();
        for (Temperature temperature : temperatureList) {
            JSONObject tempObject = new JSONObject();
            tempObject.put(PARAM_VALUE, BigDecimal.valueOf(temperature.getValue())
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue());
            tempObject.put(PARAM_DATE, dateTimeFormatter.format(temperature.getDate()));
            temperatureResult.add(tempObject);
        }
        result.put(PARAM_RESULT, temperatureResult);
    }
}
