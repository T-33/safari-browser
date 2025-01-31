package model.Network;

import java.net.URL;
import java.util.Map;

public class HttpResponse {
    private final URL url;
    private final int statusCode;
    private final Map<String, String> headers;
    private final String html;
    private final String css;

    public HttpResponse(int statusCode, Map<String, String> headers, String htmlBody, String cssResources, URL url) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.html = htmlBody;
        this.css = cssResources;
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getCss() {
        return css;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHtml() {
        return html;
    }

}

