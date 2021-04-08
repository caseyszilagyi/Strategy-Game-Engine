package ooga.controller;

import ooga.model.game_engine.Engine;
import ooga.model.game_engine.GameEngine;
import ooga.model.game_initialization.Initializer;
import ooga.model.game_initialization.EngineInitializer;

public class ModelController implements BackEndExternalAPI {

  private Engine gameEngine;
  private Initializer creator;
  private FrontEndExternalAPI viewController;

  public ModelController(){
  }

  @Override
  public void setViewController(FrontEndExternalAPI newViewController) {
    viewController=newViewController;
    creator = new EngineInitializer(viewController);
    gameEngine = creator.getEngine();
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

  @Override
  public void getAllPossibleMoves(int xPos, int yPos) {
    gameEngine.printAllPossibleMoves(xPos, yPos);
  }

  //for testing
  public Engine getEngine(){
    return gameEngine;
  }

  @Override
  public void executeAction(String actionString) {
    gameEngine.executeAction(actionString);
  }

}
