package mywebserver.api;

import java.util.ArrayList;
import java.util.List;

public class NavigationAPI {

    /**
     * Get list of all cities which contains passed street name.
     *
     * @param streetName Street name to be searched for
     * @return List of found cities. Never returns null.
     */
    public static List<String> getCities(final String streetName) {
        List<String> listOfCitis = new ArrayList<>();
        findCities(listOfCitis);
        return listOfCitis;
    }

    private static void findCities(List<String> listOfCities) {

    }

}
