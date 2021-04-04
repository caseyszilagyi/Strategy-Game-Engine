package ooga.view;

import javafx.scene.Node;

public class WelcomeScreenSceneFactory extends AbstractGameSceneFactory {

  public WelcomeScreenSceneFactory(String fileName) {
    super(fileName);
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
