package ooga.view;

import java.lang.reflect.Field;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ooga.view.util.DukeApplicationTest;
import ooga.view.window.GameWindow;
import ooga.view.window.GameWindowFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ViewTest extends DukeApplicationTest {

  private ViewManager viewManager;
  private final String DEFAULT_RESOURCES_PACKAGE = "ooga.view.resources.";
  private GameWindowFactory windowFactory;
  private GameSceneFactory sceneFactory;
  private ResourceBundle resources;
  private Button chessButton;
  private Button checkersButton;
  private Button connectfourButton;
  private Label welcomeTitle;
  private GameWindow primaryWindow;

  /**
   * Starts the program and finds all the view elements needed.
   * This also uses reflection to access private methods and fields.
   * @param stage
   */
  @Override
  public void start(Stage stage){
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + "init");
    viewManager = new ViewManager(resources);
    chessButton = lookup("#chess").query();
    checkersButton = lookup("#checkers").query();
    connectfourButton = lookup("#connectfour").query();
    welcomeTitle = lookup("#title-text").query();
    primaryWindow = (GameWindow) getFieldFromObject(viewManager, "primaryWindow");

  }

  /**
   * Helper method to get any field from any object, public or private. Any object
   * returned by this method need to be casted to the desired type. Will print
   * to stacktrace if the field is not found.
   * @param object a {@link Object} reference for the object from which a {@link Field}
   *               is desired.
   * @param fieldName {@code String} name of the field for use by reflection
   * @return a {@code Object} reference
   */
  private Object getFieldFromObject(Object object, String fieldName){
    try {
      Field f = object.getClass()
          .getDeclaredField(fieldName);
      f.setAccessible(true);
      return f.get(object);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Test
  void testGameStarted(){
    assertNotNull(viewManager);
  }

  @Test
  void testStartGameButtons(){
    assertEquals("Chess", chessButton.getText());
    assertEquals("Checkers", checkersButton.getText());
    assertEquals("Connect Four", connectfourButton.getText());
  }

  @Test
  void testTitle(){
    assertEquals("Welcome to BrainMate!", welcomeTitle.getText());
  }


  @Test
  void testChessStart(){
    clickOn(chessButton);
    Label gameTitle = lookup("#chess").query();
    assertEquals("Chess", gameTitle.getText());
  }

  @Test
  void testCheckersStart(){
    clickOn(checkersButton);
    Label gameTitle = lookup("#checkers").query();
    assertEquals("Checkers", gameTitle.getText());
  }

  @Test
  void testConnectfourStart(){
    clickOn(connectfourButton);
    Label gameTitle = lookup("#connectfour").query();
    assertEquals("Connect Four", gameTitle.getText());
  }

  @Test
  void testGameWindow(){
    assertNotNull(primaryWindow);
  }


  @Test
  void testHighlightColorPicker(){
    clickOn(chessButton);
    ColorPicker highlightColorPicker = highlightColorPicker = lookup("#highlightColorPicker").query();
    assertNotNull(highlightColorPicker);
  }

  /**
   * On closing the game window, the title screen should show again. The key combination
   * in this test only works for MacOS.
   */

  void testCloseGameWindow(){
    clickOn(chessButton);
    primaryWindow.close();
    Label title = lookup("#title-text").query();
    assertEquals("Welcome to BrainMate!", title.getText());
  }

}
