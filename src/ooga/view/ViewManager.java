package ooga.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.BoardController;
import ooga.controller.FrontEndExternalAPI;
import ooga.controller.ModelController;

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

  /**
   * Creates a new instance of {@code ViewManager} with a resource bundle with
   * information about the first window to show. The constructor then shows the
   * first window.
   * @param initFile a {@link ResourceBundle} with specifications for the first window.
   *
   */
  public ViewManager(ResourceBundle initFile){
    this.initFile = initFile;
    gameWindowFactory = new GameWindowFactory();
    sceneFactory = new GameSceneFactory();
    primaryWindow = gameWindowFactory.makeWindow("Stage");
    createControllers();
    changeScene("initialWindowScene");
  }

  /**
   * Creates the necessary {@link BoardController} and {@link ModelController} objects.
   */
  private void createControllers(){
    boardController = new BoardController();
    modelController = new ModelController();
    boardController.setModelController(modelController);
    modelController.setBoardController(boardController);
  }

  public GameScene changeScene(String sceneNameProperty){
    String initSceneName = initFile.getString(sceneNameProperty);
    GameScene newScene = sceneFactory.makeScene(initSceneName, this::onButtonClicked,
        modelController);
    primaryWindow.showScene(newScene);
    return newScene;
  }

  private void onButtonClicked(ActionEvent e){
    modelController.setGameType("chess");
    onStartClicked();
  }

  public void onStartClicked(){
    ((BoardScene) changeScene("boardScene")).attachBoardController(boardController);
  }


  /**
   * Gets the correct subclass of {@link GameWindow} to show the initial window of the
   * game. This searches for the "initialWindowType" attribute in the init file and
   * instantiates the window type requested. Will print to stacktrace if instantiation
   * fails.
   * @return a {@code GameWindow} instance.
   */
  private GameWindow getInitialWindow(){
    String windowType = initFile.getString("initialWindowType");
    return gameWindowFactory.makeWindow(windowType);
  }

}
