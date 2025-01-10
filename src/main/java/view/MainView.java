package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for the browser.
 */

public class MainView extends JFrame {
    private JFrame frame;
    private Canvas canvas;

    /**
     * Initializes the main window.
     */
    public MainView() {
        frame = new JFrame("Safari Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public Canvas getCanvas(){
        return canvas;
    }
    public JFrame getFrame(){
        return frame;
    }
}
