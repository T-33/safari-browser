package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom button component with hover and click effects.
 */

public class SearchButton extends JComponent implements MouseListener {
    private String text;
    private boolean hovered = false;
    private boolean pressed = false;

    /**
     * Initializes the button with the given text.
     * @param text The label displayed on the button.
     */
    public SearchButton(String text) {
        this.text = text;
        setPreferredSize(new Dimension(50, 30));
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arcSize = 15;

        // Draw button background
        g2d.setColor(pressed ? Color.GRAY : hovered ? Color.LIGHT_GRAY : Color.WHITE);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcSize, arcSize));

        // Draw button text
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = (getWidth() - fm.stringWidth(text)) / 2;
        int textHeight = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(text, textWidth, textHeight);

        // Draw button border
        g2d.setColor(Color.black);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
}
