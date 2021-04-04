package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class GameWindow extends Stage {
  ResourceBundle resources;
  private final String DEFAULT_RESOURCES_FOLDER = "view.resources.";

  public GameWindow (String filename) {
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCES_FOLDER + filename);
    this.show();
  }

  private GameScene makeScene(String sceneName) {
    try {
      String sceneFactoryName = sceneName + "SceneFactory";
      ResourceBundle sceneResources = ResourceBundle
          .getBundle(DEFAULT_RESOURCES_FOLDER + sceneName);
      Class factory = Class.forName(sceneFactoryName);
      AbstractGameSceneFactory sceneFactory = (AbstractGameSceneFactory) factory.getConstructor()
          .newInstance(sceneResources);
      return sceneFactory.getScene();
    } catch (ClassNotFoundException | NoSuchMethodException |
        IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void makeSceneAndShow(String sceneName) {
    this.setScene(makeScene(sceneName));
  }

}
