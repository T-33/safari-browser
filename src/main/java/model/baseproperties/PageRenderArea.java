package model.baseproperties;

/**
 * Represents rectangular area of canvas on which HTML would be rendered.
 * @see model.CustomCanvas
 */
public class PageRenderArea {
    private static int x;
    private static int y;
    private static int width;
    private final int height;

    public PageRenderArea(int x, int y, int width, int height) {
        PageRenderArea.x = x;
        PageRenderArea.y = y;
        PageRenderArea.width = width;
        this.height = height;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static int getWidth() {
        return width;
    }
}