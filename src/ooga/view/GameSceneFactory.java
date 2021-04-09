package ooga.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * Creates {@link GameScene} subclasses, however returns the general {@code GameScene}
 * object rather than the concrete subclass.
 */
public class GameSceneFactory {
  private final int DEFAULT_WIDTH = 500;
  private final int DEFAULT_HEIGHT = 500;

  private int SCENE_WIDTH = DEFAULT_WIDTH;
  private int SCENE_HEIGHT = DEFAULT_HEIGHT;
  private final String DEFAULT_RESOURCES_FOLDER = "src/ooga/view/resources/";
  private final String DEFAULT_RESOURCES_PACKAGE = "ooga.view.resources.";
  private ResourceBundle resources;
  private GameScene myScene;
  private Parent myRoot;


  /**
   * Creates a {@link GameScene} with a {@code ResourceBundle} that holds information about
   * the scene being created. The resource bundle must include the filename of an {@code FXML}
   * file, as the scene is created using the FXML as root.
   * @param resources a {@code ResourceBundle} for this scene
   */
  public GameScene makeScene(ResourceBundle resources) {

    this.resources = resources;
    String FXMLName = resources.getString("FXML");
    try {
      Parent root = FXMLLoader.load(new File(DEFAULT_RESOURCES_FOLDER + FXMLName).toURI().toURL());
//      setSceneSize(Integer.parseInt(resources.getString("sceneWidth")),
//          Integer.parseInt(resources.getString("sceneHeight")));
      myScene = new GameScene(root);

      myRoot = myScene.getRoot();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return myScene;
  }

  public GameScene makeWelcomeScene(ResourceBundle resources){
    if (!resources.getString("sceneType").equals("WelcomeScene")) {

    }

  }

  /**
   * Creates a {@link GameScene} using the {@code String} name of a resources file.
   * @param sceneName name of the {@code GameScene} to instantiate, must match a data file
   *                  name.
   * @return a {@code GameScene} subclass
   */
  public GameScene makeScene(String sceneName) {
    ResourceBundle sceneResources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + sceneName);
    return makeScene(sceneResources);
  }

  /**
   * Sets the scene to the desired width and height
   * @param width desired width of scene
   * @param height desired height of scene
   */
  private void setSceneSize(int width, int height){
    SCENE_WIDTH = width;
    SCENE_HEIGHT = height;
  }
}
