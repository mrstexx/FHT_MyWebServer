package mywebserver.plugins.navigation;

public class NodeDetail {

    private String street = null;
    private String city = null;

    /**
     * @return Street name
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street Street name to be setted
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return City name
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city City name to be setted
     */
    public void setCity(String city) {
        this.city = city;
    }
}
