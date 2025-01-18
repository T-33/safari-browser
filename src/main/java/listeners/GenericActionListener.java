package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A generic action listener to execute a specific action when triggered.
 */
public class GenericActionListener implements ActionListener {
    private Runnable action;

    /**
     * Initializes the listener with the action to perform.
     * @param action The action to execute when triggered.
     */
    public GenericActionListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.run();
    }
}
// передаём в этот класс действие (через Runnable), которое нужно выполнить при нажатии кнопки.