package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;
import model.htmlParser.parser.dom.DomText;
import model.renderTree.dom.RenderText;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Used for storing individual words for proper line wrapping.
 * Or storing said words as children.
 */
public class LayoutTextBox extends LayoutBox{
    private String text;

    public LayoutTextBox(String text) {
        super(BoxType.INLINE);
        this.text = text;
    }

    /**
     * Calculates blocks position and size.
     * @param containingBox parent of box. If null, then
     */
    @Override
    public void layout(LayoutBox containingBox) {
        setHeight(calculateHeight());
        setWidth(calculateWidth());
    }

    /**
     *  calculates height of string of text using default font name and font size
     * @see model.baseproperties.BaseProperties;
     */
    private int calculateHeight() {
        //temporary buffered image object to access Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        String defaultFontName = BaseProperties.getBaseFontName();
        int defaultFontSize = BaseProperties.getBaseFontSize();

        Font font = new Font(defaultFontName,  Font.PLAIN, defaultFontSize);
//        g2.setFont(font);

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        String text = getText();

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

        String defaultFontName = BaseProperties.getBaseFontName();
        int defaultFontSize = BaseProperties.getBaseFontSize();

        Font font = new Font(defaultFontName,  Font.PLAIN, defaultFontSize);
        g2.setFont(font);

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        String text = getText();

        int textLength = fontMetrics.stringWidth(text);

        return textLength;
    }

    public String getText() {
        return text;
    }
}
