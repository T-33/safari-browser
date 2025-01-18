package controller;

import model.NavigationModel;

/**
 * Handles interactions between the BrowserModel and the View.
 */
public class NavigationController {
    private final NavigationModel navigationModel;

    public static final String CURRENT_URL = "Current URL: ";
    public static final String UNDO = "Undo: ";
    public static final String REDO = "Redo: ";
    public static final String UNDO_TEXT = "Nothing to undo.";
    public static final String REDO_TEXT = "Nothing to redo.";

    public NavigationController(NavigationModel navigationModel) {
        this.navigationModel = navigationModel;
    }

    public void addUrl(String url) {
        navigationModel.addUrl(url);
        System.out.println(CURRENT_URL + navigationModel.getCurrentUrl());
    }

    public String undo() {
        String previousUrl = navigationModel.undo();
        if (previousUrl != null) {
            System.out.println(UNDO + previousUrl);
        } else {
            System.out.println(UNDO_TEXT);
        }
        return previousUrl;
    }

    public String redo() {
        String nextUrl = navigationModel.redo();
        if (nextUrl != null) {
            System.out.println(REDO + nextUrl);
        } else {
            System.out.println(REDO_TEXT);
        }
        return nextUrl;
    }
}
