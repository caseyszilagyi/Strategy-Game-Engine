package ooga.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class GameSceneFactory {
  private final int DEFAULT_WIDTH = 500;
  private final int DEFAULT_HEIGHT = 500;

  private int SCENE_WIDTH = DEFAULT_WIDTH;
  private int SCENE_HEIGHT = DEFAULT_HEIGHT;
  private final String DEFAULT_RESOURCES_FOLDER = "src/ooga/view/resources/";
  private final ResourceBundle resources;
  private GameScene myScene;
  private BorderPane myRoot;

  public GameSceneFactory (ResourceBundle resources) {
    this.resources = resources;
    String FXMLname = resources.getString("FXML");
    try {
      Parent root = FXMLLoader.load(new File(DEFAULT_RESOURCES_FOLDER + FXMLname).toURI().toURL());
      setSceneSize(Integer.parseInt(resources.getString("sceneWidth")),
          Integer.parseInt(resources.getString("sceneHeight")));
      myScene = new GameScene(root, SCENE_WIDTH, SCENE_HEIGHT);

      myRoot = (BorderPane) myScene.getRoot();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setSceneSize(int width, int height){
    SCENE_WIDTH = width;
    SCENE_HEIGHT = height;
  }


  public GameScene getScene () {
    return myScene;
  }
}
