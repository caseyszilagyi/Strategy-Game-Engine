package ooga.controller;

import ooga.model.game_engine.Engine;
import ooga.model.game_initialization.Creator;
import ooga.model.game_initialization.GameCreator;

public class ModelController implements BackEndExternalAPI {

  private Engine gameEngine;
  private Creator creator;
  private FrontEndExternalAPI viewController;

  public ModelController(){
  }

  @Override
  public void setViewController(FrontEndExternalAPI newViewController) {
    viewController=newViewController;
    creator = new GameCreator(viewController);
  }

  @Override
  public void setGameType(String gameName) {
    creator.initializeGame(gameName);
  }

  @Override
  public void setGameRules(String rulesFileName) {
    creator.setGameRules(rulesFileName);
  }

  @Override
  public void setLanguage(String language) {

  }

  @Override
  public void setPlayer(String playerFileName) {
    creator.setPlayer(playerFileName);
  }

  @Override
  public void setBoardState(String boardFileName) {
    creator.setBoardState(boardFileName);
  }

  @Override
  public void saveCurrentBoardState(String fileName) {

  }

  @Override
  public void saveCurrentRules(String fileName) {

  }

}
