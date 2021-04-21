package ooga.view;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import ooga.view.util.DukeApplicationTest;
import ooga.view.window.GameWindowFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewTest extends DukeApplicationTest {

  private ViewManager viewManager;
  private final String DEFAULT_RESOURCES_PACKAGE = "ooga.view.resources.";
  private GameWindowFactory windowFactory;
  private GameSceneFactory sceneFactory;
  private ResourceBundle resources;
  private Button startButton;
  private Label welcomeTitle;

  @Override
  public void start(Stage stage){
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + "init");
    viewManager = new ViewManager(resources);
    startButton = lookup("#startGame").query();
    welcomeTitle = lookup("#title-text").query();
  }

  @Test
  void testGameStarted(){
    assertNotNull(viewManager);
  }

  @Test
  void testStartGameButton(){
    assertEquals("Start", startButton.getText());
  }

  @Test
  void testTitle(){
    assertEquals("Welcome to BrainMate!", welcomeTitle.getText());
  }

  @Test
  void testStartChessGame(){
    clickOn(startButton);
    Label gameTitle = lookup("#title-text").query();
    assertEquals("Chess", gameTitle.getText());
  }

  /**
   * On closing the game window, the title screen should show again. The key combination
   * in this test only works for MacOS.
   */
  @Test
  void testCloseGameWindow(){
    clickOn(startButton);
    push(KeyCode.COMMAND, KeyCode.Q);
    Label title = lookup("#title-text").query();
    assertEquals("Welcome to BrainMate!", title.getText());
  }

}
