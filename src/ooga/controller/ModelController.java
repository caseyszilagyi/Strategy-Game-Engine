package ooga.controller;

import ooga.model.GameManager;
import ooga.model.Manager;

public class ModelController implements BackEndExternalAPI {

  private Manager manager;
  private FrontEndExternalAPI viewController;

  public ModelController(){
    manager = new GameManager();
  }

  @Override
  public void setViewController(FrontEndExternalAPI newViewController) {
    viewController=newViewController;
    manager.setViewController(viewController);
  }

  @Override
  public void setGameType(String gameName) {
    manager.initializeGame(gameName);
  }

  @Override
  public void setGameRules(String rulesFileName) {
    manager.setGameRules(rulesFileName);
  }

  @Override
  public void setLanguage(String language) {

  }

  @Override
  public void setPlayer(int playerPosition, String playerFileName) {
    manager.setPlayer(playerPosition, playerFileName);
  }

  @Override
  public void setBoardState(String boardFileName) {
    manager.setBoardState(boardFileName);
  }

  @Override
  public void saveCurrentBoardState(String fileName) {

  }

  @Override
  public void saveCurrentRules(String fileName) {

  }

}
