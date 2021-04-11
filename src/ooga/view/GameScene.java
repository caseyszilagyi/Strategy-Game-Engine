package ooga.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;

public abstract class GameScene extends Scene {

  public static final String DEFAULT_RESOURCES_PACKAGE = "view.resources.";
  public static final String DEFAULT_RESOURCES_PATH = "ooga/view/resources/";
  private ResourceBundle resources;
  private GridPane sceneRoot;

  public GameScene(Parent root, ResourceBundle resources){
    super(root);
    this.resources = resources;
    int sceneWidth = Integer.parseInt(resources.getString("width"));
    int sceneHeight = Integer.parseInt(resources.getString("height"));
    sceneRoot = (GridPane) root;
    setSceneSize(sceneWidth, sceneHeight);
    sceneRoot.setAlignment(Pos.TOP_CENTER);

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

  public Button makeButton (String property, EventHandler<ActionEvent> handler) {
    // represent all supported image suffixes
    final String IMAGE_FILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button result = new Button();
    String label = resources.getString(property);
    if (label.matches(IMAGE_FILE_SUFFIXES)) {
      result.setGraphic(new ImageView(new Image(DEFAULT_RESOURCES_PATH + label)));
    }
    else {
      result.setText(label);
    }
    result.setOnAction(handler);
    return result;
  }

  public abstract void populateScene();

}


