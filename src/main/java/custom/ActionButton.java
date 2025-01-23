package custom;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom button component with hover and click effects.
 */

public class ActionButton extends JComponent implements MouseListener {
    private final String text;
    private boolean hovered = false;
    private boolean pressed = false;
    private final Runnable action;

    /**
     * Initializes the button with the given text.
     * @param text The label displayed on the button.
     */
    public ActionButton(String text, Runnable action) {
        this.text = text;
        this.action = action;
        setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arcSize = Constants.ARC_SIZE;

        g2d.setColor(pressed ? Color.GRAY : hovered ? Color.LIGHT_GRAY : Color.WHITE);
        g2d.fill(new RoundRectangle2D.Float(Constants.RECTANGLE_X, Constants.RECTANGLE_Y, getWidth(), getHeight(), arcSize, arcSize));

        g2d.setColor(Color.black);
        g2d.setFont(new Font(Constants.FONT_NAME, Font.PLAIN, Constants.FONT_SIZE));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = (getWidth() - fm.stringWidth(text)) / 2;
        int textHeight = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(text, textWidth, textHeight);

        g2d.setColor(Color.black);
        g2d.draw(new RoundRectangle2D.Float(Constants.RECTANGLE_X, Constants.RECTANGLE_Y, getWidth() - 1, getHeight() - 1, arcSize, arcSize));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (action != null) {
            action.run();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hovered = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hovered = false;
        repaint();
    }

    public static final class Constants {
        final static int BUTTON_WIDTH = 50;
        final static int BUTTON_HEIGHT = 30;

        final static int ARC_SIZE = 15;
        final static int RECTANGLE_X = 0;
        final static int RECTANGLE_Y = 0;

        final static String FONT_NAME = "Arial";
        final static int FONT_SIZE = 14;
    }
}
