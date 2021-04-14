package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import ooga.view.resources.WelcomeScene;

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


  /**
   * Creates a {@link GameScene} with a {@code ResourceBundle} that holds information about
   * the scene being created.
   * @param resources a {@code ResourceBundle} for this scene
   */
  public GameScene makeScene(ResourceBundle resources) {

    this.resources = resources;
    Parent root = new GridPane();

    String sceneType = resources.getString("sceneType");
    GameScene myScene = null;
    try {
      myScene = (GameScene) this.getClass()
          .getDeclaredMethod("make" + sceneType, Parent.class, ResourceBundle.class)
          .invoke(this, new Object[]{root, resources});
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return myScene;
  }

  /**
   * Creates a {@link GameScene} of type {@link WelcomeScene}.
   * @param root the {@code Parent} object to act as the root of the scene
   * @param resources a {@code ResourceBundle} holding scene data files
   * @return a {@code GameScene} object
   */
  private GameScene makeWelcomeScene(Parent root, ResourceBundle resources){
    GameScene newScene = new WelcomeScene(root, resources);

    return newScene;
  }

  /**
   * Creates a {@link GameScene} of type {@link BoardScene}.
   * @param root the {@code Parent} object to act as the root of the scene
   * @param resources a {@code ResourceBundle} holding scene data files
   * @return a {@code GameScene} object
   */
  private GameScene makeBoardScene(Parent root, ResourceBundle resources){
    GameScene newScene = new BoardScene(root, resources);
    return newScene;
  }

  /**
   * Creates a {@link GameScene} using the {@code String} name of a resources file.
   * @param sceneFileName name of the {@code GameScene} to instantiate, must match a data file
   *                      name.
   * @return a {@code GameScene} object
   */
  public GameScene makeScene(String sceneFileName) {
    ResourceBundle sceneResources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + sceneFileName);
    return makeScene(sceneResources);
  }

}
