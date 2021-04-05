package ooga.view;

import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class GameScene extends Scene {
  private BorderPane sceneRoot;

  public GameScene (Parent sceneBorder, int width, int height){
    super(sceneBorder, width, height);
    sceneRoot = (BorderPane) this.getRoot();
    sceneRoot.setMinWidth(width);
    sceneRoot.setMinHeight(height);
  }


}
