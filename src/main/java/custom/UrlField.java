package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;

public class UrlField extends JComponent implements KeyListener {
    private StringBuilder text;
    private boolean focused = false;

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

        //фон
        g2d.setColor(Color.WHITE);
        int arcSize = 15;
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcSize, arcSize));

        //текст
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = 5;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString(text.toString(), textX, textY);

        //границы
        g2d.setColor(Color.BLACK);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize));
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
    //Этот метод вызывается, когда компонент добавляется в контейнер.
    //Вызывает requestFocusInWindow, чтобы сразу установить фокус на текстовом поле.

    public String getText() {
        return text.toString();
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focused = focusable;
    }
}
