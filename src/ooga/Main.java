package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.ViewController;

/**
 * This is the class that is run to instantiate the front and back end, and link them together
 *
 * @author Casey Szilagyi
 */

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The start method is called after the init method has returned, and after the system is ready
   * for the application to begin running.
   *
   * @param primaryStage The primary stage for this application where the scene is set.
   * @throws Exception if something goes wrong
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    FrontEndExternalAPI viewController = new ViewController();
    BackEndExternalAPI modelController = new ModelController();
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);
  }

}
