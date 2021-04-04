package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.scene.layout.BorderPane;

public abstract class AbstractGameSceneFactory {
  private final int DEFAULT_WIDTH = 500;
  private final int DEFAULT_HEIGHT = 500;

  private int SCENE_WIDTH = DEFAULT_WIDTH;
  private int SCENE_HEIGHT = DEFAULT_HEIGHT;
  private final String DEFAULT_RESOURCES_FOLDER = "view.resources.";
  private final ResourceBundle resources;
  private GameScene myScene;
  private BorderPane myRoot;

  public AbstractGameSceneFactory(ResourceBundle resources) {
    this.resources = resources;
    myScene = createEmptyScene();
    myRoot = (BorderPane) myScene.getRoot();
  }

  private ViewPane createPane (String paneName) {

    try {
      if (resources.getString("has" + paneName).equals("false")) {
        return null;
      }

      Class paneFactoryClass = Class.forName(paneName + "ViewPanelFactory");

      AbstractViewPaneFactory factory =
          (AbstractViewPaneFactory) paneFactoryClass.getConstructor(ResourceBundle.class)
              .newInstance(resources);
      return factory.createPane();
    } catch (ClassNotFoundException | NoSuchMethodException |
        IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addTopBar () {

    myRoot.setTop(createPane("TopBar"));

  };

  public void addMainView () {

    myRoot.setCenter(createPane("MainView"));
  }

  public void addSplitPane () {

    myRoot.setRight(createPane("SplitPane"));
  }

  public void setBorderSize(int width, int height){
    SCENE_WIDTH = width;
    SCENE_HEIGHT = height;
  };

  public GameScene createEmptyScene() {
    try {
      String sceneType = resources.getString("sceneType");
      Class scene = Class.forName(sceneType + "Scene");
      setBorderSize(Integer.parseInt(resources.getString("sceneWidth")),
          Integer.parseInt(resources.getString("sceneHeight")));
      Class params[] = {BorderPane.class, int.class, int.class};
      GameScene newScene = (GameScene) scene.getConstructor(params)
          .newInstance(new BorderPane(), SCENE_WIDTH, SCENE_HEIGHT);
      newScene.getStylesheets().add(DEFAULT_RESOURCES_FOLDER + resources.getString("CSS"));
      return newScene;
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
        InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
      return null;
    }
  }


  public abstract void assembleScene();

  public GameScene getScene () {
    assembleScene();
    return myScene;
  }
}
