package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class WelcomeScreenSceneFactory extends AbstractGameSceneFactory {


  public WelcomeScreenSceneFactory(ResourceBundle resources)
      throws ClassNotFoundException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, InvocationTargetException {

    super(resources);
  }


  @Override
  public void assembleScene() {

    addMainView();
  }

  public GameScene getScene() {

    return super.getScene();
  }
}
