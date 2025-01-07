package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenericActionListener implements ActionListener {
    private Runnable action;

    public GenericActionListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.run();
    }
}
// передаём в этот класс действие (через Runnable), которое нужно выполнить при нажатии кнопки.