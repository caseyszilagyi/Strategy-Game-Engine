package ooga.controller;

import ooga.view.Display;

public class ViewController implements FrontEndExternalAPI{

  private Display display;
  private BackEndExternalAPI modelController;

  public ViewController(){

  }

  @Override
  public void setModelController(BackEndExternalAPI newModelController) {
    modelController = newModelController;
    display.setModelController(modelController);
  }

  @Override
  public void setBoardDimensions(int width, int height) {

  }

  @Override
  public void setBoardSpace(int row, int column, String identifier) {

  }

  @Override
  public void gameWin(String playerName) {

  }

  @Override
  public void gameLose(String playerName) {

  }

  @Override
  public void setPlayer(int playerNumber, String playerName) {

  }
}
