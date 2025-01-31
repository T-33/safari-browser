package view;

import controller.NavigationController;
import custom.ActionButton;
import custom.UrlField;
import listeners.*;
import model.Model;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.dom.RenderElement;
import model.renderTree.dom.RenderNode;

import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Main panel containing the URL input field and action buttons.
 */
public class Canvas extends JPanel {
    private LayoutBox rootLayout;
    private final Map<Rectangle, String> clickableLinks = new HashMap<>();

    private NavigationController navigationController;

    private final UrlField urlField;
    private final ActionButton searchButton;
    private final ActionButton undoButton;
    private final ActionButton redoButton;
    private final Predicate<KeyEvent> enterKeyPredicate;
    private final Runnable searchAction;

    public static final String SEARCH_BUTTON_TEXT = "Search";
    public static final String UNDO_BUTTON_TEXT = "Undo";
    public static final String REDO_BUTTON_TEXT = "Redo";

    /**
     * Initializes the canvas with a URL input field, undo/redo buttons, and a search button.
     */
    public Canvas() {
        rootLayout = new LayoutBox(BoxType.BLOCK);

        addMouseMotionListener(new MouseHoverListener(this, clickableLinks));
        addMouseListener(new MouseClickListener(this));

        setLayout(new BorderLayout());

        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.enterKeyPredicate = new EnterKeyPredicate();
        this.searchAction = new SearchAction(this);

        urlField = new UrlField();
        searchButton = new ActionButton(SEARCH_BUTTON_TEXT, this::search);
        undoButton = new ActionButton(UNDO_BUTTON_TEXT, this::undo);
        redoButton = new ActionButton(REDO_BUTTON_TEXT, this::redo);

        urlPanel.add(undoButton);
        urlPanel.add(redoButton);
        urlPanel.add(urlField);
        urlPanel.add(searchButton);
        add(urlPanel, BorderLayout.NORTH);

        urlField.addKeyListener(new GenericKeyListeners(enterKeyPredicate, searchAction));
    }

    /**
     * Triggers the search action.
     */
    public void search() {
        String url = urlField.getText();
        if (!url.isEmpty()) {
            navigationController.addUrl(url);
        }
    }

    /**
     * Performs the undo action, restoring the previous URL.
     */
    public void undo() {
        String previousUrl = navigationController.undo();
        if (previousUrl != null) {
            urlField.setText(previousUrl);
        }
    }

    /**
     * Performs the redo action, restoring the next URL.
     */
    public void redo() {
        String nextUrl = navigationController.redo();
        if (nextUrl != null) {
            urlField.setText(nextUrl);
        }
    }

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void drawPage(LayoutBox rootLayout) {
        setPreferredSize(new Dimension(rootLayout.getWidth(), rootLayout.getHeight()));
        getParent().setPreferredSize(new Dimension(rootLayout.getWidth(), rootLayout.getHeight()));
        getParent().revalidate();
        getParent().repaint();

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

        if (layoutBox.getChildren() != null) {
            layoutBox.getChildren().forEach(child -> drawLayout(g2d, child));
        }
    }

    private void drawText(Graphics2D g2d, LayoutTextBox layoutTextBox, DomElement domElement) {
        String text = layoutTextBox.getText();
        if (text == null || text.isEmpty()) {
            return;
        }

        if (domElement == null || domElement.getAttribute(Constants.ATTR_HREF) == null) {
            DomNode parentNode = layoutTextBox.getRenderNode() != null
                    ? layoutTextBox.getRenderNode().getDomNode().getParent()
                    : null;

            if (parentNode instanceof DomElement parentElement) {
                if (isLink(parentElement)) {
                    domElement = parentElement;
                }
            }
        }

        boolean isLink = isLink(domElement);
        if (isLink) {
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font(Constants.DEFAULT_FONT_FAMILY, Font.PLAIN | Font.ITALIC, Constants.DEFAULT_FONT_SIZE));

            FontMetrics fontMetrics = g2d.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(text);
            int textHeight = fontMetrics.getHeight();

            clickableLinks.put(new Rectangle(
                    layoutTextBox.getX(),
                    layoutTextBox.getY() - textHeight,
                    textWidth,
                    textHeight
            ), domElement.getAttribute(Constants.ATTR_HREF));
        }
        else {
            g2d.setColor(Constants.DEFAULT_TEXT_COLOR);
            g2d.setFont(new Font(Constants.DEFAULT_FONT_FAMILY, Font.PLAIN, Constants.DEFAULT_FONT_SIZE));
            g2d.drawString(text, layoutTextBox.getX(), layoutTextBox.getY() + layoutTextBox.getHeight() - Constants.TEXT_VERTICAL_OFFSET);
            return;
        }

        Map<String, String> styles = domElement.getComputedStyle();
        if (styles != null) {
            String textColor = styles.get(Constants.STYLE_COLOR);
            g2d.setColor(parseColorOrDefault(textColor));
            Font font = createFont(domElement);
            g2d.setFont(font);
        }

        int x = layoutTextBox.getX();
        int y = layoutTextBox.getY() + layoutTextBox.getHeight() - Constants.TEXT_VERTICAL_OFFSET;

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

    public void handleLinkClick(Point clickPoint) {
        for (Map.Entry<Rectangle, String> entry : clickableLinks.entrySet()) {
            if (entry.getKey().contains(clickPoint)) {
                String url = entry.getValue();
                navigationController.addUrl(url);

                LayoutBox newLayout = Model.getInstance().renderPage(url);
                if (newLayout != null) {
                    this.rootLayout = newLayout;
                    this.revalidate();
                    this.repaint();
                }
                break;
            }
        }
    }

    private boolean isLink(DomElement domElement) {
        return domElement != null && domElement.getAttribute(Constants.ATTR_HREF) != null;
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
        public static final String FONT_STYLE_BOLD = "bold";
        public static final String FONT_STYLE_ITALIC = "italic";

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