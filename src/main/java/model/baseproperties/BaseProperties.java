package model.baseproperties;

/**
 * Class containing default font and render area rectangle on which webpage is rendered.
 * Render area is adjusted when window is resized.
 */
public class BaseProperties {
    private static final int BASE_FONT_SIZE = 16;
    private static final String DEFAULT_FONT = "SansSerif";

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
}
