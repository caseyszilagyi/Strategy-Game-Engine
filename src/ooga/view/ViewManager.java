package ooga.view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.FileChooser;
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
 * @author kenneth Moore III
 */
public class ViewManager {
  private final ResourceBundle initFile;
  private final GameWindowFactory gameWindowFactory;
  private final GameSceneFactory sceneFactory;
  private final StageWindow primaryWindow;
  private ModelController modelController;
  private BoardController boardController;
  private final String HOME_SCENE = "initialWindowScene";

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
    primaryWindow.showScene(makeScene("initialWindowScene"));
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
  private GameScene makeScene(String sceneNameProperty) {
    String sceneName = initFile.getString(sceneNameProperty);
    GameScene newScene = sceneFactory.makeScene(sceneName, this::onButtonClicked,
        modelController);
    return newScene;
  }

  /**
   * Handles button clicks. This method assumes that the source of the incoming
   * {@link ActionEvent} is of type {@link Button}. Then it uses reflection to call
   * methods in this class based on the ID of the button pressed.
   * @param e
   */
  private void onButtonClicked(ActionEvent e) {
    String buttonID = null;
    if (e.getSource().getClass() == Button.class) {
      Button buttonPressed = (Button) e.getSource();
      buttonID = buttonPressed.getId();
    } else if (e.getSource().getClass() == MenuItem.class) {
      MenuItem menuItem = (MenuItem) e.getSource();
      buttonID = menuItem.getId();
    }
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
    BoardScene newScene = (BoardScene) makeScene("boardScene");
    newScene.attachBoardControllerToBoard(boardController);
    modelController.setGameType(gameType);

    showConfigMenu();

    if (primaryWindow.isShowing()) {
      primaryWindow.showScene(newScene);
      primaryWindow.setResizable(true);
      primaryWindow.setCurrentSceneTitle(gameType);
      positionWindow(primaryWindow, 500, 200);
    }
  }

  /**
   * Shows a {@link FloatingWindow} with the configuration menu for the game.
   */
  private void showConfigMenu() {
    GameScene configScene = sceneFactory.makeScene("GameConfigScene",
        this::onButtonClicked, modelController);
    ((GameConfigScene)configScene).giveBoardController(boardController);
    GameWindow configWindow = makeFloatingWindow(configScene);
    configWindow.showScene(configScene);
  }

  private void chess() {
    startGame("chess");
  }

  private void checkers() {
    startGame("checkers");
  }

  private void connectfour() {
    startGame("connectfour");
  }

  private void exitButton() {
    //code to save
    primaryWindow.close();
    new ViewManager(initFile);
  }

  private void undoButton() {
    try {
      modelController.undoTurn();
    } catch (Exception e) {
      new GameAlert(AlertType.ERROR, "No moves to undo!");
    }
  }

  private void resetButton() {
    boardController.resetColors();
    changeBackground("ooga/view/resources/boardSceneBackground.jpg");
  }

  private void helpButton() {
    new GameAlert(AlertType.INFORMATION, "https://en.wikipedia.org/wiki/Chess"
        + "\nhttps://en.wikipedia.org/wiki/Draughts\nhttps://en.wikipedia.org/wiki/Connect_Four");

  }

  private void newWindow(){
    new ViewManager(initFile);
  }

  private void settingsButton(){
    new GameAlert(AlertType.INFORMATION, "This button hasn't been implemented yet, "
        + "thank you for your patience!");
  }

  private void changeBackgroundButton() {
    FileChooser fc = new FileChooser();
    File selectedFile = fc.showOpenDialog(primaryWindow);
    if(selectedFile == null) {
      return;
    }
    try {
      changeBackground(selectedFile.toURI().toURL().toString());
    } catch (MalformedURLException e) {
      String errorMessage = e.getMessage();
      Alert error = new Alert(AlertType.ERROR);
      error.setContentText(errorMessage);
      error.showAndWait();
    }
  }

  /**
   * Changes the background of the {@link BoardScene}.
   * @param url the url of the image desired
   */
  private void changeBackground(String url) {
    Scene scene = primaryWindow.getScene();
    BackgroundImage backgroundImage=  new BackgroundImage(new Image(url),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    ((GameScene) scene).setBackground(new Background(backgroundImage));
  }

  /**
   * Dismisses the {@link GameConfigScene} and restarts the {@link ViewManager}.
   */
  private void cancelButton(){
    primaryWindow.close();
    new ViewManager(initFile);
  }

  /**
   * Closes the primary {@link GameWindow} and exits the game.
   */
  private void quit(){
    primaryWindow.close();
  }

  /**
   * Creates a new empty {@link FloatingWindow} instance and preset its owner window
   * to the primary window of this game instance. This also sets the scene for the window
   * to display.
   * @return a {@code FloatingWindow} with no scene specified
   */
  private FloatingWindow makeFloatingWindow(GameScene scene) {
    FloatingWindow popup = (FloatingWindow) gameWindowFactory
        .makeWindow(initFile.getString("popupWindowType"));
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
