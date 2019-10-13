package mywebserver.request;

import java.util.ArrayList;
import java.util.List;

public enum ERequestMethods {

    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    String requestMethod;
    private static List<String> values;

    ERequestMethods(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getValue() {
        return this.requestMethod;
    }

    private static void initList() {
        values = new ArrayList<>();
        for (ERequestMethods requestMethod : ERequestMethods.values()) {
            values.add(requestMethod.getValue());
        }
    }

    public static boolean contains(String method) {
        if (values == null) {
            initList();
        }
        return values.contains(method);
    }

}
