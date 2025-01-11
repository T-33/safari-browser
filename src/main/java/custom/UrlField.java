package custom;

import javax.swing.*;
import java.awt.*;
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
    private boolean focused = false;

    /**
     * Initializes the URL input field.
     */
    public UrlField() {
        text = new StringBuilder();
        setPreferredSize(new Dimension(500, 30));
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g2d.setColor(Color.WHITE);
        int arcSize = 15;
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcSize, arcSize));

        // Draw text
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = 5;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString(text.toString(), textX, textY);

        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize));
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(Character.isLetterOrDigit(c) || c == ' ' || ":/.?&=-".indexOf(c) != -1) {
            text.append(c);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !text.isEmpty()) {
            text.deleteCharAt(text.length() - 1);
            repaint();
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
            pasteFromClopboard();
        }
    }

    private void pasteFromClopboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
            if(clipboardText != null) {
                text.append(clipboardText);
                repaint();
            }
        }catch(UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void addNotify(){
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
}
