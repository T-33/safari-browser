package model;

import model.Network.Network;
import model.layoutengine.layoutboxes.LayoutBox;
import view.Canvas;

import java.awt.image.BufferedImage;

public class Model {
    private static Model instance;
    private final Network network;
    private Canvas canvas;
    private Engine engine;

    private Model() {
        this.network = new Network();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }


    public LayoutBox renderPage(String url) {
        assert canvas != null;

        if (engine == null) {
            engine = EngineFactory.createEngine();
        }

        try {
            String fetchedHtml = fetchHtml(url);
            String fetchedCss = fetchStyles(url);
            return engine.renderPage(fetchedHtml, fetchedCss, canvas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

}
