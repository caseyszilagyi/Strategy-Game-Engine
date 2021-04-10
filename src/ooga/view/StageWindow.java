package ooga.view;

import javafx.stage.Stage;

public class StageWindow extends Stage implements GameWindow {

  @Override
  public void makeVisible() {
    this.setResizable(false);
    this.show();
  }

  @Override
  public void showScene(GameScene scene) {
    setScene(scene);
  }
}
