package ooga.controller;

import ooga.model.engine.Engine;
import ooga.model.initialization.engine.Initializer;
import ooga.model.initialization.engine.EngineInitializer;

public class ModelController implements BackEndExternalAPI {

  private Engine gameEngine;

  private Initializer creator;
  private FrontEndExternalAPI boardController;

  public ModelController(){
  }

  @Override
  public String toString(){
    return "Valid Model Controller";
  }

  /**
   * Sets the view controller that this modelController is linked to
   * @param newViewController The view controller
   */
  @Override
  public void setBoardController(FrontEndExternalAPI newViewController) {
    boardController = newViewController;
    creator = new EngineInitializer(boardController);
    gameEngine = creator.getEngine();
    gameEngine.setIfTurnRules(false);
  }

  /**
   * Sets the game that the engine initializer will start. By default, this initializes
   * the normal version of the game
   * @param gameName The name of the game
   */
  @Override
  public void setGameType(String gameName) {
    creator.initializeGame(gameName);
    setPlayers("user", "opponent");
  }

  /**
   * The method that the front end calls whenever a square is clicked on the board
   * @param x The x coordinate
   * @param y The y coordinate
   */
  @Override
  public void actOnCoordinates(int x, int y) {
    gameEngine.runTurn(x, y);
  }

  /**
   * Sets the board state to a different one than the default
   * @param boardFileName The file name that contains the board
   */
  @Override
  public void setBoardState(String boardFileName) {
    creator.setBoardState(boardFileName);

  }


  //Everything below has not yet been implemented

  @Override
  public void modifyGameRules(String rulesFileName) {
    creator.setGameRules(rulesFileName);
  }

  @Override
  public void setPlayers(String user, String opponent) {
    creator.addPlayers(user, opponent);
  }

  @Override
  public void pauseGame() {

  }

  @Override
  public void resumeGame() {

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
