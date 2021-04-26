package ooga.view;

import java.io.File;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ooga.controller.ModelController;

public class GameConfigScene extends GameScene{
  private final ResourceBundle resources;
  private final GridPane sceneRoot;
  private final ModelController modelController;
  private final EventHandler<ActionEvent> handler;

  /**
   * Constructor for {@code GameScene} takes a {@link Parent} object and a {@link ResourceBundle}. The
   * scene root is usually a {@link GridPane} for ease of layout.
   *
   * @param root      the {@code Parent} object to act as the root of the scene
   * @param handler
   * @param resources a {@code ResourceBundle} holding scene data files
   */
  public GameConfigScene(Parent root, ResourceBundle resources,
      EventHandler<ActionEvent> handler, ModelController modelController) {
    super(root, handler, resources);
    sceneRoot = (GridPane) root;
    sceneRoot.getStyleClass().add("configMenu");
    sceneRoot.setVgap(20);
    sceneRoot.setHgap(10);
    this.resources = resources;
    this.handler = handler;
    this.modelController = modelController;
    this.getStylesheets().add(DEFAULT_RESOURCES_PATH + resources.getString("CSS"));
    populateScene();
  }

  @Override
  public void populateScene() {
    ColumnConstraints col1 = makeColumnConstraint(HPos.CENTER);
    sceneRoot.getColumnConstraints().add(col1);

    setTitle("prompt");

    HBox playerNames = new HBox();
    TextField player1 = makeTextField("player1");
    TextField player2 = makeTextField("player2");

    CheckBox playAI = new CheckBox(resources.getString("AI"));
    playAI.setOnAction(e -> player2.setDisable(playAI.isArmed()));

    playerNames.getChildren().addAll(player1, player2);

    sceneRoot.add(playerNames, 0, 1);
    sceneRoot.add(playAI, 0, 2);

    Button cancelButton = makeButton("cancelButton");
    Button startButton = makeButton("startButton");
    Button openButton = makeButton("customStart");

    AtomicReference<File> boardFile = null;

    openButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose a board configuration file to open");
      fileChooser.getExtensionFilters().add(
          new ExtensionFilter("XML files", "*.xml"));
      boardFile.set(fileChooser.showOpenDialog(null));
    });

    sceneRoot.add(openButton, 0, 3);


    Node buttons = makeButtonBar(new Button[]{cancelButton, startButton});

    cancelButton.setOnAction(e -> {
      ((Stage) getWindow()).close();
      handler.handle(e);
    });

    startButton.setOnAction(e -> {
      String user = player1.getText();
      String opponent;
      if (player2.isDisabled()) {
        opponent = "AI";
      } else {
        opponent = player2.getText();
      }
      modelController.setPlayers(user, opponent);

      if (boardFile != null) {
        modelController.setBoardState(boardFile.toString());
      }
      getWindow().hide();
    });

    sceneRoot.add(buttons, 0, 4);
  }
}
