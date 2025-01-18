import controller.NavigationController;
import model.NavigationModel;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        NavigationModel model = new NavigationModel();
        NavigationController controller = new NavigationController(model);
        MainView mainView = new MainView();
        mainView.getCanvas().setNavigationController(controller);
    }
}