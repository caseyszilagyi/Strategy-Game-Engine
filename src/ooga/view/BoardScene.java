package ooga.view;

import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardScene extends GameScene {

  private ResourceBundle resources;
  private GridPane sceneRoot;

  public BoardScene(Parent root, ResourceBundle resources) {
    super(root, resources);
    this.resources = resources;
    sceneRoot = (GridPane) root;
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

    Button pauseButton = makeButton("pauseButton",
        e -> System.out.println("Pause Button clicked"));

    Button settingsButton = makeButton("settingsButton",
        e -> System.out.println("Settings Button clicked"));

    Button helpButton = makeButton("helpButton",
        e -> System.out.println("Help Button clicked"));

    sceneRoot.add(pauseButton, 0, 1);
    sceneRoot.add(settingsButton, 1, 1);
    sceneRoot.add(helpButton, 2, 1);
    sceneRoot.add(new Board(8, 8), 0, 3);
  }
}
