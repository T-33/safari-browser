package view;

import controller.NavigationController;
import custom.ActionButton;
import custom.UrlField;
import listeners.GenericKeyListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Main panel containing the URL input field and action buttons.
 */
public class Canvas extends JPanel {
    private final UrlField urlField;
    private final ActionButton searchButton;
    private final ActionButton undoButton;
    private final ActionButton redoButton;
    private NavigationController navigationController;

    public static final String SEARCH_BUTTON_TEXT = "Search";
    public static final String UNDO_BUTTON_TEXT = "Undo";
    public static final String REDO_BUTTON_TEXT = "Redo";
    /**
     * Initializes the canvas with a URL input field, undo/redo buttons, and a search button.
     */
    public Canvas() {
        //todo: integrate model

        setLayout(new BorderLayout());

        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        urlField = new UrlField();
        searchButton = new ActionButton(SEARCH_BUTTON_TEXT, this::search);
        undoButton = new ActionButton(UNDO_BUTTON_TEXT, this::undo);
        redoButton = new ActionButton(REDO_BUTTON_TEXT, this::redo);

        urlPanel.add(undoButton);
        urlPanel.add(redoButton);
        urlPanel.add(urlField);
        urlPanel.add(searchButton);
        add(urlPanel, BorderLayout.NORTH);

        urlField.addKeyListener(new GenericKeyListeners(
                e -> e.getKeyCode() == KeyEvent.VK_ENTER,
                this::search
        ));
    }

    /**
     * Triggers the search action.
     */
    private void search() {
        String url = urlField.getText();
        if (!url.isEmpty()) {
            navigationController.addUrl(url);
        }
    }

    /**
     * Performs the undo action, restoring the previous URL.
     */
    private void undo() {
        String previousUrl = navigationController.undo();
        if (previousUrl != null) {
            urlField.setText(previousUrl);
        }
    }
    /**
     * Performs the redo action, restoring the next URL.
     */
    private void redo() {
        String nextUrl = navigationController.redo();
        if (nextUrl != null) {
            urlField.setText(nextUrl);
        }
    }

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public UrlField getUrlField() {
        return urlField;
    }

    public ActionButton getSearchButton() {
        return searchButton;
    }
}
