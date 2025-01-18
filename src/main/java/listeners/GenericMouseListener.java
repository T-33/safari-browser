package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A generic mouse listener to execute a specific action on mouse click.
 */
public class GenericMouseListener extends MouseAdapter {
    private Runnable action;

    /**
     * Initializes the listener with the action to perform.
     * @param action The action to execute on mouse click.
     */
    public GenericMouseListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        action.run();
    }
}