package model.baseproperties;

/**
 * Represents rectangular area of canvas on which HTML would be rendered.
 * @see model.CustomCanvas
 */
public class PageRenderArea {
    private static int x;
    private static int y;
    private static int width;
    private static int height;

    public PageRenderArea(int x, int y, int width, int height) {
        PageRenderArea.x = x;
        PageRenderArea.y = y;
        PageRenderArea.width = width;
        PageRenderArea.height = height;
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

    public static int getHeight() {
        return height;
    }

    public static void setX(int x) {
        PageRenderArea.x = x;
    }

    public static void setY(int y) {
        PageRenderArea.y = y;
    }

    public static void setWidth(int width) {
        PageRenderArea.width = width;
    }

    public static void setHeight(int height) {
        PageRenderArea.height = height;
    }
}