package mywebserver.http;

public enum EHttpHeader {

    HOST("Host"),
    CONNECTION("Connection"),
    ACCEPT("Accept"),
    USER_AGENT("User-Agent"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    CONTENT_LENGTH("Content-Length"),
    SERVER("Server"),
    DATE("Date"),
    CONTENT_TYPE("Content-Type"),
    HTTP("HTTP/1.1");

    private String value;

    EHttpHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
