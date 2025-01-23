package listeners;

import java.awt.event.KeyEvent;
import java.util.function.Predicate;

public class EnterKeyPredicate implements Predicate<KeyEvent> {
    @Override
    public boolean test(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_ENTER;
    }
}
