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
     */
    public static String getTemperaturesByDateASJSON(Date date) {
        return TemperatureUtil.getTemperaturesByDate(date).toJSONString();
    }
}
