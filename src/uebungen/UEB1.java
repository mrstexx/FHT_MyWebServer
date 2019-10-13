package uebungen;

import BIF.SWE1.interfaces.Url;
import mywebserver.http.WebURL;

public class UEB1 {

    public Url getUrl(String path) {
        return new WebURL(path);
    }

    public void helloWorld() {
        System.out.println("Hello, World!");
    }
}