package view;

import custom.SearchButton;
import custom.UrlField;
import listeners.GenericKeyListeners;
import listeners.GenericMouseListener;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    //?? модель не готова
    private UrlField urlField;
    private SearchButton searchButton;

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

        searchButton.addMouseListener(new GenericMouseListener(() -> {
            //логика загрузки страницы по URL
        }));
        urlField.addKeyListener(new GenericKeyListeners(() -> {
            //логика передачи url из urlField а модель
        }));
    }

    public UrlField getUrlField() {
        return urlField;
    }

    public SearchButton getSearchButton() {
        return searchButton;
    }
}
