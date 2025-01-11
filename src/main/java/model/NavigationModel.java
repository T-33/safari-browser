package model;

import java.util.Stack;

public class NavigationModel {
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public NavigationModel() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void addUrl(String url) {
        if (undoStack.isEmpty() || !undoStack.peek().equals(url)) {
            undoStack.push(url);
            redoStack.clear();
        }
    }

    public String undo() {
        if (undoStack.size() > 1) {
            redoStack.push(undoStack.pop());
            return undoStack.peek();
        }
        return null;
    }

    public String redo() {
        if (!redoStack.isEmpty()) {
            String url = redoStack.pop();
            undoStack.push(url);
            return url;
        }
        return null;
    }

    public String getCurrentUrl() {
        return undoStack.isEmpty() ? "" : undoStack.peek();
    }
}
