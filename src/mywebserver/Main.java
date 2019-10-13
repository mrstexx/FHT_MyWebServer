package mywebserver;

import mywebserver.http.WebURL;

public class Main {
    public static void main(String[] args) {
        WebURL url = new WebURL("http://www.test.com/path.jpg?a=1&b=2" +
                "");
        System.out.println(url.getExtension());
    }
}
