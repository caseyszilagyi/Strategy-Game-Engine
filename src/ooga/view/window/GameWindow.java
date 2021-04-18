package ooga.view.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.stage.Window;
import ooga.view.GameScene;

/**
 * The top level UI element of a view. The game can have one or more instances of
 * {@code GameWindow} of different subtypes.
 *
 * The active view of a {@code GameWindow} can be set to any {@link GameScene} instance.
 *
 * @author Yi Chen
 */
public interface GameWindow {

  /**
   * Attaches the given {@link GameScene} object to the window and shows it.
   * @param scene a {@code GameScene} object
   */
  public void showScene(GameScene scene);



}
