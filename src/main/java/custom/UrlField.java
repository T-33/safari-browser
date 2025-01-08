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
        setPreferredSize(new Dimension(700, 30));
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //фон
        g2d.setColor(focused ? Color.WHITE : Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //текст
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = 5;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString(text.toString(), textX, textY);

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
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !text.isEmpty()) {
            text.deleteCharAt(text.length() - 1);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

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
}
