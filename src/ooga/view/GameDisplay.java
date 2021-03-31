package ooga.view;

import ooga.controller.BackEndExternalAPI;

public class GameDisplay implements Display{

  BackEndExternalAPI modelController;

  /**
   * Model controller that is used to send method calls to the model
   * @param newModelController The model controller
   */
  @Override
  public void setModelController(BackEndExternalAPI newModelController) {
    modelController = newModelController;
  }
}
