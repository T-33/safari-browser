package view;

import controller.NavigationController;
import model.Model;
import model.NavigationModel;
import model.baseproperties.BaseProperties;
import model.baseproperties.PageRenderArea;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private Canvas canvas;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Safari Browser";

    public MainView() {
        super(TITLE);

        NavigationModel navModel = new NavigationModel();
        Model model = Model.getInstance();
        NavigationController controller = new NavigationController(navModel, model);

        initializeUI();
        canvas.setNavigationController(controller);

        model.setCanvas(canvas);

        final int scrollBarWidth = 20;

        PageRenderArea.setWidth(WIDTH - scrollBarWidth);
        PageRenderArea.setHeight(HEIGHT);
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JScrollPane scrollPane = new JScrollPane(canvas);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
