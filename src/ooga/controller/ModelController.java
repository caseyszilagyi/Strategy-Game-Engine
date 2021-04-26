package ooga.controller;

import java.io.File;
import ooga.model.engine.running.Engine;
import ooga.model.initialization.engine.Initializer;
import ooga.model.initialization.engine.EngineInitializer;

/**
 * This class is used to interact with the modules in the backend. This class implements the
 * BackEndExternalAPI, and is dependant on the Engine.java class, and the Initializer.java class
 * <p>
 * Example Code:
 * <p>
 * BackEndExternalAPI modelController = new ModelController(); DummyViewController viewController =
 * new DummyViewController(); modelController.setBoardController(viewController);
 * modelController.setGameType("connectfour"); GameBoard gameBoard =
 * modelController.getEngine().getBoard(); Engine gameEngine = modelController.getEngine();
 * <p>
 * modelController.actOnCoordinates(0,0);
 * <p>
 * It is not necessary to link a view controller in order to run a game through the ModelController,
 * that is simply necessary if you want to view the board separately.
 *
 * @author Casey Szilagyi
 * @author Cole Spector
 */

public class ModelController implements BackEndExternalAPI {

  private Initializer engineInitializer;
  private Engine gameEngine;
  private FrontEndExternalAPI boardController;
  private String gameType;

  @Override
  public String toString() {
    return gameType;
  }

  /**
   * Sets the view controller that this modelController is linked to
   *
   * @param newViewController The view controller
   */
  @Override
  public void setBoardController(FrontEndExternalAPI newViewController) {
    boardController = newViewController;
    engineInitializer = new EngineInitializer(boardController);
    gameEngine = engineInitializer.getEngine();
  }

  /**
   * The method that the front end calls whenever a square is clicked on the board
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  @Override
  public void actOnCoordinates(int x, int y) {
    gameEngine.runTurn(x, y);
  }

  /**
   * Sets the game that the engine initializer will start. By default, this initializes the normal
   * version of the game
   *
   * @param gameName The name of the game
   */
  @Override
  public void setGameType(String gameName) {
    gameType = gameName;
    engineInitializer.initializeGame(gameName);
    setPlayers("user", "AI");
  }

  /**
   * Sets the board state to a different one than the default
   *
   * @param boardFileName The file name that contains the board
   */
  @Override
  public void setBoardState(String boardFileName) {
    engineInitializer.setBoardState(boardFileName);
  }


  /**
   * Sets the board state to a different one than the default
   *
   * @param boardFile The file that the board is contained in
   */
  @Override
  public void setBoardState(File boardFile) {
    engineInitializer.setBoardState(boardFile);
  }

  /**
   * Sets the players that will be playing the game
   *
   * @param user     The first player, who has the bottom of the board
   * @param opponent The second player, who has the top of the board
   */
  @Override
  public void setPlayers(String user, String opponent) {
    engineInitializer.addPlayers(user, opponent);
  }


  /**
   * Undoes a turn
   */
  @Override
  public void undoTurn(){
    gameEngine.undoTurn();
  }

  /**
   * Changes a piece
   * @param pieceName The name of the new piece
   */
  @Override
  public void changePiece(String pieceName) {

  }

  /**
   * Primarily used for testing, this method gets the game engine that the game is being run on
   *
   * @return The game engine
   */
  public Engine getEngine() {
    return gameEngine;
  }
}
