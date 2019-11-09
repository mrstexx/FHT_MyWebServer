package mywebserver.response;

import java.util.HashMap;
import java.util.Map;

public enum EMimeType {
    APPLICATION_JSON("application/json", "json"),
    APPLICATION_JS("application/javascript", "js"),
    APPLICATION_PDF("application/pdf", "pdf"),
    APPLICATION_PHP("application/x-httpd-php", "php"),

    AUDIO_MPEG("audio/mpeg", "mp3"),
    AUDIO_MP4("audio/mp4", "mp4"),

    TEXT_HTML("text/html", "html"),
    TEXT_PLAIN("text/plain", "txt"),
    TEXT_CSS("text/css", "css"),
    TEXT_JS("text/javascript", "js"),
    TEXT_XML("text/xml", "xml"),
    TEXT_CSV("text/comma-separated-values", "csv"),

    VIDEO_MP4("video/mp4", "mp4"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_JPG("image/jpg", "jpg"),
    IMAGE_JPEG("image/jpeg", "jpeg"),
    IMAGE_SVG("image/svg", "svg"),
    IMAGE_GIF("image/gif", "gif");

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
