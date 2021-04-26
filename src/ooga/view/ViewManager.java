package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.BoardController;
import ooga.controller.ModelController;
import ooga.view.board.BoardScene;
import ooga.view.window.FloatingWindow;
import ooga.view.window.GameWindow;
import ooga.view.window.GameWindowFactory;
import ooga.view.window.StageWindow;

/**
 * Instantiates and manages all views for a single game instance.
 * This class controls the flow of {@link GameWindow} and {@link GameScene}
 * objects based on user button clicks, switching scenes and showing/closing windows
 * as necessary.
 *
 * @author Yi Chen
 */
public class ViewManager {
  private final ResourceBundle initFile;
  private final GameWindowFactory gameWindowFactory;
  private final GameSceneFactory sceneFactory;
  private final StageWindow primaryWindow;
  private ModelController modelController;
  private BoardController boardController;

  private String DEFAULT_GAMETYPE = "chess";

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
    primaryWindow = (StageWindow) getInitialWindow();
    ((Stage) primaryWindow).setOnCloseRequest(e -> handleStageClose());
    createControllers();
    changeScene("initialWindowScene");
    primaryWindow.setCurrentSceneTitle("title-text");
  }

  /**
   * All methods in here are run when the user closes the window. Do any saving operations
   * here.
   */
  private void handleStageClose() {
    String sceneType = ((GameScene) primaryWindow.getScene()).getSceneType();
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
    } catch (NoSuchMethodException | IllegalAccessException |
        InvocationTargetException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Starts the game by changing the scene of the current window to a {@link BoardScene}
   * instance. This also gives the {@link ModelController} its game type so that it can
   * initialize the correct game files.
   * @param gameType a {@code String} for the game type.
   */

  public void startGame(String gameType) {
    ((BoardScene) changeScene("boardScene"))
        .attachBoardControllerToBoard(boardController);
    modelController.setGameType(gameType);
    primaryWindow.setResizable(true);
    primaryWindow.setCurrentSceneTitle(gameType);
    positionWindow(primaryWindow, 500, 200);
  }

  private void chess(){
    startGame("chess");
  }

  private void checkers(){
    startGame("checkers");
  }

  private void connectfour(){
    startGame("connectfour");
  }

  public void undoButton() {
    modelController.undoTurn();
  }


  public void resetButton() {
    boardController.resetColors();
  }

  /**
   * Creates a new empty {@link FloatingWindow} instance and preset its owner window
   * to the primary window of this game instance. This also sets the scene for the window
   * to display.
   * @return a {@code FloatingWindow} with no scene specified.
   */
  private FloatingWindow showFloatingWindow(GameScene scene) {
    FloatingWindow popup = (FloatingWindow) gameWindowFactory
        .makeWindow(initFile.getString("popupWindowType"));
    popup.setOwnerWindow(primaryWindow);
    popup.showScene(scene);
    return popup;
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
