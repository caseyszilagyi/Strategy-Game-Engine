package ooga.view;

import java.util.ResourceBundle;
import javafx.scene.Node;

public class MenuSceneFactory extends AbstractGameSceneFactory {
  private final ResourceBundle myResources;

  public MenuSceneFactory(ResourceBundle resources) {
    myResources = resources;
  }

  @Override
  public Node createTopBar() {
    return null;
  }

  @Override
  public Node createMainView() {
    return null;
  }

  @Override
  public Node createSplitPanes() {
    return null;
  }

  @Override
  public void setBorderSize(int width, int height) {

  }
}
