package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JFrame frame;
    private Canvas canvas;

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
