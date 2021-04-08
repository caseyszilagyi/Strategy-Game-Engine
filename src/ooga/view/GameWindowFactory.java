package ooga.view;

import java.lang.reflect.InvocationTargetException;

public class GameWindowFactory {
  public GameWindow makeWindow (String windowType){
    GameWindow newWindow;
    try {
      Class c = Class.forName("ooga.view." + windowType + "Window");
      newWindow = (GameWindow) c.getConstructor().newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException |
        InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
    return newWindow;
  }

}
