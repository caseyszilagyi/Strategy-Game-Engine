package ooga.view.window;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ooga.view.GameScene;

/**
 * A subclass of {@link GameWindow} that extends {@link Stage}. This is the most common
 * type of window and can be shown without a parent.
 *
 * @author Yi Chen
 */
public class StageWindow extends Stage implements GameWindow {


  /**
   * Attaches the given {@link GameScene} object to the window and shows it.
   * @param scene a {@code GameScene} object
   */
  @Override
  public void showScene(GameScene scene) {
    setScene(scene);
    this.sizeToScene();
    this.setResizable(false);
    this.show();
  }


  @Override
  public void close(){
    super.close();
  }
}
