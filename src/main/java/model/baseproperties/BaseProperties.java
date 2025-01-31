package model.baseproperties;

/**
 * Class containing default font and render area rectangle on which webpage is rendered.
 * Render area is adjusted when window is resized.
 */
public class BaseProperties {
    private static final int BASE_FONT_SIZE = 16;
    private static final String DEFAULT_FONT = "SansSerif";

    public static final String DEFAULT_CSS = """
            html, body, div, p, h1, h2, h3, h4, h5, h6, header, footer, section, article, aside, nav, ul, ol, li, table, form, blockquote, hr, main, figure, figcaption, dl, dt, dd {
              display: block;
            }
            
            span, a, strong, em, b, i, u, img, input, label, textarea, button, code, abbr, cite, q, small, sub, sup, time, mark, progress, meter, iframe, canvas, video, audio, select, option {
              display: inline;
            }
            """;

    public static int DEFAULT_IMAGE_WIDTH = 200;
    public static int DEFAULT_IMAGE_HEIGHT = 200;

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
