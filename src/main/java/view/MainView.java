package view;

import controller.NavigationController;
import model.Model;
import model.NavigationModel;

import javax.swing.JFrame;
import java.awt.BorderLayout;

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
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }
}
