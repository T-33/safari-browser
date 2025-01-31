package listeners;

import view.Canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClickListener extends MouseAdapter {
    private final Canvas canvas;

    public MouseClickListener(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        canvas.handleLinkClick(e.getPoint());
    }
}
