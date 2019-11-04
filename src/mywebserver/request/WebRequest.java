package mywebserver.request;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import mywebserver.http.EHttpHeader;
import mywebserver.http.WebURL;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class WebRequest implements Request {

    private InputStream inputStream;
    private boolean isValidRequest = false;
    private String rawPath = "";
    private String requestMethod = "";
    private String requestBody = "";
    private Map<String, String> headers;

    public WebRequest(InputStream inputStream) {
        this.inputStream = inputStream;
        parseWebRequest();
    }

    private void parseWebRequest() {
        if (this.inputStream == null) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
        try {
            parseHeaderMethod(bufferedReader);
            parseHeaderMetadata(bufferedReader);
            if (this.requestMethod.equals(ERequestMethods.POST.getValue())) {
                parseRequestBody(bufferedReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRequestBody(BufferedReader bufferedReader) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        while (bufferedReader.ready()) {
            char temp = (char) bufferedReader.read();
            requestBody.append(temp);
        }
        this.requestBody = requestBody.toString();
    }

    private void parseHeaderMethod(BufferedReader bufferedReader) throws IOException {
        String headerLine = bufferedReader.readLine();
        if (headerLine != null && !headerLine.isEmpty()) {
            StringTokenizer parser = new StringTokenizer(headerLine);
            String method = parser.nextToken().toUpperCase();
            String url = "";
            if (parser.hasMoreTokens()) {
                url = parser.nextToken();
            }
            if (ERequestMethods.contains(method)) {
                this.requestMethod = method;
                // WebURL.isValidURL()
                // TODO better handling for isValidURL
                if (!url.isEmpty()) {
                    this.rawPath = url;
                    this.isValidRequest = true;
                }
            }
        }
    }

    private void parseHeaderMetadata(BufferedReader bufferedReader) throws IOException {
        String headerLine = "";
        while ((headerLine = bufferedReader.readLine()) != null && !headerLine.isEmpty()) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            String[] splitLine = headerLine.split("[:]");
            if (splitLine.length > 1) {
                headers.put(splitLine[0].toLowerCase().trim(), splitLine[1].trim());
            }
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
        if (this.requestBody.isEmpty()) {
            return null;
        }
        return new ByteArrayInputStream(this.requestBody.getBytes(StandardCharsets.UTF_8));

    }

    @Override
    public String getContentString() {
        if (this.requestBody.isEmpty()) {
            return null;
        }
        return this.requestBody;
    }

    @Override
    public byte[] getContentBytes() {
        if (this.requestBody.isEmpty()) {
            return null;
        }
        return this.requestBody.getBytes(StandardCharsets.UTF_8);
    }

    public String toString() {
        return getRequestLine() + getRequestHeader() + "\n\n" + this.requestBody;
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
