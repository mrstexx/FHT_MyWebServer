package mywebserver.response;

import java.util.HashMap;
import java.util.Map;

public enum EMimeType {
    TEXT_HTML("text/html", "html"),
    TEXT_PLAIN("text/plain", "txt"),
    TEXT_JSON("text/json", "json"),
    TEXT_CSS("text/css", "css"),
    TEXT_JS("text/javascript", "js");

    private String value;
    private String extension;
    private static Map<String, String> values;

    EMimeType(String value, String extension) {
        this.value = value;
        this.extension = extension;
    }

    public String getValue() {
        return value;
    }

    public String getExtension() {
        return extension;
    }

    private static void initValues() {
        values = new HashMap<>();
        for (EMimeType mimeType : EMimeType.values()) {
            values.put(mimeType.getExtension(), mimeType.getValue());
        }
    }

    public static String getValue(String extension) {
        if (values == null) {
            initValues();
        }
        String value = values.get(extension);
        if (value == null || value.isEmpty()) {
            return "";
        }
        return value;
    }
}
