package model.Network;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private URL url;
    private static final int timeLimit = 10000;
    private static final int MAX_REDIRECTS = 5;



    public Network(){}
    public HttpResponse getPage(String urlString) {
        System.out.println(urlString);
        for (int i = 0; i < MAX_REDIRECTS; i++) {
            try {
                URL url = urlString.startsWith("http") ? new URL(urlString) :
                        urlString.contains(".") ? new URL("https://" + urlString) :
                                new URL(getGoogleReq(urlString));

                String host = url.getHost();
                String path = url.getPath().isEmpty() ? "/" : url.getPath();
                int port = url.getProtocol().equalsIgnoreCase("https") ? 443 : 80;

                SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

                try (SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port)) {
                    sslSocket.startHandshake();
                    sslSocket.setSoTimeout(timeLimit);
                    sslSocket.setEnabledProtocols(new String[]{"TLSv1.2"});

                    Request(sslSocket, host, path);
                    HttpResponse response = readHttpResponse(sslSocket, url);

                    int statusCode = response.getStatusCode();

                    if (statusCode == 301 || statusCode == 302 || statusCode == 303) {
                        String newLocation = response.getHeaders().get("Location");
                        if (newLocation != null) {
                            urlString = newLocation;
                            continue;
                        }
                    }

                    if (statusCode == 200) {
                        return response;
                    }

                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        System.out.println("Превышен лимит редиректов (" + MAX_REDIRECTS + "). Возвращаем последний доступный ответ.");
        return null;
    }



    private void Request(SSLSocket sslSocket, String host, String path) throws IOException {
        PrintWriter writer = new PrintWriter(sslSocket.getOutputStream(), true);
        writer.println("GET " + path + " HTTP/1.1");
        writer.println("Host: " + host);
        writer.println("Connection: close");
        writer.println("User-Agent: JavaClient/1.0");
        writer.println();
    }

    private HttpResponse readHttpResponse(SSLSocket sslSocket, URL baseUrl) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        String statusLine = reader.readLine();

        String[] statusParts = statusLine.split(" ", 3);
        if (statusParts.length < 3 || !statusParts[1].matches("\\d+")) {
            throw new IOException("Malformed status line: " + statusLine);
        }
        int statusCode = Integer.parseInt(statusParts[1]);

        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex > 0) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                headers.put(headerName, headerValue);
            } else {
                throw new IOException(line);
            }
        }

        StringBuilder body = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            body.append(line).append("\n");
        }

        String htmlBody = body.toString().trim();
        String cssResources = getStyles(htmlBody, baseUrl);

        return new HttpResponse(statusCode, headers, htmlBody, cssResources, baseUrl);
    }

    private String getStyles(String html, URL baseUrl) {
        StringBuilder cssResult = new StringBuilder();
        try {
            String[] lines = html.split("\n");
            for (String line : lines) {
                if (line.contains("<link") && line.contains("rel=\"stylesheet\"")) {
                    int hrefStart = line.indexOf("href=\"") + 6;
                    int hrefEnd = line.indexOf("\"", hrefStart);

                    if (hrefStart > 6 && hrefEnd > hrefStart) {
                        String cssPath = line.substring(hrefStart, hrefEnd);
                        String cssUrl = cssPath.startsWith("http")
                                ? cssPath
                                : baseUrl.getProtocol() + "://" + baseUrl.getHost() +
                                (cssPath.startsWith("/") ? cssPath : "/" + cssPath);
                        HttpResponse cssResponse = getPage(cssUrl);
                        if (cssResponse != null) {
                            String cssContent = cssResponse.getHtml();
                            if (cssContent != null) {
                                cssResult.append(cssContent).append("\n");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cssResult.toString();
    }


    public String getGoogleReq(String urlString){
        try {
            String encodedQuery = URLEncoder.encode(urlString, "UTF-8");
            return "https://www.google.com/search?q=" + encodedQuery;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
