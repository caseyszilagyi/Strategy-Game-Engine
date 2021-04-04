package ooga.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public abstract class GameScene extends Scene {


  public GameScene (Pane sceneBorder, int width, int height){
    super(sceneBorder, width, height);
  }
}
