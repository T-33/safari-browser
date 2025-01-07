package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GenericMouseListener extends MouseAdapter {
    private Runnable action;
    public GenericMouseListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        action.run();
    }
}
