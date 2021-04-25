package ooga.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ooga.controller.ModelController;

/**
 * A subclass of {@link GameScene}, this scene is the first one viewers see when starting
 * the game. This scene allows users to select which game to play, as well as certain
 * game configurations.
 *
 * @author Yi Chen
 */
public class WelcomeScene extends GameScene {
  private ResourceBundle resources;
  private final GridPane sceneRoot;
  private final EventHandler<ActionEvent> handler;
  private final ModelController modelController;

  /**
   * Constructs the superclass and sets the {@link GridPane} layout specifically for
   * this scene. Also sets the {@link javafx.css.StyleClass} for this scene and
   * loads the CSS stylesheet from the filename given in the properties file.
   * @param root the {@code Parent} object to act as the root of the scene
   * @param resources a {@code ResourceBundle} holding scene data files
   * @param handler
   * @param modelController
   */
  public WelcomeScene(Parent root, ResourceBundle resources,
      EventHandler<ActionEvent> handler, ModelController modelController) {
    super(root, handler, resources);
    this.resources = resources;
    this.handler = handler;
    this.modelController = modelController;
    sceneRoot = (GridPane) root;
    sceneRoot.setHgap(10);
    sceneRoot.setVgap(300);
    sceneRoot.getStyleClass().add("title");
    this.getStylesheets().add(DEFAULT_RESOURCES_PATH + resources.getString("CSS"));
    populateScene();
  }


  @Override
  public void populateScene() {
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setHalignment(HPos.CENTER);
    sceneRoot.getColumnConstraints().add(col1);

    Label welcomeLabel = makeLabel("title-text");
    sceneRoot.add(welcomeLabel, 0, 0);

    Node buttons = makeButtonBar("buttons");

    sceneRoot.add(buttons, 0, 1);
  }


}
