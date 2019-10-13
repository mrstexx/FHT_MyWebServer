package uebungen;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import mywebserver.request.WebRequest;
import mywebserver.response.WebResponse;
import mywebserver.http.WebURL;

import java.io.InputStream;

public class UEB2 {

    public void helloWorld() {
        System.out.println("Hello, World!");
    }

    public Url getUrl(String s) {
        return new WebURL(s);
    }

    public Request getRequest(InputStream inputStream) {
        return new WebRequest(inputStream);
    }

    public Response getResponse() {
        return new WebResponse();
    }
}
