package ooga.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;
import ooga.view.window.GameWindow;

/**
 * A {@code GameScene} object extends {@link Scene} and is given to a {@link GameWindow}
 * to show.
 *
 * @author Yi Chen
 */
public abstract class GameScene extends Scene {

  public static final String DEFAULT_RESOURCES_PACKAGE = "view.resources.";
  public static final String DEFAULT_RESOURCES_PATH = "ooga/view/resources/";
  private final ResourceBundle resources;
  private final GridPane sceneRoot;
  private final EventHandler<ActionEvent> handler;

  /**
   * Constructor for {@code GameScene} takes a {@link Parent} object and a {@link ResourceBundle}.
   * The scene root is usually a {@link GridPane} for ease of layout.
   * @param root the {@code Parent} object to act as the root of the scene
   * @param handler
   * @param resources a {@code ResourceBundle} holding scene data files
   */
  public GameScene(Parent root, EventHandler<ActionEvent> handler,
      ResourceBundle resources){
    super(root);
    this.resources = resources;
    this.handler = handler;
    int sceneWidth = Integer.parseInt(resources.getString("width"));
    int sceneHeight = Integer.parseInt(resources.getString("height"));
    sceneRoot = (GridPane) root;
    setSceneSize(sceneWidth, sceneHeight);
    sceneRoot.setAlignment(Pos.TOP_CENTER);

  }

  /**
   * Returns the {@code String} scene type of this {@code GameScene}.
   * @return a {@code String} representing the scene type
   */
  public String getSceneType(){
    return resources.getString("sceneType");
  }

  /**
   * Sets the size of a scene. This will force any window displaying the scene to conform
   * to the size.
   * @param width desired width of scene
   * @param height desired height of scene
   */
  public void setSceneSize(int width, int height){
    GridPane sceneRoot = (GridPane) this.getRoot();
    sceneRoot.setMinWidth(width);
    sceneRoot.setMinHeight(height);
  }

  /**
   * Creates a text displaying {@link Label} object. This object will search properties
   * files for keys matching the input, and set the lable to the corresponding string.
   * @param property a {@code String} key in the resource file for this scene
   * @return a {@code Label} object
   */
  public Label makeLabel(String property) {
    Label label = new Label();
    label.setId(property);
    label.setText(resources.getString(label.getId()));

    return label;
  }

  /**
   * Creates a {@link Button} in the scene, with text determined by {@code property}, and
   * {@link EventHandler} specified by the event handler of this scene.
   * @param property a {@code String} matching a key in the scene source file
   * @return a {@code Button} object
   */
  public Button makeButton (String property) {
    // represent all supported image suffixes
    final String IMAGE_FILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button result = new Button();
    String label = resources.getString(property);
    result.setId(property);
    if (label.matches(IMAGE_FILE_SUFFIXES)) {
      result.setGraphic(new ImageView(new Image(DEFAULT_RESOURCES_PATH + label)));
    }
    else {
      result.setText(label);
    }
    result.setOnAction(handler);
    return result;
  }

  /**
   * Makes a {@link java.util.List} of {@link Button} objects using the button list
   * property name specified. {@code buttonProperties} correspond to a list of
   * individual button keys in this scene's {@link ResourceBundle}. Eventually, the
   * property key of a button is set as the ID of the button, and its corresponding
   * {@code String} is set as the button's label.
   *
   * This also adds a {@link EventHandler<ActionEvent>} to
   * the buttons created.
   *
   * @param buttonProperties a {@code String} corresponding to a list of strings in the resource
   *                         bundle of this scene.
   * @return a list of {@code Button}s.
   */
  public Button[] makeButtons(String buttonProperties){
    String[] buttonNames = resources.getString(buttonProperties).split(",");
    Button[] buttons = new Button[buttonNames.length];
    for (int i = 0; i < buttonNames.length; i++) {
      buttons[i] = (makeButton(buttonNames[i]));
    }
    return buttons;
  }

  /**
   * Creates a {@link Node} of type {@link HBox} with one or more {@link Button} objects that are usually
   * centered and evenly distributed. This element is usually placed at the top of a scene.
   * @return a {@code Node} instance
   */
  public Node makeTopBar(){
    HBox topBar = new HBox();
    topBar.setAlignment(Pos.CENTER);
    topBar.getStyleClass().add("hbox");
    Button[] buttons = makeButtons("topBarButtons");
    topBar.getChildren().addAll(buttons);
    return topBar;
  }

  /**
   * Populates the scene with the correct layout panels and view elements depending
   * on the subclass of {@code GameScene}.
   */
  public abstract void populateScene();

}


