package view;

import controller.NavigationController;
import model.Model;
import model.NavigationModel;
import model.baseproperties.PageRenderArea;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Viewer extends JFrame {
    private Canvas canvas;
    private final Model model = Model.getInstance();

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Safari Browser";

    public Viewer() {
        super(TITLE);

        this.canvas = new Canvas();
        model.setCanvas(canvas);

        initializeUI();
        if (canvas == null) {
            return;
        }

        NavigationModel navModel = new NavigationModel();
        NavigationController controller = new NavigationController(navModel, model);
        canvas.setNavigationController(controller);

        final int scrollBarWidth = 20;
        PageRenderArea.setWidth(WIDTH - scrollBarWidth);
        PageRenderArea.setHeight(HEIGHT);
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JScrollPane scrollPane = new JScrollPane(canvas);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
