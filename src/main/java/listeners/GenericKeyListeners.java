package listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Predicate;

/**
 * A generic key listener to execute an action based on a condition.
 */
public class GenericKeyListeners extends KeyAdapter {
    private Predicate<KeyEvent> condition;
    private Runnable action;

    /**
     * Initializes the listener with a condition and action.
     * @param condition A predicate to check if the action should execute.
     * @param action The action to execute if the condition is met.
     */
    public GenericKeyListeners(Predicate<KeyEvent> condition, Runnable action) {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (condition.test(e)) {
            action.run();
        }
    }
}
