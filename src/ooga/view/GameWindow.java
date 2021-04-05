package ooga.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class GameWindow extends Stage {
  ResourceBundle sceneListBundle;
  private final String DEFAULT_RESOURCES_FOLDER = "ooga.view.resources.";
  private ArrayList<GameScene> allScenes;

  public GameWindow () {
    allScenes = new ArrayList<>();
    sceneListBundle = ResourceBundle.getBundle(DEFAULT_RESOURCES_FOLDER + "sceneList");
    String sceneNames[] = sceneListBundle.getString("allScenes").split(",");
    for (String name : sceneNames) {
      allScenes.add(makeScene(name));
    }
    this.setScene(allScenes.get(0));
  }

  private GameScene makeScene(String sceneName) {
    ResourceBundle sceneResources = ResourceBundle.getBundle(DEFAULT_RESOURCES_FOLDER + sceneName);
    GameSceneFactory sceneFactory = new GameSceneFactory(sceneResources);
    return sceneFactory.getScene();
  }

  public void makeSceneAndShow() {
    this.show();
  }

}
