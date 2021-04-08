package ooga.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FXMLController {
  public FXMLController(){

  }

  @FXML
  private void handleClick(ActionEvent mouseEvent){
    System.out.println("User clicked Start");
  }
}
