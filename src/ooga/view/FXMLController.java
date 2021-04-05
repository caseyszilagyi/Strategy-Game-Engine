package ooga.view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class FXMLController {
  public FXMLController(){

  }

  @FXML
  private void handleImageClick(MouseEvent mouseEvent){
    System.out.println("Clicked on image");
  }
}
