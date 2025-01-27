package custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UrlFieldBlinker implements ActionListener {

    private final UrlField urlField;

    public UrlFieldBlinker(UrlField urlField) {
        this.urlField = urlField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (urlField.isFocusOwner()) {
            urlField.setCaretVisible(!urlField.isCaretVisible());
            urlField.repaint();
        } else {
            urlField.setCaretVisible(false);
            urlField.repaint();
        }
    }
}
