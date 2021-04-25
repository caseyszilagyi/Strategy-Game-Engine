package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.BoardController;
import ooga.controller.ModelController;
import ooga.view.board.BoardScene;
import ooga.view.window.GameWindow;
import ooga.view.window.GameWindowFactory;

/**
 * Instantiates and manages all views for a single game instance.
 * This class controls the flow of {@link GameWindow} and {@link GameScene}
 * objects based on user button clicks, switching scenes and showing/closing windows
 * as necessary.
 */
public class ViewManager {
  private final ResourceBundle initFile;
  private final GameWindowFactory gameWindowFactory;
  private final GameSceneFactory sceneFactory;
  private final GameWindow primaryWindow;
  private ModelController modelController;
  private BoardController boardController;
  private static final String CHESS_GAMETYPE = "chess";
  private static final String CHECKERS_GAMETYPE = "checkers";
  private static final String CONNECT4_GAMETYPE = "connectfour";

  /**
   * Creates a new instance of {@code ViewManager} with a resource bundle with
   * information about the first window to show. The constructor then shows the
   * first window. This constructor assumes tat the first scene created is a window
   * of type {@link Stage}.
   * @param initFile a {@link ResourceBundle} with specifications for the first window.
   *
   */
  public ViewManager(ResourceBundle initFile) {
    this.initFile = initFile;
    gameWindowFactory = new GameWindowFactory();
    sceneFactory = new GameSceneFactory();
    primaryWindow = getInitialWindow();
    ((Stage) primaryWindow).setOnCloseRequest(e -> handleStageClose());
    createControllers();
    changeScene("initialWindowScene");
  }

  /**
   * All methods in here are run when the user closes the window. Do any saving operations
   * here.
   */
  private void handleStageClose() {
    String sceneType = ((GameScene)((Stage) primaryWindow).getScene()).getSceneType();
    if (sceneType.equals("BoardScene")) {
      new ViewManager(initFile);
    }
  }

  /**
   * Creates the necessary {@link BoardController} and {@link ModelController} objects.
   */
  private void createControllers() {
    boardController = new BoardController();
    modelController = new ModelController();
    boardController.setModelController(modelController);
    modelController.setBoardController(boardController);
  }


  private void positionWindow(Stage window, int x, int y) {
    window.setX(x);
    window.setY(y);
  }

  /**
   * Changes the scene of the primary {@link GameWindow} to the desired scene. The
   * scene name is specified by an input {@code String} that corresponds to a property
   * in the {@code init.properties} file that points to the name of an existing
   * {@code GameScene} class.
   *
   * @param sceneNameProperty the property key corresponding to the name of an existing
   *                          {@code GameScene} class.
   * @return a {@link GameScene} subclass of the desired type.
   */
  private GameScene changeScene(String sceneNameProperty) {
    String initSceneName = initFile.getString(sceneNameProperty);
    GameScene newScene = sceneFactory.makeScene(initSceneName, this::onButtonClicked,
        modelController);
    primaryWindow.showScene(newScene);
    return newScene;
  }

  /**
   * Handles button clicks. This method assumes that the source of the incoming
   * {@link ActionEvent} is of type {@link Button}. Then it uses reflection to call
   * methods in this class based on the ID of the button pressed.
   * @param e
   */
  private void onButtonClicked(ActionEvent e) {
    Button buttonPressed = (Button) e.getSource();
    String buttonID = buttonPressed.getId();
    try {
      this.getClass().getDeclaredMethod(buttonID).invoke(this);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Starts the game by changing the scene of the current window to a {@link BoardScene}
   * instance.
   */
  public void chessButton() {
    ((BoardScene) changeScene("boardScene"))
        .attachBoardControllerToBoard(boardController);
    modelController.setGameType(CHESS_GAMETYPE);
    ((Stage) primaryWindow).setResizable(true);
    positionWindow((Stage) primaryWindow, 500, 200);
  }

  public void checkersButton() {
    ((BoardScene) changeScene("boardScene"))
        .attachBoardControllerToBoard(boardController);
    modelController.setGameType(CHECKERS_GAMETYPE);
    ((Stage) primaryWindow).setResizable(true);
    positionWindow((Stage) primaryWindow, 500, 200);
  }

  public void connect4Button() {
    ((BoardScene) changeScene("boardScene"))
        .attachBoardControllerToBoard(boardController);
    modelController.setGameType(CONNECT4_GAMETYPE);
    ((Stage) primaryWindow).setResizable(true);
    positionWindow((Stage) primaryWindow, 500, 200);
  }

  public void undoButton() {
    modelController.undoTurn();
  }


  /**
   * Gets the correct subclass of {@link GameWindow} to show the initial window of the
   * game. This searches for the "initialWindowType" attribute in the init file and
   * instantiates the window type requested. Will print to stacktrace if instantiation
   * fails.
   * @return a {@code GameWindow} instance.
   */
  private GameWindow getInitialWindow() {
    String windowType = initFile.getString("initialWindowType");
    return gameWindowFactory.makeWindow(windowType);
  }

}
