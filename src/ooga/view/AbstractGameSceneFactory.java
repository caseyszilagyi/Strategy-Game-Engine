package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public abstract class AbstractGameSceneFactory {
  private final int DEFAULT_WIDTH = 500;
  private final int DEFAULT_HEIGHT = 500;

  private int SCENE_WIDTH = DEFAULT_WIDTH;
  private int SCENE_HEIGHT = DEFAULT_HEIGHT;
  private final String DEFAULT_RESOURCES_FOLDER = "view.resources.";

  public abstract Node createTopBar();

  public abstract Node createMainView();

  public abstract Node createSplitPanes();

  public abstract void setBorderSize(int width, int height);

  public GameScene assembleScene(String filename)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    ResourceBundle resources = ResourceBundle.getBundle(DEFAULT_RESOURCES_FOLDER + filename);

    String sceneType = resources.getString("sceneType");
    Class sceneFactory = Class.forName(sceneType + "SceneFactory");
    GameScene newScene = (GameScene) sceneFactory.getConstructor().newInstance(resources);

    Node topBar = createTopBar();
    Node mainView = createMainView();
    Node splitPane = createSplitPanes();

    BorderPane sceneRoot = (BorderPane) newScene.getRoot();
    sceneRoot.setTop(topBar);
    sceneRoot.setCenter(mainView);
    sceneRoot.setRight(splitPane);
    return newScene;
  }

}
