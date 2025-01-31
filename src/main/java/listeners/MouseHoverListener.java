package listeners;

import view.Canvas;

import java.awt.Rectangle;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Map;

public class MouseHoverListener extends MouseMotionAdapter {
    private final Canvas canvas;
    private final Map<Rectangle, String> clickableLinks;

    public MouseHoverListener(Canvas canvas, Map<Rectangle, String> clickableLinks) {
        this.canvas = canvas;
        this.clickableLinks = clickableLinks;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean overLink = clickableLinks.keySet().stream().anyMatch(rect -> rect.contains(e.getPoint()));
        canvas.setCursor(overLink ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
    }
}
