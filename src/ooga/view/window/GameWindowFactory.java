package ooga.view.window;

import java.lang.reflect.InvocationTargetException;

/**
 * This class creates objects that extend {@link GameWindow}.
 *
 * @author Yi Chen
 */
public class GameWindowFactory {

  /**
   * Creates a {@link GameWindow} based on an input {@code String} describing a window
   * type. The string given must match the name of an existing {@code GameWindow} subclass.
   * @param windowType {@code String} name of the {@code GameWindow} subclass
   * @return a {@code GameWindow} object
   */
  public static GameWindow makeWindow (String windowType){
    GameWindow newWindow;
    try {
      Class c = Class.forName("ooga.view.window." + windowType + "Window");
      newWindow = (GameWindow) c.getConstructor().newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException |
        InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
    return newWindow;
  }

}
