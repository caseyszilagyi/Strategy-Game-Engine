package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import ooga.controller.ModelController;
import ooga.view.board.BoardScene;

/**
 * Creates {@link GameScene} subclasses, however returns the general {@code GameScene}
 * object rather than the concrete subclass.
 */
public class GameSceneFactory {
  private final int DEFAULT_WIDTH = 500;
  private final int DEFAULT_HEIGHT = 500;

  private final int SCENE_WIDTH = DEFAULT_WIDTH;
  private final int SCENE_HEIGHT = DEFAULT_HEIGHT;
  private final String DEFAULT_RESOURCES_FOLDER = "src/ooga/view/resources/";
  private final String DEFAULT_RESOURCES_PACKAGE = "ooga.view.resources.";
  private EventHandler<ActionEvent> handler;


  /**
   * Creates a {@link GameScene} with a {@code ResourceBundle} that holds information about
   * the scene being created.
   * @param resources a {@code ResourceBundle} for this scene
   * @param handler an {@link EventHandler} for all button events from this scene
   * @param modelController
   */
  public GameScene makeScene(ResourceBundle resources,
      EventHandler<ActionEvent> handler, ModelController modelController) {
    this.handler = handler;
    Parent root = new GridPane();

    String sceneType = resources.getString("sceneType");
    GameScene myScene = null;
    try {
      myScene = (GameScene) this.getClass()
          .getDeclaredMethod("make" + sceneType, Parent.class,
              ResourceBundle.class, ModelController.class)
          .invoke(this, new Object[]{root, resources, modelController});
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return myScene;
  }

  /**
   * Creates a {@link GameScene} using the {@code String} name of a resources file.
   * @param sceneFileName name of the {@code GameScene} to instantiate, must match a data file
   *                      name.
   * @param handler an {@link EventHandler} for all button events from this scene
   * @param modelController
   * @return a {@code GameScene} object
   */
  public GameScene makeScene(String sceneFileName,
      EventHandler<ActionEvent> handler, ModelController modelController) {
    ResourceBundle sceneResources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + sceneFileName);
    return makeScene(sceneResources, handler, modelController);
  }

  /**
   * Creates a {@link GameScene} of type {@link WelcomeScene}.
   * @param root the {@code Parent} object to act as the root of the scene
   * @param resources a {@code ResourceBundle} holding scene data files
   * @param modelController
   * @return a {@code GameScene} object
   */
  private GameScene makeWelcomeScene(Parent root, ResourceBundle resources,
      ModelController modelController){
    GameScene newScene = new WelcomeScene(root, resources, handler, modelController);

    return newScene;
  }

  private GameScene makeGameConfigScene(Parent root, ResourceBundle resources,
      ModelController modelController) {
    GameScene newScene = new GameConfigScene(root, resources, handler, modelController);
    return newScene;
  }

  /**
   * Creates a {@link GameScene} of type {@link BoardScene}.
   * @param root the {@code Parent} object to act as the root of the scene
   * @param resources a {@code ResourceBundle} holding scene data files
   * @param modelController
   * @return a {@code GameScene} object
   */
  private GameScene makeBoardScene(Parent root, ResourceBundle resources,
      ModelController modelController){
    GameScene newScene = new BoardScene(root, resources, handler, modelController);
    return newScene;
  }

}
