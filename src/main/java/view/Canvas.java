package view;

import controller.NavigationController;
import custom.ActionButton;
import custom.UrlField;
import listeners.GenericKeyListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Stack;

/**
 * Main panel containing the URL input field and action buttons.
 */
public class Canvas extends JPanel {
    private UrlField urlField; // Custom component for URL input
    private ActionButton searchButton; // Button for triggering a search
    private ActionButton undoButton; // Button for undoing the last action
    private ActionButton redoButton; // Button for redoing the last undone action
    private NavigationController navigationController;
    /**
     * Initializes the canvas with a URL input field, undo/redo buttons, and a search button.
     */
    public Canvas() {
        //?? model is not ready
        setLayout(new BorderLayout());

        // URL panel for input field and buttons
        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        urlField = new UrlField();
        searchButton = new ActionButton("Search", this::search);
        undoButton = new ActionButton("Undo", this::undo);
        redoButton = new ActionButton("Redo", this::redo);

        // Add components to the panel
        urlPanel.add(undoButton);
        urlPanel.add(redoButton);
        urlPanel.add(urlField);
        urlPanel.add(searchButton);
        add(urlPanel, BorderLayout.NORTH);

        // Add listener for Enter key press in the URL field
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
