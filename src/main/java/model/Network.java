package model;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private URL url;
    private Map<String, String> headers;

    public Network(){
        this.headers = new HashMap<>();
    }

    public String getPage(String urlString) throws Exception {
        if (urlString.startsWith("http")) {
            url = new URL(urlString);
        } else if(urlString.contains(".")) {
            url = new URL("https://" + urlString);
        }else{
            url = new URL(getGoogleReq(urlString));
        }
        System.out.println(url);
        String host = url.getHost();
        int port = url.getPort() == -1 ? 80 : url.getPort();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            headers = getHeaders();


            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    out.print(entry.getKey() + ": " + entry.getValue() + "\r\n");
                }
            }
            out.flush();

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }

            System.out.println(response);
            return response.toString();
        }
    }

    public String getGoogleReq(String urlString){
//        TODO create request to google
        return urlString;
    }

    public String getStyles(String urlString) throws Exception{
        URL url = new URL(urlString);
        String host = url.getHost();
        int port = url.getPort() == -1 ? 80 : url.getPort();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.print("GET " + url.getPath() + " HTTP/1.1\r\n");
            out.print("Host: " + host + "\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");
            out.flush();

            StringBuilder response = new StringBuilder();
            String inputLine;
            boolean isBody = false;
            while ((inputLine = in.readLine()) != null) {
                if (isBody) {
                    response.append(inputLine).append("\n");
                }
                if (inputLine.isEmpty()) {
                    isBody = true;
                }
            }
            return response.toString();
        }
    }

    public BufferedImage getImage(String urlString) throws Exception {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to fetch image. HTTP response code: " + responseCode);
        }

        try (InputStream inputStream = connection.getInputStream()) {
            return ImageIO.read(inputStream);
        }
    }



    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9");
        headers.put("Cookie", "VISITOR_INFO1_LIVE=Y-PbsKo7FYg; VISITOR_PRIVACY_METADATA=CgJLRxIEGgAgRg%3D%3D; PREF=f7=4100&f4=4000000&tz=Asia.Bishkek&f5=20000&f6=40000000; YSC=fmZXz5bkpBg; SID=g.a000rwgvoxAAkf6YWuWDFSb-Q9Fi09dIpcm0q3A13RCv-U6IJi1LVR7kmbgvUHyE7hzkTWFCfwACgYKAbkSARYSFQHGX2Miei10zRL3bx9DjyAOmZ_oVxoVAUF8yKo9VdRBqFEWvtrtqF0BhDvy0076; __Secure-1PSID=g.a000rwgvoxAAkf6YWuWDFSb-Q9Fi09dIpcm0q3A13RCv-U6IJi1L2mnfj8wuokPbarjTte8FAAACgYKAXcSARYSFQHGX2MiDNuW5Cw4Ac7tLbaSD6dkhxoVAUF8yKoNaF5H8EgiiwKe5jtoGlIp0076; __Secure-3PSID=g.a000rwgvoxAAkf6YWuWDFSb-Q9Fi09dIpcm0q3A13RCv-U6IJi1L9M4tOR5474gUbFcZ5t1z8gACgYKAVgSARYSFQHGX2MiULXNFtj0HgmgE-EFGALWuxoVAUF8yKqsRf61bYgCvD4hHE_1yCFu0076");
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "Custom-Client/1.0");
        headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");

        return headers;
    }

}
