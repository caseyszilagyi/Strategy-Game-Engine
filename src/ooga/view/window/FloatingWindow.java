package ooga.view.window;

import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;
import ooga.view.GameScene;

/**
 * A window that floats above a parent window. This window will block the user from
 * clicking anywhere else until the window is dismissed. The window is also not resizable.
 *
 * @author Yi Chen
 */
public class FloatingWindow extends Stage implements GameWindow{

  @Override
  public void showScene(GameScene scene) {
    initModality(Modality.APPLICATION_MODAL);
    setScene(scene);
    setResizable(false);
    showAndWait();
  }


  @Override
  public void close() {
    hide();
  }
}
