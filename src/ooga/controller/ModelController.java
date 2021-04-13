package ooga.controller;

import ooga.model.game_engine.Engine;
import ooga.model.game_initialization.Initializer;
import ooga.model.game_initialization.EngineInitializer;

public class ModelController implements BackEndExternalAPI {

  private Engine gameEngine;
  private Initializer creator;
  private FrontEndExternalAPI viewController;

  public ModelController(){
  }

  /**
   * Sets the view controller that this modelController is linked to
   * @param newViewController The view controller
   */
  @Override
  public void setViewController(FrontEndExternalAPI newViewController) {
    viewController=newViewController;
    creator = new EngineInitializer(viewController);
    gameEngine = creator.getEngine();
  }

  /**
   * Sets the game that the engine initializer will start. By default, this initializes
   * the normal version of the game
   * @param gameName The name of the game
   */
  @Override
  public void setGameType(String gameName) {
    creator.initializeGame(gameName);
  }

  /**
   * The method that the front end calls whenever a square is clicked on the board
   * @param x The x coordinate
   * @param y The y coordinate
   */
  @Override
  public void actOnCoordinates(int x, int y) {
    gameEngine.actOnCoordinates(x, y);
  }


  //Everything below has not yet been implemented

  @Override
  public void modifyGameRules(String rulesFileName) {
    creator.setGameRules(rulesFileName);
  }

  @Override
  public void setPlayers(String player1, String player2) {

  }

  @Override
  public void pauseGame() {

  }

  @Override
  public void resumeGame() {

  }
  /**
   * Sets the board state to a different one than the default
   * @param boardFileName The file name that contains the board
   */
  @Override
  public void setBoardState(String boardFileName) {
    creator.setBoardState(boardFileName);
  }

  /**
   * Save the current state of the board in a file
   * @param fileName The name of the file to store the game information
   */
  @Override
  public void saveGame(String fileName) {

  }

  @Override
  public void executeAction(String actionString) {
    gameEngine.executeAction(actionString);
  }

  //for testing
  public Engine getEngine(){
    return gameEngine;
  }


}
