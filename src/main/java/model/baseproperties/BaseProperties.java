package model.baseproperties;

public class BaseProperties {
    private static int BASE_FONT_SIZE = 16;
    private static String DEFAULT_FONT = "SansSerif";
    private static PageRenderArea pageRenderArea = new PageRenderArea(0, 0, 800, 600);

    //TODO удалить ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

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

    public static void setBaseFontSize(int newBaseFontSize) {
        BASE_FONT_SIZE = newBaseFontSize;
    }

    public static void setDefaultFont(String defaultFont) {
        DEFAULT_FONT = defaultFont;
    }
}
