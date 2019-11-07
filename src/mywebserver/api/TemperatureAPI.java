package mywebserver.api;

import mywebserver.util.TemperatureUtil;

public class TemperatureAPI {

    /**
     * Function used to get temperature data with passed parameters
     *
     * @param pageNumber          Page number
     * @param numberOfPageResults Number of page results to be shown
     * @return JSON object with results
     * @PublicAPI
     */
    public static String getTemperaturePageAsJSONString(int pageNumber, int numberOfPageResults) {
        return TemperatureUtil.getTemperaturePage(pageNumber, numberOfPageResults).toJSONString();
    }

}
