package ooga.view;

import ooga.view.window.GameWindowFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GameWindowFactoryTest {

  private GameWindowFactory windowFactory = new GameWindowFactory();
  private GameSceneFactory sceneFactory = new GameSceneFactory();

  @Test
  void testReflection(){
    //assertNotNull(windowFactory.makeWindow("Stage"));
  }
}
