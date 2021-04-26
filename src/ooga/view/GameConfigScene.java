package ooga.view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ooga.controller.BoardController;
import ooga.controller.ModelController;
import ooga.exceptions.GameRunningException;

/**
 * This scene holds configuration settings and controls for a new game. This window
 * will float above all other windows until dismissed. The options in this window
 * all have their own specialized event handlers and interact with the {@link ModelController}.
 *
 * @author Yi Chen
 */
public class GameConfigScene extends GameScene{
  private final ResourceBundle resources;
  private final GridPane sceneRoot;
  private final ModelController modelController;
  private BoardController boardController;
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
    playAI.setId("AI");
    playAI.setOnAction(e -> player2.setDisable(playAI.isArmed()));

    playerNames.getChildren().addAll(player1, player2);

    sceneRoot.add(playerNames, 0, 1);
    sceneRoot.add(playAI, 0, 2);

    Button cancelButton = makeButton("cancelButton");
    Button startButton = makeButton("startButton");
    Button openButton = makeButton("customStart");
    //Button pieceTypeButton = makeButton("PieceButton");

    //pieceTypeButton.setOnAction(e -> pieceType());
    openButton.setOnAction(e -> openBoard());

    sceneRoot.add(openButton, 0, 3);
    //sceneRoot.add(pieceTypeButton, 1, 3);

    Node buttons = makeButtonBar(new Button[]{cancelButton, startButton});

    cancelButton.setOnAction(this::cancelButton);

    startButton.setOnAction(e -> {
      String user = player1.getText();
      String opponent;
      if (player2.isDisabled()) {
        opponent = "AI";
      } else {
        opponent = player2.getText();
      }

      if (user.equals("") || opponent.equals("")) {
        new GameAlert(AlertType.ERROR, "Please input a name for both players");
      } else {
        try {
          modelController.setPlayers(user, opponent);
        } catch (Exception exception){
          new GameAlert(AlertType.ERROR, exception.getMessage());
        }
        getWindow().hide();
      }
    });


    sceneRoot.add(buttons, 0, 4);
  }

  public void giveBoardController(BoardController boardController) {
    this.boardController = boardController;
  }

  /**
   * Shows a {@link FileChooser} that prompts the user to enter an XML board configuration
   * file.
   */
  private void openBoard(){
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new ExtensionFilter("XML files", "*.xml"));
    File boardFile = fileChooser.showOpenDialog(null);
    try{
      if (boardFile != null) {
        modelController.setBoardState(boardFile);
      } else {
        modelController.setBoardState("chess");
      }
    } catch (Exception e){
      new GameAlert(AlertType.ERROR, e.getMessage());
    }

  }

//  private void pieceType() {
//    DirectoryChooser directoryChooser = new DirectoryChooser();
//    directoryChooser.setTitle("Choose a directory in data for piece images");
//    File boardFile = directoryChooser.showDialog(null);
//    if (boardFile != null) {
//      String[] directories = boardFile.toString().split("/");
//      boardController.setPieceFolder(directories[directories.length-1]);
//    }
//  }

  private void cancelButton(ActionEvent e) {
    ((Stage) getWindow()).close();
    handler.handle(e);
  }
}
