package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class WelcomeScreenSceneFactory extends GameSceneFactory {


  public WelcomeScreenSceneFactory(ResourceBundle resources)
      throws ClassNotFoundException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, InvocationTargetException {

    super(resources);
  }


}
