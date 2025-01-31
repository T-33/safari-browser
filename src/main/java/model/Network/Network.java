package model.Network;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network {
    private static final int TIMEOUT = 20000;
    private static final int MAX_REDIRECTS = 5;
    private static List<URL> redirectedPages;
    private static URL url;

    public Network(){}
    public HttpResponse getResponse(String urlString) {
        try {
            if (urlString.startsWith("http")) {
                url = new URL(urlString);
            } else if (urlString.contains(".")) {
                url = new URL("https://" + urlString);
            } else {
                url = new URL(getGoogleReq(urlString));
            }

            String host = url.getHost();
            String path = url.getPath().isEmpty() ? "/" : url.getPath();
            if (url.getQuery() != null) {
                path += "?" + url.getQuery();
            }
            int port = url.getProtocol().equalsIgnoreCase("https") ? 443 : 80;

            for (int i = 0; i < MAX_REDIRECTS; i++) {
                try (Socket socket = createSocket(url, host, port)) {
                    makeRequest(socket, host, path);
                    HttpResponse response = readHttpResponse(socket, url);

                    if (response.getStatusCode() == 200) {
                        return response;
                    }

                    if (response.getStatusCode() == 301 || response.getStatusCode() == 302 || response.getStatusCode() == 303) {
                        String newLocation = response.getHeaders().get("Location");
                        System.out.println(newLocation);
                        if (newLocation != null && newLocation.startsWith("https://www.google.com")) {
                            return response;
                        }
                        url = new URL(url, newLocation);
                        host = url.getHost();
                        path = url.getPath().isEmpty() ? "/" : url.getPath();
                        port = url.getProtocol().equalsIgnoreCase("https") ? 443 : 80;
                        continue;
                    }

                    throw new IOException("Error " + response.getStatusCode());
                }
            }
            throw new IOException("Redirect limit reached");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpResponse readHttpResponse(Socket socket, URL baseUrl) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String statusLine = reader.readLine();

        if (statusLine == null) {
            throw new IOException("Empty response from server");
        }

        String[] statusParts = statusLine.split(" ", 3);
        if (statusParts.length < 3 || !statusParts[1].matches("\\d+")) {
            throw new IOException("Malformed status line: " + statusLine);
        }
        int statusCode = Integer.parseInt(statusParts[1]);

        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                break;
            }
            int colonIndex = line.indexOf(":");
            if (colonIndex > 0) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                headers.put(headerName, headerValue);
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
                        HttpResponse cssResponse = getResponse(cssUrl);
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

    public BufferedImage getImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedImage image = ImageIO.read(connection.getInputStream());
            connection.disconnect();

            return image;

        } catch (IOException e) {
            return null;
        }
    }

    private void makeRequest(Socket socket, String host, String path) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("GET " + path + " HTTP/1.1");
        writer.println("Host: " + host);
        writer.println("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
        writer.println("Connection: close");
        writer.println();
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

    private Socket createSocket(URL url, String host, int port) throws IOException {
        if ("https".equalsIgnoreCase(url.getProtocol())) {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
            sslSocket.startHandshake();
            sslSocket.setSoTimeout(TIMEOUT);
            return sslSocket;
        } else {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            socket.setSoTimeout(TIMEOUT);
            return socket;
        }
    }
}