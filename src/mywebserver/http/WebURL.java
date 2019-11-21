package mywebserver.http;

import BIF.SWE1.interfaces.Url;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebURL implements Url {

    private static final String URL_REGEX_VALID =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                    "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                    "([).!';/?:,][[:blank:]])?$";
    private String rawUrl;

    public WebURL(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    @Override
    public String getRawUrl() {
        return this.rawUrl;
    }

    @Override
    public String getPath() {
        if (this.rawUrl != null) {
            if (this.rawUrl.contains("?")) {
                return this.rawUrl.split("[?]")[0];
            }
            if (this.rawUrl.contains("#")) {
                return this.rawUrl.split("[#]")[0];
            }
            return this.rawUrl;
        }
        return "";
    }

    @Override
    public Map<String, String> getParameter() {
        if (this.rawUrl != null) {
            if (this.rawUrl.contains("?")) {
                String urlParameters = this.rawUrl.split("[?]")[1];
                return parseUrlParameters(urlParameters);
            }
        }
        return new HashMap<>();
    }

    private Map<String, String> parseUrlParameters(String urlParameters) {
        Map<String, String> parameters = new HashMap<>();
        if (urlParameters.contains("&")) {
            String[] params = urlParameters.split("[&]");
            for (String subParam : params) {
                String[] spitedSubParams = subParam.split("[=]");
                parameters.put(spitedSubParams[0], spitedSubParams[1]);
            }
        } else {
            String[] params = urlParameters.split("[=]");
            parameters.put(params[0], params[1]);
        }
        return parameters;
    }

    @Override
    public int getParameterCount() {
        Map<String, String> parameters = getParameter();
        if (parameters != null) {
            return parameters.size();
        }
        return 0;
    }

    @Override
    public String[] getSegments() {
        return Arrays.stream(getPath().split("[/]")).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }

    @Override
    public String getFileName() {
        if (this.rawUrl != null) {
            String lastSegment = getLastSegment();
            if (lastSegment.contains(".")) {
                return lastSegment;
            }
        }
        return "";
    }

    @Override
    public String getExtension() {
        String lastSegment = getLastSegment();
        if (lastSegment.contains(".")) {
            String[] fileSegments = lastSegment.split("[.]");
            return fileSegments.length > 0 ? fileSegments[fileSegments.length - 1] : "";
        }
        return "";
    }

    @Override
    public String getFragment() {
        if (this.rawUrl != null) {
            if (this.rawUrl.contains("#")) {
                return this.rawUrl.split("[#]")[1];
            }
        }
        return "";
    }

    private String getLastSegment() {
        String[] segments = getSegments();
        return segments.length > 0 ? segments[segments.length - 1] : "";
    }

    public static boolean isValidURL(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        Pattern urlPattern = Pattern.compile(URL_REGEX_VALID);
        Matcher matcher = urlPattern.matcher(url);
        return matcher.matches();
    }
}
