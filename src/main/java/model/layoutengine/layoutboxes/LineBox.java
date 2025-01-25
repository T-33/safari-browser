package model.layoutengine.layoutboxes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LineBox {
    private List<LayoutBox> children = new ArrayList<>();
    private float width = 0;
    private float height = 0;

    public void addChild(LayoutBox child) {
        children.add(child);
    }

    public void layout(LayoutBox containingBox) {
        float availableWidth = containingBox.getWidth();
        float currentX = 0;
        float currentY = 0;

        for (LayoutBox child : children) {
            if (child instanceof LayoutTextBox) {
                // Handle text elements
                processTextElement((LayoutTextBox) child, availableWidth, currentX, currentY);
            } else {
                // Handle other inline elements
                processInlineChild(child, availableWidth, currentX, currentY);
            }

            // Update currentX and currentY
            currentX += child.getWidth();
            height = Math.max(height, child.getHeight());
        }

        // Update the line box's width
        width = currentX;
    }

    private void processTextElement(LayoutTextBox textElement, float availableWidth, float currentX, float currentY) {
        String text = textElement.getText();
        Font font = textElement.getFont();
        String[] words = text.split(" ");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean addSpace = i < words.length - 1;
            String segmentText = addSpace ? word + " " : word;

            float segmentWidth = measureTextWidth(segmentText, font);
            float segmentHeight = measureTextHeight(font);

            // Check if the segment fits in the current line
            if (currentX + segmentWidth > availableWidth) {
                // Move to the next line
                currentX = 0;
                currentY += height;
                height = 0;
            }

            // Position the text segment
            textElement.setX(currentX);
            textElement.setY(currentY);
            currentX += segmentWidth;
            height = Math.max(height, segmentHeight);
        }
    }

    private void processInlineChild(LayoutBox child, float availableWidth, float currentX, float currentY) {
        float childWidth = child.getWidth();
        float childHeight = child.getHeight();

        // Check if the child fits in the current line
        if (currentX + childWidth > availableWidth) {
            // Move to the next line
            currentX = 0;
            currentY += height;
            height = 0;
        }

        // Position the child
        child.setX(currentX);
        child.setY(currentY);
        currentX += childWidth;
        height = Math.max(height, childHeight);
    }

    /**
     * Line's width is equal to containing box's width
     * @param containingBox
     * @return
     */
    public float getRemainingSpace(LayoutBox containingBox) {
        return width - childWidthSum();
    }

    public float childWidthSum() {
        float sum = 0;
        for(LayoutBox child : children) {
            sum += child.getWidth();
        }
        return sum;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    /**
     *  calculates height of string of text using default font name and font size
     * @see model.baseproperties.BaseProperties;
     */
    private int measureTextHeight(Font font) {
        //temporary buffered image object to access Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setFont(font);

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        int textHeight = fontMetrics.getHeight();

        return textHeight;
    }

    /**
     *  calculates width of string of text using default font name and font size
     * @see model.baseproperties.BaseProperties;
     */
    private int measureTextWidth(String segmentText, Font font) {
        //temporary buffered image object to access Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setFont(font);

        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        int textWidth = fontMetrics.stringWidth(segmentText);

        return textWidth;
    }
}