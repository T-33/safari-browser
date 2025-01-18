package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

/**
 * Custom text input component for entering a URL.
 */
public class UrlField extends JComponent implements KeyListener {
    private StringBuilder text;
    private boolean focused = false;

    public UrlField() {
        text = new StringBuilder();
        setPreferredSize(new Dimension(Constants.URL_FIELD_WIDTH, Constants.URL_FIELD_HEIGHT));
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Float(
                Constants.RECTANGLE_X,
                Constants.RECTANGLE_Y,
                getWidth(),
                getHeight(),
                Constants.URL_FIELD_ARC_SIZE,
                Constants.URL_FIELD_ARC_SIZE
        ));

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Constants.FONT_NAME, Font.PLAIN, Constants.FONT_SIZE));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = Constants.TEXT_X;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(text.toString(), textX, textY);

        g2d.setColor(Color.BLACK);
        g2d.draw(new RoundRectangle2D.Float(
                Constants.RECTANGLE_X,
                Constants.RECTANGLE_Y,
                getWidth() - 1,
                getHeight() - 1,
                Constants.URL_FIELD_ARC_SIZE,
                Constants.URL_FIELD_ARC_SIZE
        ));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetterOrDigit(c) || c == ' ' || Constants.ALLOWED_URL_CHARS.indexOf(c) != -1) {
            text.append(c);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !text.isEmpty()) {
            text.deleteCharAt(text.length() - 1);
            repaint();
        }
        else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
            pasteFromClipboard();
        }
    }

    private void pasteFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
            if (clipboardText != null) {
                text.append(clipboardText);
                repaint();
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    public String getText() {
        return text.toString();
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focused = focusable;
    }

    public void setText(String newText) {
        text = new StringBuilder(newText);
        repaint();
    }

    static final class Constants {
        static final int URL_FIELD_WIDTH = 500;
        static final int URL_FIELD_HEIGHT = 30;
        static final int URL_FIELD_ARC_SIZE = 15;

        static final int TEXT_X = 5;
        static final int RECTANGLE_X = 0;
        static final int RECTANGLE_Y = 0;

        static final String FONT_NAME = "Arial";
        static final int FONT_SIZE = 14;
        static final String ALLOWED_URL_CHARS = ":/.?&=- ";

    }
}
