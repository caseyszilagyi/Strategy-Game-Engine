package ooga.view;

import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public abstract class GameScene extends Scene {

  public static final String DEFAULT_RESOURCES_PACKAGE = "view.resources.";
  public static final String DEFAULT_RESOURCES_PATH = "ooga/view/resources/";
  private ResourceBundle resources;

  public GameScene(Parent root, ResourceBundle resources){
    super(root);
    this.resources = resources;

  }

  public void setSceneSize(int width, int height){
    GridPane sceneRoot = (GridPane) this.getRoot();
    sceneRoot.setMinWidth(width);
    sceneRoot.setMinHeight(height);
  }

  public Label makeLabel(String property) {
    Label label = new Label();
    label.setId(property);
    label.setText(resources.getString(label.getId()));

    return label;
  }

  public abstract void populateScene();

}


