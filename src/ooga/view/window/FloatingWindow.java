package ooga.view.window;

import javafx.scene.Node;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;
import ooga.view.GameScene;

/**
 * A window that floats above a parent window. This window can be dismissed by
 * pressing the escape key. Note: a {@code FloatingWindow} must have an owner window set by
 * {@code setOwnerWindow()} before calling {@code showScene}, otherwise it will
 * throw an error.
 */
public class FloatingWindow extends Popup implements GameWindow{
  private Window ownerWindow;

  @Override
  public void showScene(GameScene scene) {
    Node sceneObjects = scene.getRoot();
    getContent().add(sceneObjects);
    show(ownerWindow);
  }

  /**
   * Sets the owner window of this object floating window.
   * @param ownerWindow a {@link Window} reference to use as the owner
   */
  public void setOwnerWindow(Window ownerWindow){
    this.ownerWindow = ownerWindow;
  }

  @Override
  public void close() {
    hide();
  }
}
