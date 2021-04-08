package ooga;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.ViewController;
import ooga.view.GameScene;
import ooga.view.GameSceneFactory;
import ooga.view.GameWindow;
import ooga.view.StageWindow;
import ooga.view.*;

/**
 * This is the class that is run to instantiate the front and back end, and link them together
 *
 * @author Casey Szilagyi
 */

public class Main extends Application {

  private ResourceBundle initFile;
  private static final String DEFAULT_RESOURCES_PACKAGE = "ooga.view.resources.";
  private GameSceneFactory sceneFactory = new GameSceneFactory();
  private GameWindowFactory gameWindowFactory = new GameWindowFactory();

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The start method is called after the init method has returned, and after the system is ready
   * for the application to begin running. This method also connects instantiates the APIs
   * and creates the initial window.
   *
   * @param primaryStage The primary stage for this application where the scene is set. This stage
   *                     is not used, and instead a {@code Stage} subclass
   *                     {@link GameWindow} is instantiated and shown.
   * @throws Exception if something goes wrong
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    FrontEndExternalAPI viewController = new ViewController();
    BackEndExternalAPI modelController = new ModelController();
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);
    initFile = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + "init");

    GameWindow initialWindow = getInitialWindow();
    String initSceneName = initFile.getString("initialWindowScene");
    GameScene newScene = sceneFactory.makeScene(initSceneName);
    initialWindow.showScene(newScene);
    initialWindow.makeVisible();
  }

  /**
   * Gets the correct subclass of {@link GameWindow} to show the initial window of the
   * game. This searches for the "initialWindowType" attribute in the init file and
   * instantiates the window type requested. Will print to stacktrace if instantiation
   * fails.
   * @return a {@code GameWindow} instance.
   */
  private GameWindow getInitialWindow(){
    String windowType = initFile.getString("initialWindowType");
    return gameWindowFactory.makeWindow(windowType);
  }

}
