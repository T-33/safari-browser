package model;

import model.Network.Network;

import java.awt.image.BufferedImage;

public class Model {
    private static Model instance;
    private final Network network;

    private Model() {
        this.network = new Network();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public String fetchHtml(String url) {
        try {
            return network.getResponse(url).getHtml();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String fetchStyles(String cssUrl) {
        try {
            return network.getResponse(cssUrl).getCss();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage fetchImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        return network.getImage(imageUrl);
    }

}
