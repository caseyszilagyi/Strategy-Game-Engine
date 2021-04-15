package ooga.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.model.engine.action_files.Action;

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

  /**
   * Creates a new instance of {@code ViewManager} with a resource bundle with
   * information about the first window to show. The constructor then shows the
   * first window.
   * @param initFile a {@link ResourceBundle} with specifications for the first window.
   */
  public ViewManager(ResourceBundle initFile){
    this.initFile = initFile;
    gameWindowFactory = new GameWindowFactory();
    sceneFactory = new GameSceneFactory();
    primaryWindow = gameWindowFactory.makeWindow("Stage");
    changeScene("initialWindowScene");
  }

  public void changeScene(String sceneNameProperty){
    String initSceneName = initFile.getString(sceneNameProperty);
    GameScene newScene = sceneFactory.makeScene(initSceneName, this::onButtonClicked);
    primaryWindow.showScene(newScene);
  }

  private void onButtonClicked(ActionEvent e){

  }

  public void onStartClicked(){
    changeScene("boardScene");
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
