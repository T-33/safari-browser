package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;
import model.htmlParser.parser.dom.DomElement;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * LayoutBox that stores text.
 * It is assumed that text box contains individual words,
 * otherwise text would not be wrapped to new line correctly.
 */
public class LayoutTextBox extends LayoutBox{
    private String text;
    private final DomElement associatedElement;

    public LayoutTextBox(String text, DomElement associatedElement) {
        super(BoxType.INLINE);
        this.text = text;
        this.associatedElement = associatedElement;
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
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setFont(getFont());

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        return fontMetrics.getHeight();
    }

    /**
     * Calculates width of string of text using font.
     */
    private int calculateWidth() {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setFont(getFont());

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        return fontMetrics.stringWidth(getText());
    }

    public String getText() {
        return text;
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
}
