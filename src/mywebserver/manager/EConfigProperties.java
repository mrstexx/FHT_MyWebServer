package mywebserver.manager;

public enum EConfigProperties {
    URL("db.url"),
    PASSWORD("db.pwd"),
    USER("db.user");

    private String value;

    EConfigProperties(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
