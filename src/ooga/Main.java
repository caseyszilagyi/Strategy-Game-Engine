package ooga;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.view.GameWindow;
import ooga.view.*;

/**
 * This is the class that is run to instantiate the front and back end, and link them together
 *
 * @author Casey Szilagyi
 */

public class Main extends Application {

  private ResourceBundle initFile;
  private static final String DEFAULT_RESOURCES_PACKAGE = "ooga.view.resources.";

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

    initFile = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + "init");
    new ViewManager(initFile);
  }



}
