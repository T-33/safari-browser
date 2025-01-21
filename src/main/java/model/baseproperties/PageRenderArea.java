package model.baseproperties;

/**
 * Represents rectangular area of canvas on which HTML would be rendered.
 * @see model.CustomCanvas
 */
public class PageRenderArea {
    private int x;
    private int y;
    private int width;
    private int height;

    public PageRenderArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * By default, each field is zero.
     */
    public PageRenderArea() {
        this(0, 0, 0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}