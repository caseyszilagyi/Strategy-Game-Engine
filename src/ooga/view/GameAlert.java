package ooga.view;

import javafx.scene.control.Alert;

/**
 * A custom alert window class that displays an alert with the desired message.
 */
public class GameAlert extends Alert {

  public GameAlert(AlertType type, String errorMessage) {
    super(type);
    setContentText(errorMessage);
    show();
  }

}
