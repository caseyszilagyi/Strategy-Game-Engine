package ooga.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * The top level UI element of the game. The game can have one or more instances of
 * {@code GameWindow}, and each will hold a completely independent instance of the game.
 *
 * Within a {@code GameWindow}, multiple {@link GameScene} instances can be created,
 * and any of these can be set as the active view in the window. The windows can be
 * of different subclasses of {@code Window}, each with different behaviors.
 *
 *
 * @author Yi Chen
 */
public interface GameWindow {


  public abstract void makeVisible();

  public abstract void showScene(GameScene scene);

}
