package model.baseproperties;

public class BaseProperties {
    private static final int BASE_FONT_SIZE = 16;
    private static final String DEFAULT_FONT = "SansSerif";
    private static final PageRenderArea pageRenderArea = new PageRenderArea(0, 0, 1800, 1200);

    /**
     * Non-instantiable.
     */
    private BaseProperties() {}

    public static int getBaseFontSize() {
        return BASE_FONT_SIZE;
    }

    public static String getBaseFontName() {
        return DEFAULT_FONT;
    }

    public static PageRenderArea getPageRenderArea() {
        return pageRenderArea;
    }
}
