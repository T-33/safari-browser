package listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A generic key listener to execute a specific action when the Enter key is pressed.
 */
public class GenericKeyListeners extends KeyAdapter {
    private Runnable onEnter;

    /**
     * Initializes the listener with the action to perform.
     * @param onEnter The action to execute when Enter is pressed.
     */
    public GenericKeyListeners(Runnable onEnter) {
        this.onEnter = onEnter;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            onEnter.run();
        }
    }
}