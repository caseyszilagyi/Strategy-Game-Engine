package ooga.view;

import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ooga.controller.ModelController;

public class BoardScene extends GameScene {

  private ResourceBundle resources;
  private GridPane sceneRoot;
  private ModelController modelController;

  public BoardScene(Parent root, ResourceBundle resources,
      ModelController modelController) {
    super(root, resources);
    this.modelController = modelController;
    this.resources = resources;
    sceneRoot = (GridPane) root;
    sceneRoot.getStyleClass().add("boardScene");
    sceneRoot.setVgap(30);
    this.getStylesheets().add(DEFAULT_RESOURCES_PATH + resources.getString("CSS"));
    populateScene();
  }

  @Override
  public void populateScene() {
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setHalignment(HPos.CENTER);
    sceneRoot.getColumnConstraints().add(col1);

    HBox topBar = new HBox();
    topBar.setAlignment(Pos.CENTER);

    Label welcomeLabel = makeLabel("title-text");
    sceneRoot.add(welcomeLabel, 0, 0);

    Button pauseButton = makeButton("pauseButton",
        e -> System.out.println("Pause Button clicked"));

    Button settingsButton = makeButton("settingsButton",
        e -> System.out.println("Settings Button clicked"));

    Button helpButton = makeButton("helpButton",
        e -> System.out.println("Help Button clicked"));

    topBar.getChildren().addAll(pauseButton, settingsButton, helpButton);

    sceneRoot.add(topBar, 0, 1);
    sceneRoot.add(new Board(8, 8, modelController), 0, 3);
  }
}
