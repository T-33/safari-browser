package custom;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.BasicStroke;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

/**
 * Custom text input component for entering a URL.
 */
public class UrlField extends JComponent implements KeyListener {
    private StringBuilder text;
    private boolean caretVisible;
    private Timer caretTimer;

    public UrlField() {
        text = new StringBuilder();
        setPreferredSize(new Dimension(UrlFieldConstants.URL_FIELD_WIDTH, UrlFieldConstants.URL_FIELD_HEIGHT));
        addKeyListener(this);
        setFocusable(true);

        caretTimer = new Timer(
                UrlFieldConstants.CARET_BLINK_DELAY,
                new UrlFieldBlinker(this)
        );
        caretTimer.start();
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

    private void drawCaret(Graphics2D g2d, FontMetrics fm, int textX, int textY) {
        int caretX = textX + fm.stringWidth(text.toString());
        int caretTop = textY - fm.getAscent();
        int caretBottom = textY + fm.getDescent()/2;

        g2d.setColor(UrlFieldConstants.CARET_COLOR);
        g2d.setStroke(new BasicStroke(UrlFieldConstants.CARET_WIDTH));
        g2d.drawLine(caretX, caretTop, caretX, caretBottom);
    }

    public boolean isCaretVisible() {
        return caretVisible;
    }

    public void setCaretVisible(boolean caretVisible) {
        this.caretVisible = caretVisible;
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String newText) {
        text = new StringBuilder(newText);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Float(
                UrlFieldConstants.RECTANGLE_X,
                UrlFieldConstants.RECTANGLE_Y,
                getWidth(),
                getHeight(),
                UrlFieldConstants.URL_FIELD_ARC_SIZE,
                UrlFieldConstants.URL_FIELD_ARC_SIZE
        ));

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(UrlFieldConstants.FONT_NAME, Font.PLAIN, UrlFieldConstants.FONT_SIZE));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = UrlFieldConstants.TEXT_X;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(text.toString(), textX, textY);

        g2d.setColor(Color.BLACK);
        g2d.draw(new RoundRectangle2D.Float(
                UrlFieldConstants.RECTANGLE_X,
                UrlFieldConstants.RECTANGLE_Y,
                getWidth() - 1,
                getHeight() - 1,
                UrlFieldConstants.URL_FIELD_ARC_SIZE,
                UrlFieldConstants.URL_FIELD_ARC_SIZE
        ));

        if (isFocusOwner() && caretVisible) {
            drawCaret(g2d, fm, textX, textY);
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

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetterOrDigit(c) || c == ' ' || UrlFieldConstants.ALLOWED_URL_CHARS.indexOf(c) != -1) {
            text.append(c);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (caretTimer != null) {
            caretTimer.stop();
        }
    }
    static final class UrlFieldConstants {

        static final int URL_FIELD_WIDTH = 500;
        static final int URL_FIELD_HEIGHT = 30;
        static final int URL_FIELD_ARC_SIZE = 15;

        static final int TEXT_X = 5;
        static final int RECTANGLE_X = 0;
        static final int RECTANGLE_Y = 0;

        static final String FONT_NAME = "Arial";
        static final int FONT_SIZE = 14;
        static final String ALLOWED_URL_CHARS = ":/.?&=- ";

        static final int CARET_BLINK_DELAY = 500;
        static final int CARET_WIDTH = 1;
        static final Color CARET_COLOR = Color.BLACK;
    }
}
