package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SearchButton extends JComponent implements MouseListener {
    private String text;
    private boolean hovered = false;
    private boolean pressed = false;

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

        //фон
        if(pressed){
            g2d.setColor(Color.gray);
        } else if (hovered) {
            g2d.setColor(Color.lightGray);
        } else {
            g2d.setColor(Color.white);
        }
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //текст кнопки
        g2d.setColor(Color.black);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = (getWidth() - fm.stringWidth(text)) / 2;
        int textHeight = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(text, textWidth, textHeight);

        //границы
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
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
