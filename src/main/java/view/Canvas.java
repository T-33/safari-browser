package view;

import custom.SearchButton;
import custom.UrlField;
import listeners.GenericKeyListeners;
import listeners.GenericMouseListener;
import model.Network;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    //?? модель не готова
    private UrlField urlField;
    private SearchButton searchButton;

    public Canvas() {
        //this.model = model;

        setLayout(new BorderLayout());

        JPanel urlPanel = new JPanel(); //почему создали панель
        urlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        urlField = new UrlField();
        searchButton = new SearchButton("Search");

        urlField.setFocusable(true);
        urlField.requestFocusInWindow();

        urlPanel.add(urlField);
        urlPanel.add(searchButton);
        add(urlPanel, BorderLayout.NORTH);

        searchButton.addMouseListener(new GenericMouseListener(() -> {
            //что происходит при нажатии кнопки
        }));
        urlField.addKeyListener(new GenericKeyListeners(() -> {
            //логика загрузки URL
        }));
    }

    public UrlField getUrlField() {
        return urlField;
    }

    public SearchButton getSearchButton() {
        return searchButton;
    }
}
