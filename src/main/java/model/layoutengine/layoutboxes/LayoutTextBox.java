package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;
import model.htmlParser.parser.dom.DomText;
import model.renderTree.dom.RenderText;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Stores text.
 */
public class LayoutTextBox extends LayoutBox{
    private String text;

    public LayoutTextBox(String text) {
        super(BoxType.INLINE);
        this.text = text;
    }

    /**
     * Doesn't layout, because it will be broken into lineBoxes by parent inline element
     * @param containingBox parent of box. If null, then
     */
    @Override
    public void layout(LayoutBox containingBox) {
        setHeight(calculateHeight());
        setWidth(calculateWidth());
    }

    /**
     * Should look up font in associated CSS.
     * Now return default font.
     * @return
     */
    public Font getFont() {
        String defaultFontName = BaseProperties.getBaseFontName();
        int defaultFontSize = BaseProperties.getBaseFontSize();

        return new Font(defaultFontName,  Font.PLAIN, defaultFontSize);
    }

    /**
     *  calculates height of string of text using default font name and font size
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
     *  calculates width of string of text using default font name and font size
     * @see model.baseproperties.BaseProperties;
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
