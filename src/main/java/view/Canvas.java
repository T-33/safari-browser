package view;

import custom.ActionButton;
import custom.UrlField;
import listeners.GenericKeyListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Stack;

/**
 * Canvas class representing the main panel of the browser.
 * It contains the URL input field, undo/redo buttons, and the search button.
 */
public class Canvas extends JPanel {
    private UrlField urlField; // Custom component for URL input
    private ActionButton searchButton; // Button for triggering a search
    private ActionButton undoButton; // Button for undoing the last action
    private ActionButton redoButton; // Button for redoing the last undone action
    private Stack<String> undoStack; // Stack for undo history
    private Stack<String> redoStack; // Stack for redo history

    /**
     * Initializes the canvas with a URL input field, undo/redo buttons, and a search button.
     */
    public Canvas() {
        //?? model is not ready
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        setLayout(new BorderLayout());

        // URL panel for input field and buttons
        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        urlField = new UrlField();
        searchButton = new ActionButton("Search", this::search);
        undoButton = new ActionButton("Undo", this::undo);
        redoButton = new ActionButton("Redo", this::redo);

        urlField.setFocusable(true);
        urlField.requestFocusInWindow();

        // Add components to the panel
        urlPanel.add(undoButton);
        urlPanel.add(redoButton);
        urlPanel.add(urlField);
        urlPanel.add(searchButton);
        add(urlPanel, BorderLayout.NORTH);

        // Add listener for Enter key press in the URL field
        urlField.addKeyListener(new GenericKeyListeners(
                e -> e.getKeyCode() == KeyEvent.VK_ENTER,
                () -> {
                    String url = urlField.getText();
                    if (!url.isEmpty()) {
                        addToUndoStack(url);
                        System.out.println("Entered URL: " + url);
                    }
                }
        ));
    }

    /**
     * Triggers the search action.
     */
    private void search() {
        String url = urlField.getText();
        if (!url.isEmpty()) {
            addToUndoStack(url); // Add the current URL to the undo stack
            System.out.println("Searching for: " + url); // Placeholder for actual search logic
        }
    }

    /**
     * Adds a text value to the undo stack and clears the redo stack.
     * @param text The text to add to the undo stack.
     */
    private void addToUndoStack(String text) {
        if (undoStack.isEmpty() || !undoStack.peek().equals(text)) {
            undoStack.push(text);
        }
        redoStack.clear(); // Clear redo stack when a new action occurs
    }

    /**
     * Performs the undo action, restoring the previous URL.
     */
    private void undo() {
        if (undoStack.size() > 1) { // Должно быть хотя бы 2 элемента, чтобы сделать undo
            String current = urlField.getText();
            redoStack.push(current); // Сохранение текущего URL в redoStack
            undoStack.pop(); // Удаление текущего URL из undoStack
            String previous = undoStack.peek(); // Получение предыдущего URL
            urlField.setText(previous);
            urlField.repaint();
            System.out.println("Undo: " + previous);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    /**
     * Performs the redo action, restoring the next URL.
     */
    private void redo() {
        if (!redoStack.isEmpty()) {
            String current = urlField.getText();
            if (undoStack.isEmpty() || !undoStack.peek().equals(current)) {
                undoStack.push(current); // Save current text to undo stack
            }
            String next = redoStack.pop(); // Restore the next text
            urlField.setText(next);
            urlField.repaint();
            System.out.println("Redo: " + next);
        } else {
            System.out.println("Nothing to redo.");
        }
    }

    public UrlField getUrlField() {
        return urlField;
    }

    public ActionButton getSearchButton() {
        return searchButton;
    }
}
