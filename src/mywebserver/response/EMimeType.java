package mywebserver.response;

public enum EMimeType {
    TEXT_HTML("text/html");

    private String value;

    EMimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
