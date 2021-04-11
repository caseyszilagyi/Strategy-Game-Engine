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

  /**
   * Sets the game that the engine initializer will start. By default, this initializes
   * the normal version of the game
   * @param gameName The name of the game
   */
  @Override
  public void setGameType(String gameName) {
    creator.initializeGame(gameName);
  }


  @Override
  public void setGameRules(String rulesFileName) {
    creator.setGameRules(rulesFileName);
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

  /**
   * Adds an active player to the game
   * @param playerFileName The string associated with the player name
   */
  @Override
  public void addPlayer(String playerFileName) {
    creator.setPlayer(playerFileName);
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
  public void saveCurrentBoardState(String fileName) {

  }

  @Override
  public void saveCurrentRules(String fileName) {

  }


  @Override
  public void getAllPossibleMoves(int xPos, int yPos) {
    gameEngine.printAllPossibleMoves(xPos, yPos);
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
