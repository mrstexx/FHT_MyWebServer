package mywebserver.response;

import BIF.SWE1.interfaces.Response;
import mywebserver.http.EHttpHeader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WebResponse implements Response {

    private static final String DEFAULT_SERVER_HEADER = "BIF-SWE1-Server";

    private Map<String, String> headers;
    private String serverHeader;
    private String contentType;
    private String content;

    private Integer statusCode;

    public WebResponse() {
        this.headers = new HashMap<>();
        this.headers.put(EHttpHeader.DATE.getValue(), LocalDateTime.now().toString());
        this.headers.put(EHttpHeader.SERVER.getValue(), DEFAULT_SERVER_HEADER);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getContentLength() {
        byte[] bytes = null;
        if (this.content != null) {
            bytes = this.content.getBytes(StandardCharsets.UTF_8);
        }
        return bytes != null ? bytes.length : 0;
    }

    @Override
    public String getContentType() {
        if (this.contentType != null) {
            return this.contentType;
        }
        return "";
    }

    @Override
    public void setContentType(String contentType) {
        if (contentType == null) {
            throw new IllegalStateException();
        }
        this.contentType = contentType;
        this.headers.put(EHttpHeader.CONTENT_TYPE.getValue(), contentType);
    }

    @Override
    public int getStatusCode() throws Exception {
        if (this.statusCode != null) {
            return this.statusCode;
        }
        throw new Exception("Status code null");
    }

    @Override
    public void setStatusCode(int status) {
        this.statusCode = status;
    }

    @Override
    public String getStatus() {
        if (this.statusCode != null) {
            return EStatusCodes.getStatus(this.statusCode);
        }
        throw new IllegalArgumentException("Status code null");
    }

    @Override
    public void addHeader(String header, String value) {
        if (!header.isEmpty() && !value.isEmpty()) {
            if (this.headers.containsKey(header)) {
                this.headers.computeIfPresent(header, (k, v) -> value);
            } else {
                this.headers.put(header, value);
            }
        }
    }

    @Override
    public String getServerHeader() {
        if (this.serverHeader != null) {
            return this.serverHeader;
        }
        return DEFAULT_SERVER_HEADER;
    }

    @Override
    public void setServerHeader(String server) {
        this.serverHeader = server;
        this.headers.put(EHttpHeader.SERVER.getValue(), server);
    }

    @Override
    public void setContent(String content) {
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        this.content = new String(byteContent, StandardCharsets.UTF_8);
    }

    @Override
    public void setContent(byte[] content) {
        this.content = new String(content);
    }

    @Override
    public void setContent(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        this.content = stringBuilder.toString();
    }

    @Override
    public void send(OutputStream network) throws Exception {
        try {
            network.write(this.getHTTPResponse().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHTTPResponse() throws Exception {
        StringBuilder httpResponse = new StringBuilder();
        httpResponse.append(createStatusLine());
        httpResponse.append('\n');
        httpResponse.append(createHeader());
        // extra blank line
        httpResponse.append('\n');
        if (this.contentType != null && this.content == null) {
            // response content is null
            throw new Exception("No content available when content type setted");
        }
        httpResponse.append(this.content);
        return httpResponse.toString();
    }

    private String createStatusLine() {
        return EHttpHeader.HTTP.getValue() +
                " " +
                this.getStatus();
    }

    private String createHeader() {
        StringBuilder headerContent = new StringBuilder();
        for (Map.Entry<String, String> stringStringEntry : this.headers.entrySet()) {
            headerContent.append(((Map.Entry) stringStringEntry).getKey()).append(": ").append(((Map.Entry) stringStringEntry).getValue()).append('\n');
        }
        return headerContent.toString();
    }
}
