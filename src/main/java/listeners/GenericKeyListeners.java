package listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GenericKeyListeners extends KeyAdapter {
    private Runnable onEnter;
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
//слушатель выполнит действие если пользователь нажмет enter