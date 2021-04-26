package ooga.view.window;

import javafx.scene.Node;
import javafx.stage.Modality;
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
public class FloatingWindow extends Stage implements GameWindow{

  @Override
  public void showScene(GameScene scene) {
    initModality(Modality.APPLICATION_MODAL);
    setScene(scene);
    showAndWait();
  }


  @Override
  public void close() {
    hide();
  }
}
