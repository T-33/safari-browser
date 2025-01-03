package model;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private URL link;
    private Map<String, String> headers = new HashMap<>();

    public String request(String url) throws Exception {
        link = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) link.openConnection();
        connection.setRequestMethod("GET");
        updateHeaders();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();
        return response.toString();
    }

    private Map<String, String> updateHeaders(){
        headers.clear();
//        headers.put("Host", "www.youtube.com");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.put("Cookie", "VISITOR_INFO1_LIVE=Y-PbsKo7FYg; VISITOR_PRIVACY_METADATA=CgJLRxIEGgAgRg%3D%3D; PREF=f7=4100&f4=4000000&tz=Asia.Bishkek&f5=20000&f6=40000000; YSC=fmZXz5bkpBg; SID=g.a000rwgvoxAAkf6YWuWDFSb-Q9Fi09dIpcm0q3A13RCv-U6IJi1LVR7kmbgvUHyE7hzkTWFCfwACgYKAbkSARYSFQHGX2Miei10zRL3bx9DjyAOmZ_oVxoVAUF8yKo9VdRBqFEWvtrtqF0BhDvy0076; __Secure-1PSID=g.a000rwgvoxAAkf6YWuWDFSb-Q9Fi09dIpcm0q3A13RCv-U6IJi1L2mnfj8wuokPbarjTte8FAAACgYKAXcSARYSFQHGX2MiDNuW5Cw4Ac7tLbaSD6dkhxoVAUF8yKoNaF5H8EgiiwKe5jtoGlIp0076; __Secure-3PSID=g.a000rwgvoxAAkf6YWuWDFSb-Q9Fi09dIpcm0q3A13RCv-U6IJi1L9M4tOR5474gUbFcZ5t1z8gACgYKAVgSARYSFQHGX2MiULXNFtj0HgmgE-EFGALWuxoVAUF8yKqsRf61bYgCvD4hHE_1yCFu0076");
        return headers;
    }
}
