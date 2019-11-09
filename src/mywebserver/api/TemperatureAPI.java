package mywebserver.api;

import mywebserver.util.TemperatureUtil;

import java.util.Date;

public class TemperatureAPI {

    /**
     * Function used to get temperature data with passed parameters
     *
     * @param pageNumber          Page number
     * @param numberOfPageResults Number of page results to be shown
     * @return String of JSON object with results
     */
    public static String getTemperaturePageAsJSONString(int pageNumber, int numberOfPageResults) {
        return TemperatureUtil.getTemperaturePage(pageNumber, numberOfPageResults).toJSONString();
    }

    /**
     * Function used to get temperature data with passed date
     *
     * @param date Date to get temperatures of
     * @return String of JSON object with results
     * @deprecated Use rest request instead
     */
    public static String getTemperaturesByDateAsJSONString(Date date) {
        return TemperatureUtil.getTemperaturesByDate(date).toJSONString();
    }

    /**
     * Function used to get temperature with passed date. Also used for GetTemperature REST API.
     * Date form: yyyy/MM/dd
     *
     * @param date String value of date to get temperature of
     * @return String of JSON object with results
     */
    public static String getTemperatureByStringAsJSONString(String date) {
        return TemperatureUtil.getTemperaturesByStringDate(date).toJSONString();
    }
}
