package ooga.controller;

import ooga.model.engine.running.Engine;
import ooga.model.initialization.engine.Initializer;
import ooga.model.initialization.engine.EngineInitializer;

public class ModelController implements BackEndExternalAPI {

  private Initializer engineInitializer;
  private Engine gameEngine;
  private FrontEndExternalAPI boardController;


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
    engineInitializer = new EngineInitializer(boardController);
    gameEngine = engineInitializer.getEngine();
    gameEngine.setIfTurnRules(false);
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
   * Sets the game that the engine initializer will start. By default, this initializes
   * the normal version of the game
   * @param gameName The name of the game
   */
  @Override
  public void setGameType(String gameName) {
    engineInitializer.initializeGame(gameName);
    //engineInitializer.initializeGame("checkers");
    setPlayers("user", "opponent");
  }

  /**
   * Sets the board state to a different one than the default
   * @param boardFileName The file name that contains the board
   */
  @Override
  public void setBoardState(String boardFileName) {
    engineInitializer.setBoardState(boardFileName);

  }

  /**
   * Sets the players that will be playing the game
   * @param user The first player, who has the bottom of the board
   * @param opponent The second player, who has the top of the board
   */
  @Override
  public void setPlayers(String user, String opponent) {
    engineInitializer.addPlayers(user, opponent);
  }

  @Override
  public void undoTurn(){
    gameEngine.undoTurn();
  }


  //Everything below has not yet been implemented

  @Override
  public void forfeitGame(){

  }

  @Override
  public void offerDraw(){

  }


  @Override
  public void changePiece(String pieceName){

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



  //for testing
  public Engine getEngine(){
    return gameEngine;
  }


}
