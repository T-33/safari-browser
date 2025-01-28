package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * LayoutBox that stores text.
 * It is assumed that text box contains individual words,
 * otherwise text would not be wrapped to new line correctly.
 */
public class LayoutTextBox extends LayoutBox{
    private String text;

    public LayoutTextBox(String text) {
        super(BoxType.INLINE);
        this.text = text;
    }

    /**
     * Doesn't layout, because it will be broken into lineBoxes by parent inline element
     * @param containingBox not needed, will be needed as layout becomes more complex.
     */
    @Override
    public void layout(LayoutBox containingBox) {
        setHeight(calculateHeight());
        setWidth(calculateWidth());
    }

    /**
     * Should look up font in associated CSS.
     * Now returns default font.
     * @see model.baseproperties.BaseProperties;
     * @return font of text box;
     */
    public Font getFont() {
        String defaultFontName = BaseProperties.getBaseFontName();
        int defaultFontSize = BaseProperties.getBaseFontSize();

        return new Font(defaultFontName,  Font.PLAIN, defaultFontSize);
    }

    /**
     *  Calculates height of string of text using font.
     * @see model.baseproperties.BaseProperties;
     */
    private int calculateHeight() {
        //temporary buffered image object to access Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setFont(getFont());

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        int textHeight = fontMetrics.getHeight();

        return textHeight;
    }

    /**
     * Calculates width of string of text using font.
     */
    private int calculateWidth() {
        //temporary buffered image object to access Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setFont(getFont());

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        int textWidth = fontMetrics.stringWidth(getText());

        return textWidth;
    }

    public String getText() {
        return text;
    }
}
