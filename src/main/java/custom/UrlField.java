package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UrlField extends JComponent implements KeyListener {
    private StringBuilder text;
    private boolean focused = false;

    public UrlField() {
        text = new StringBuilder();
        setPreferredSize(new Dimension(200, 30));
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //фон
        g2d.setColor(focused ? Color.LIGHT_GRAY : Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //текст
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString(text.toString(), 5, getHeight() / 2 + 5);

        //границы
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(Character.isLetterOrDigit(c) || c == ' ') {
            text.append(c);
            repaint();
        } else if (c == '\b' && !text.isEmpty()) {
            text.deleteCharAt(text.length() - 1);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public String getText() {
        return text.toString();
    }
}
