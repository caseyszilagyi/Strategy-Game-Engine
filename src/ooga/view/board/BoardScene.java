package ooga.view.board;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ooga.controller.BoardController;
import ooga.controller.ModelController;
import ooga.view.GameScene;

/**
 * This scene holds the view representation of the main game board. The scene has a top
 * control bar with buttons and extends {@link GameScene}. The scene also holds a reference
 * to {@link ModelController} and {@link BoardController}.
 *
 * @author Kenneth Moore III
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
    board = new Board(modelController);
    sceneRoot = (GridPane) root;
    sceneRoot.getStyleClass().add("boardScene");
    sceneRoot.setVgap(30);
    sceneRoot.setHgap(10);
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

  /**
   * Purpose: puts all of the buttons and nodes on the screen.
   */
  @Override
  public void populateScene() {
    ColumnConstraints col1 = makeColumnConstraint(HPos.CENTER);
    sceneRoot.getColumnConstraints().add(col1);

    // Note: the title of this scene is set by ViewManger.

    Node topBar = makeButtonBar("topBarButtons");
    sceneRoot.add(topBar, 0, 1);
    sceneRoot.add(board, 0, 3);

    VBox controlPane = new VBox();
    controlPane.getStyleClass().add("vbox");
    List<Node> Nodes = makeColorPickersAndLabels();
    Nodes.add(makeButton("changeBackgroundButton"));
    Nodes.add(makeButton("resetButton"));
    for (int i =0; i < Nodes.size(); i++) {
      controlPane.getChildren().add(i, Nodes.get(i));
    }
    sceneRoot.add(controlPane, 2 ,3);
  }

  private List<Node> makeColorPickersAndLabels() {
    List<Node> returned = new ArrayList<>();
    Label highlightLabel = makeLabel("highlightColor");
    returned.add(highlightLabel);
    ColorPicker highlightColorPicker = makeColorPicker("highlightColorPicker");
    highlightColorPicker.setOnAction(t -> board.setHighlightColor((highlightColorPicker.getValue())));
    returned.add(highlightColorPicker);
    Label lightLabel = makeLabel("lightSquareColor");
    returned.add(lightLabel);
    ColorPicker lightColorPicker = makeColorPicker("lightColorPicker");
    lightColorPicker.setOnAction(t -> board.colorLightSquares((lightColorPicker.getValue())));
    returned.add(lightColorPicker);
    Label darkLabel = makeLabel("darkSquareColor");
    returned.add(darkLabel);
    ColorPicker darkColorPicker = makeColorPicker("darkColorPicker");
    darkColorPicker.setOnAction(t -> board.colorDarkSquares((darkColorPicker.getValue())));
    returned.add(darkColorPicker);
    return returned;
  }

  private ColorPicker makeColorPicker(String id) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.getStyleClass().removeAll();
    colorPicker.getStyleClass().add("button");
    colorPicker.setId(id);
    return colorPicker;
  }
}
