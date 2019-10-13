package mywebserver.request;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import mywebserver.http.EHttpHeader;
import mywebserver.http.HttpHelper;
import mywebserver.http.WebURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class WebRequest implements Request {

    private InputStream inputStream;
    private boolean isValidRequest = false;
    private String rawPath = "";
    private String requestMethod = "";
    private String requestBody;
    private Map<String, String> headers;

    public WebRequest(InputStream inputStream) {
        this.inputStream = inputStream;
        parseWebRequest();
    }

    private void parseWebRequest() {
        if (this.inputStream == null) {
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
            String headerLine = "";
            int lineCounter = 0;
            while ((headerLine = bufferedReader.readLine()) != null) {
                if (headerLine.isEmpty()) {
                    continue;
                }
                if (lineCounter == 0) {
                    parseHeaderMethod(headerLine);
                    lineCounter++;
                    continue;
                }
                parseHeaderMetadata(headerLine);
            }
        } catch (IOException error) {
            System.out.println("WebRequest: An error occurred while reading input stream!");
            error.printStackTrace();
        }
    }

    private void parseRequestBody(String body) {
        // TODO Add POST option => in that case we need request body
        this.requestBody += body;
    }

    private void parseHeaderMethod(String headerLine) {
        StringTokenizer parser = new StringTokenizer(headerLine);
        String method = parser.nextToken().toUpperCase();
        String url = "";
        if (parser.hasMoreTokens()) {
            url = parser.nextToken();
        }
        if (Arrays.asList(HttpHelper.VALID_METHODS).contains(method)) {
            this.requestMethod = method;
            // WebURL.isValidURL()
            // TODO better handling for isValidURL
            if (!url.isEmpty()) {
                this.rawPath = url;
                this.isValidRequest = true;
            }
        }
    }

    private void parseHeaderMetadata(String headerLine) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        String[] splitLine = headerLine.split("[:]");
        if (splitLine.length > 0) {
            headers.put(splitLine[0].toLowerCase(), splitLine[1]);
        }
    }

    @Override
    public boolean isValid() {
        return this.isValidRequest;
    }

    @Override
    public String getMethod() {
        return this.requestMethod;
    }

    @Override
    public Url getUrl() {
        return new WebURL(this.rawPath);
    }

    @Override
    public Map<String, String> getHeaders() {
        if (this.headers != null) {
            return this.headers;
        }
        return new HashMap<>();
    }

    @Override
    public int getHeaderCount() {
        if (this.headers != null) {
            return headers.size();
        }
        return 0;
    }

    @Override
    public String getUserAgent() {
        if (this.headers != null) {
            return this.headers.get(EHttpHeader.USER_AGENT.getValue().toLowerCase());
        }
        return "";
    }

    @Override
    public int getContentLength() {
        if (this.headers != null) {
            return Integer.parseInt(this.headers.get(EHttpHeader.CONTENT_LENGTH.getValue().toLowerCase()));
        }
        return 0;
    }

    @Override
    public String getContentType() {
        if (this.headers != null) {
            return this.headers.get(EHttpHeader.CONTENT_TYPE.getValue().toLowerCase());
        }
        return "";
    }

    @Override
    public InputStream getContentStream() {
        return this.inputStream;
    }

    @Override
    public String getContentString() {
        return this.toString();
    }

    @Override
    public byte[] getContentBytes() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }

    public String toString() {
        return getRequestLine() + getRequestHeader();
    }

    private String getRequestLine() {
        return this.requestMethod + " " + this.rawPath + " " + EHttpHeader.HTTP.getValue() + '\n';
    }

    private String getRequestHeader() {
        StringBuilder requestHeader = new StringBuilder();
        for (Map.Entry<String, String> stringStringEntry : this.headers.entrySet()) {
            requestHeader.append(((Map.Entry) stringStringEntry).getKey()).append(": ").append(((Map.Entry) stringStringEntry).getValue()).append('\n');
        }
        return requestHeader.toString();
    }
}
