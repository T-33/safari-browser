package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainView extends JFrame {
    private Canvas canvas;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Safari Browser";

    public MainView() {
        super(TITLE);
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
