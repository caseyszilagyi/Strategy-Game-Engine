package ooga.view.resources;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;
import ooga.view.GameScene;

public class WelcomeScene extends GameScene {
  private ResourceBundle resources;
  private GridPane sceneRoot;


  public WelcomeScene(Parent root, ResourceBundle resources) {
    super(root, resources);
    this.resources = resources;
    sceneRoot = (GridPane) root;
    sceneRoot.setHgap(10);
    sceneRoot.setVgap(300);
    sceneRoot.getStyleClass().add("title");
    this.getStylesheets().add(DEFAULT_RESOURCES_PATH + resources.getString("CSS"));
    populateScene();
  }


  @Override
  public void populateScene() {
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setHalignment(HPos.CENTER);
    sceneRoot.getColumnConstraints().add(col1);

    Label welcomeLabel = makeLabel("title-text");
    sceneRoot.add(welcomeLabel, 0, 0);

    Button goButton = makeButton("goButton",
        e -> System.out.println("Button clicked"));

    sceneRoot.add(goButton, 0, 1);
  }




}
