package ooga.view.board;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ooga.controller.BoardController;
import ooga.controller.ModelController;
import ooga.view.GameScene;

/**
 * This scene holds the view representation of the main game board. The scene has a top
 * control bar with buttons and extends {@link GameScene}. The scene also holds a reference
 * to {@link ModelController} and {@link BoardController}.
 */
public class BoardScene extends GameScene {

  private final ResourceBundle resources;
  private final GridPane sceneRoot;
  private final ModelController modelController;
  private Board board;

  /**
   * Creates a scene that contains a top control bar and a {@link Board} object. The scene
   * constructs with a {@link ResourceBundle} so that it can be modified and styled.
   * @param root root of the scene, usually a {@link GridPane}
   * @param resources {@code ResourceBundle} for this scene
   * @param modelController the {@link ModelController} for this game
   */
  public BoardScene(Parent root, ResourceBundle resources,
      EventHandler<ActionEvent> handler, ModelController modelController) {
    super(root, handler, resources);
    this.modelController = modelController;
    this.resources = resources;
    board = new Board(8, 8, modelController);
    sceneRoot = (GridPane) root;
    sceneRoot.getStyleClass().add("boardScene");
    sceneRoot.setVgap(30);
    this.getStylesheets().add(DEFAULT_RESOURCES_PATH + resources.getString("CSS"));
    populateScene();
  }

  /**
   * Attaches a {@link BoardController} object to the {@code Board} object this scene
   * holds.
   * @param boardController a {@code BoardController} instance
   */
  public void attachBoardControllerToBoard(BoardController boardController) {
    boardController.giveBoard(board);
  }


  @Override
  public void populateScene() {
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setHalignment(HPos.CENTER);
    sceneRoot.getColumnConstraints().add(col1);

    Label welcomeLabel = makeLabel("title-text");
    sceneRoot.add(welcomeLabel, 0, 0);

    Node topBar = makeTopBar();

    sceneRoot.add(topBar, 0, 1);
    sceneRoot.add(board, 0, 3);
  }
}
