package ooga.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Controller2 {
  private GameSceneFactory sceneFactory = new GameSceneFactory();
  public Controller2() {
    searchButton = new Button();

  }


  private Button searchButton;

  private void initialize() {
    // search panel
    searchButton.setText("Search");
    searchButton.setOnAction(event -> loadData());
    searchButton.setStyle("-fx-background-color: #457ecd; -fx-text-fill: #ffffff;");
  }
  private void loadData() {
  }

  public void handleButtonClick(MouseEvent mouseEvent) {
    System.out.println("Button Pressed");
  }


  //https://www.baeldung.com/javafx
  //https://docs.oracle.com/javafx/2/text/jfxpub-text.htm#:~:text=to%20any%20shape.-,Adding%20Text,Example%201%20through%20Example%203.&text=Text%20t%20%3D%20new%20Text%20(10,objects%20by%20using%20the%20javafx.
  //https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
  public void handleImageClick(MouseEvent event) {
    System.out.println("Chess pressed");
    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    GameScene newScene = sceneFactory.makeScene("BoardScene");//TODO: hardcoded
    stage.setScene(newScene);
//    initialshowScene(newScene);
//    initialWindow.makeVisible();
  }


}
