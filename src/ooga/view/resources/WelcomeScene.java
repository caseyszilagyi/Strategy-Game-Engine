package ooga.view.resources;

import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ooga.view.GameScene;

public class WelcomeScene extends GameScene {
  private ResourceBundle resources;
  private GridPane sceneRoot;


  public WelcomeScene(Parent root, ResourceBundle resources) {
    super(root, resources);
    this.resources = resources;
    int sceneWidth = Integer.parseInt(resources.getString("width"));
    int sceneHeight = Integer.parseInt(resources.getString("height"));
    sceneRoot = (GridPane) this.getRoot();
    setSceneSize(sceneWidth, sceneHeight);
    sceneRoot.setHgap(10);
    sceneRoot.setVgap(300);
    sceneRoot.getStyleClass().add("title");
    this.getStylesheets().add(DEFAULT_RESOURCES_PATH + resources.getString("CSS"));
    populateScene();
  }


  @Override
  public void populateScene() {
    Label welcomeLabel = makeLabel("title-text");
    sceneRoot.add(welcomeLabel, 0, 0);
  }


}
