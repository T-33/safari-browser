package model;

import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.dom.RenderElement;
import model.renderTree.dom.RenderNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CustomCanvas extends JComponent {
    private LayoutBox rootLayout;
    private final Map<Rectangle, String> clickableLinks = new HashMap<>();

    public CustomCanvas(LayoutBox layoutBox) {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean overLink = clickableLinks.keySet().stream().anyMatch(rect -> rect.contains(e.getPoint()));
                setCursor(overLink ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
            }
        });
    }

    public void drawPage(LayoutBox rootLayout) {
        this.rootLayout = rootLayout;
        repaint();
    }

    private void drawLayout(Graphics2D g2d, LayoutBox layoutBox) {
        RenderNode renderNode = layoutBox.getRenderNode();
        DomElement domElement = getDomElement(renderNode);

        applyStyles(g2d, layoutBox, domElement);

        if (layoutBox instanceof LayoutTextBox textBox) {
            drawText(g2d, textBox, domElement);
        } else if (isImageBox(layoutBox)) {
            drawImage(g2d, layoutBox);
        } else if (layoutBox.getBoxType() == BoxType.BLOCK) {
            drawBlock(g2d, layoutBox, domElement);
        } else if (isLink(domElement)) {
            handleLink(g2d, layoutBox, domElement);
        }

        layoutBox.getChildren().forEach(child -> drawLayout(g2d, child));
    }

    private void drawText(Graphics2D g2d, LayoutTextBox layoutTextBox, DomElement domElement) {
        String text = layoutTextBox.getText();
        if (text == null || text.isEmpty()) {
            return;
        }

        if (domElement == null && layoutTextBox.getRenderNode() != null) {
            DomNode parentNode = layoutTextBox.getRenderNode().getDomNode().getParent();
            if (parentNode instanceof DomElement parentElement) {
                domElement = parentElement;
            }
        }

        if (domElement == null) {
            g2d.setColor(Constants.DEFAULT_TEXT_COLOR);
            g2d.setFont(new Font(Constants.DEFAULT_FONT_FAMILY, Font.PLAIN, Constants.DEFAULT_FONT_SIZE));
            g2d.drawString(text, layoutTextBox.getX(), layoutTextBox.getY() + layoutTextBox.getHeight() - Constants.TEXT_VERTICAL_OFFSET);
            return;
        }

        Map<String, String> styles = domElement.getComputedStyle();

        String textColor = styles.get(Constants.STYLE_COLOR);
        g2d.setColor(parseColorOrDefault(textColor));

        Font font = createFont(domElement);
        g2d.setFont(font);

        int x = layoutTextBox.getX();
        int y = layoutTextBox.getY() + layoutTextBox.getHeight() - Constants.TEXT_VERTICAL_OFFSET;

        String textAlign = styles.get(Constants.STYLE_TEXT_ALIGN);
        if (Constants.ALIGN_CENTER.equalsIgnoreCase(textAlign)) {
            x += (layoutTextBox.getWidth() - g2d.getFontMetrics().stringWidth(text)) / 2;
        } else if (Constants.ALIGN_RIGHT.equalsIgnoreCase(textAlign)) {
            x += layoutTextBox.getWidth() - g2d.getFontMetrics().stringWidth(text);
        }

        g2d.drawString(text, x, y);
    }

    private void drawImage(Graphics2D g2d, LayoutBox layoutBox) {
        RenderNode renderNode = layoutBox.getRenderNode();
        DomElement domElement = getDomElement(renderNode);

        if (domElement == null) return;

        BufferedImage image = Model.getInstance().fetchImage(domElement.getAttribute(Constants.ATTR_SRC));
        if (image == null) return;

        int x = layoutBox.getX();
        int y = layoutBox.getY();
        int width = layoutBox.getWidth() > 0 ? layoutBox.getWidth() : image.getWidth();
        int height = layoutBox.getHeight() > 0 ? layoutBox.getHeight() : image.getHeight();

        g2d.drawImage(image, x, y, width, height, this);
    }

    private void drawBlock(Graphics2D g2d, LayoutBox layoutBox, DomElement domElement) {
        if (domElement != null) {
            applyBackground(g2d, layoutBox, domElement.getComputedStyle());
            Map<String, String> styles = domElement.getComputedStyle();

            if (styles.containsKey(Constants.STYLE_BORDER_COLOR) || styles.containsKey(Constants.STYLE_BORDER_WIDTH)) {
                applyBorder(g2d, layoutBox, styles);
            }
        }

        g2d.setColor(Constants.DEFAULT_TEXT_COLOR);
        g2d.drawRect(layoutBox.getX(), layoutBox.getY(), layoutBox.getWidth(), layoutBox.getHeight());
    }

    private void handleLink(Graphics2D g2d, LayoutBox layoutBox, DomElement domElement) {
        String href = domElement.getAttribute(Constants.ATTR_HREF);
        if (href != null && !href.isEmpty()) {
            clickableLinks.put(new Rectangle(layoutBox.getX(), layoutBox.getY(), layoutBox.getWidth(), layoutBox.getHeight()), href);

            g2d.setColor(Color.BLUE);
            g2d.drawRect(layoutBox.getX(), layoutBox.getY(), layoutBox.getWidth(), layoutBox.getHeight());
        }
    }

    private void handleLinkClick(Point clickPoint) {
        for (Map.Entry<Rectangle, String> entry : clickableLinks.entrySet()) {
            if (entry.getKey().contains(clickPoint)) {
                openLink(entry.getValue());
                break;
            }
        }
    }

    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLink(DomElement domElement) {
        return domElement != null && Constants.TAG_A.equalsIgnoreCase(domElement.getTagName());
    }

    private boolean isImageBox(LayoutBox layoutBox) {
        RenderNode renderNode = layoutBox.getRenderNode();
        if (!(renderNode instanceof RenderElement renderElement)) {
            return false;
        }

        DomElement domElement = (DomElement) renderElement.getDomNode();
        return Constants.TAG_IMG.equalsIgnoreCase(domElement.getTagName()) && domElement.getAttribute(Constants.ATTR_SRC) != null;
    }

    private Font createFont(DomElement domElement) {
        String fontFamily = domElement != null ? domElement.getStyleProperty(Constants.STYLE_FONT_FAMILY) : Constants.DEFAULT_FONT_FAMILY;
        String fontSize = domElement != null ? domElement.getStyleProperty(Constants.STYLE_FONT_SIZE) : null;
        String fontWeight = domElement != null ? domElement.getStyleProperty(Constants.STYLE_FONT_WEIGHT) : null;
        String fontStyle = domElement != null ? domElement.getStyleProperty(Constants.STYLE_FONT_STYLE) : null;

        int size = parseSize(fontSize, Constants.DEFAULT_FONT_SIZE);
        int style = parseFontStyle(fontWeight, fontStyle);

        return new Font(fontFamily, style, size);
    }

    private void applyStyles(Graphics2D g2d, LayoutBox layoutBox, DomElement domElement) {
        if (domElement == null) return;

        Map<String, String> styles = domElement.getComputedStyle();
        applyBackground(g2d, layoutBox, styles);
        applyOpacity(g2d, styles);
        applyBorder(g2d, layoutBox, styles);
    }

    private void applyBackground(Graphics2D g2d, LayoutBox layoutBox, Map<String, String> styles) {
        String bgColor = styles.get(Constants.STYLE_BACKGROUND_COLOR);
        if (bgColor != null) {
            g2d.setColor(parseColor(bgColor));
            g2d.fillRect(layoutBox.getX(), layoutBox.getY(), layoutBox.getWidth(), layoutBox.getHeight());
        }
    }

    private void applyOpacity(Graphics2D g2d, Map<String, String> styles) {
        String opacity = styles.get(Constants.STYLE_OPACITY);
        if (opacity != null) {
            float alpha = parseOpacity(opacity);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }
    }

    private void applyBorder(Graphics2D g2d, LayoutBox layoutBox, Map<String, String> styles) {
        String borderColor = styles.get(Constants.STYLE_BORDER_COLOR);
        if (borderColor != null) {
            g2d.setColor(parseColor(borderColor));
            int borderWidth = parseSize(styles.get(Constants.STYLE_BORDER_WIDTH), Constants.DEFAULT_BORDER_WIDTH);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRect(layoutBox.getX(), layoutBox.getY(), layoutBox.getWidth(), layoutBox.getHeight());
        }
    }

    private Color parseColor(String color) {
        if (color == null || color.isEmpty()) {
            return Constants.DEFAULT_TEXT_COLOR;
        }

        try {
            if (color.startsWith(Constants.ATTR_LATTICE)) {
                return Color.decode(color);
            }

            return switch (color.toLowerCase()) {
                case Constants.COLOR_RED -> Color.RED;
                case Constants.COLOR_BLUE -> Color.BLUE;
                case Constants.COLOR_GREEN -> Color.GREEN;
                case Constants.COLOR_BLACK -> Color.BLACK;
                case Constants.COLOR_WHITE -> Color.WHITE;
                case Constants.COLOR_GRAY -> Color.GRAY;
                default -> Constants.DEFAULT_TEXT_COLOR;
            };
        } catch (NumberFormatException e) {
            return Constants.DEFAULT_TEXT_COLOR;
        }
    }

    private Color parseColorOrDefault(String color) {
        try {
            return parseColor(color);
        } catch (Exception e) {
            return Constants.DEFAULT_TEXT_COLOR;
        }
    }

    private float parseOpacity(String value) {
        try {
            float opacity = Float.parseFloat(value);
            return Math.min(Math.max(opacity, 0), 1);
        } catch (NumberFormatException e) {
            return Constants.DEFAULT_OPACITY;
        }
    }

    private int parseFontStyle(String fontWeight, String fontStyle) {
        int style = Font.PLAIN;

        if (Constants.FONT_STYLE_BOLD.equalsIgnoreCase(fontWeight)) {
            style |= Font.BOLD;
        }
        if (Constants.FONT_STYLE_ITALIC.equalsIgnoreCase(fontStyle)) {
            style |= Font.ITALIC;
        }

        return style;
    }

    private int parseSize(String value, int defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.replace(Constants.ATTR_PX, Constants.ATTR_NONE).trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private DomElement getDomElement(RenderNode renderNode) {
        if (renderNode instanceof RenderElement renderElement) {
            if (renderElement.getDomNode() instanceof DomElement domElement) {
                return domElement;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        clickableLinks.clear();
        drawLayout(g2d, rootLayout);
    }

    public static class Constants {
        public static final int DEFAULT_FONT_SIZE = 14;
        public static final String DEFAULT_FONT_FAMILY = "Arial";
        public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
        public static final int DEFAULT_BORDER_WIDTH = 1;
        public static final float DEFAULT_OPACITY = 1.0f;
        public static final int TEXT_VERTICAL_OFFSET = 5;

        public static final String TAG_A = "a";
        public static final String ATTR_HREF = "href";

        public static final String STYLE_BACKGROUND_COLOR = "background-color";
        public static final String STYLE_OPACITY = "opacity";
        public static final String STYLE_BORDER_COLOR = "border-color";
        public static final String STYLE_BORDER_WIDTH = "border-width";
        public static final String STYLE_COLOR = "color";
        public static final String STYLE_FONT_FAMILY = "font-family";
        public static final String STYLE_FONT_SIZE = "font-size";
        public static final String STYLE_FONT_WEIGHT = "font-weight";
        public static final String STYLE_FONT_STYLE = "font-style";
        public static final String STYLE_TEXT_ALIGN = "text-align";
        public static final String FONT_STYLE_BOLD = "bold";
        public static final String FONT_STYLE_ITALIC = "italic";

        public static final String ALIGN_CENTER = "center";
        public static final String ALIGN_RIGHT = "right";

        public static final String TAG_IMG = "img";
        public static final String ATTR_SRC = "src";
        public static final String ATTR_PX = "px";
        public static final String ATTR_NONE = "";
        public static final String ATTR_LATTICE = "#";

        public static final String COLOR_RED = "red";
        public static final String COLOR_BLUE = "blue";
        public static final String COLOR_GREEN = "green";
        public static final String COLOR_BLACK = "black";
        public static final String COLOR_WHITE = "white";
        public static final String COLOR_GRAY = "gray";
    }
}