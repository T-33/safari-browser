package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for the browser.
 */
public class MainView extends JFrame {
    private Canvas canvas;

    public MainView() {
        super("Safari Browser");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
