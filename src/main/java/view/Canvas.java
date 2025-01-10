package view;

import custom.SearchButton;
import custom.UrlField;
import listeners.GenericKeyListeners;
import listeners.GenericMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Canvas class representing the main panel of the browser.
 * It contains the URL input field and the search button.
 */

public class Canvas extends JPanel {
    //?? модель не готова
    private UrlField urlField; // Custom component for URL input
    private SearchButton searchButton; // Custom component for search button

    /**
     * Initializes the canvas with a URL input field and a search button.
     */

    public Canvas() {
        //this.model = model;

        setLayout(new BorderLayout());

        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        urlField = new UrlField();
        searchButton = new SearchButton("Search");

        urlField.setFocusable(true);
        urlField.requestFocusInWindow();

        urlPanel.add(urlField);
        urlPanel.add(searchButton);
        add(urlPanel, BorderLayout.NORTH);

        // Listener for search button clicks
        searchButton.addMouseListener(new GenericMouseListener(() -> {
            //логика загрузки страницы по URL
            String url = urlField.getText();
            if(!url.isEmpty()){
                System.out.println("Searching for: " + url); // Placeholder for page loading logic
            }
        }));

        // Listener for Enter key press in URL field
        urlField.addKeyListener(new GenericKeyListeners(
                e -> e.getKeyCode() == KeyEvent.VK_ENTER,
                () -> {
                    String url = urlField.getText();
                    if (!url.isEmpty()) {
                        System.out.println("Entered URL: " + url); // Placeholder for passing URL to the model
                    }
                }
        ));

    }

    public UrlField getUrlField() {
        return urlField;
    }

    public SearchButton getSearchButton() {
        return searchButton;
    }
}
