package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private Canvas canvas;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public MainView() {
        super("Safari Browser");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
