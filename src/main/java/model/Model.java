package model;

import model.Network.Network;
import view.Canvas;

import java.awt.image.BufferedImage;

public class Model {
    private static Model instance;
    private final Network network;
    private Canvas canvas;
    private final Engine engine;

    private Model() {
        this.network = new Network();
        engine = EngineFactory.createEngine();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void renderPage(String url) {
        String fetchedHtml = fetchHtml(url);
        String fetchedCss = fetchStyles(url);

        engine.renderPage(fetchedHtml, fetchedCss, canvas);
    }

    /**
     *
     * @param url - fetched url
     * @return null if error occurred while fetching.
     */
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
