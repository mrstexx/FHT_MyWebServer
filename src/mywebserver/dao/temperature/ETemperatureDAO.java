package mywebserver.dao.temperature;

public enum ETemperatureDAO {
    ID("temp_id"),
    VALUE("value"),
    DATE("date");

    private String value;

    ETemperatureDAO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}