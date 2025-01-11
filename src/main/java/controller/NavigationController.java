package controller;

import model.NavigationModel;

/**
 * Handles interactions between the BrowserModel and the View.
 */
public class NavigationController {
    private NavigationModel navigationModel;
    public NavigationController(NavigationModel navigationModel) {
        this.navigationModel = navigationModel;
    }
    public void addUrl(String url) {
        navigationModel.addUrl(url);
        System.out.println("Current URL: " + navigationModel.getCurrentUrl());
    }
    public String undo() {
        String previousUrl = navigationModel.undo();
        if (previousUrl != null) {
            System.out.println("Undo: " + previousUrl);
        } else {
            System.out.println("Nothing to undo.");
        }
        return previousUrl;
    }
    public String redo() {
        String nextUrl = navigationModel.redo();
        if (nextUrl != null) {
            System.out.println("Redo: " + nextUrl);
        } else {
            System.out.println("Nothing to redo.");
        }
        return nextUrl;
    }
}
