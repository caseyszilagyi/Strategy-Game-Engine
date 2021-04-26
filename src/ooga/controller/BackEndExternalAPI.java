package ooga.controller;

import java.io.File;
import ooga.model.engine.running.Engine;

/**
 * Classes that implement this interface are meant to be passed around the front end in order to
 * call the appropriate methods on the back end
 *
 * @author Casey Szilagyi
 */
public interface BackEndExternalAPI {

  /**
   * Links this half of the controller to the other half that is passed around the back end
   *
   * @param viewController The other half of the controller
   */
  public void setBoardController(FrontEndExternalAPI viewController);


  /**
   * Sets the type of game that will be played
   *
   * @param gameName The name of the game
   */
  public void setGameType(String gameName);

  /**
   * Sets a player that is playing the game
   *
   * @param user     The first player, who has the bottom of the board
   * @param opponent The second player, who has the top of the board
   */
  public void setPlayers(String user, String opponent);

  /**
   * Sets the initial state of the board
   *
   * @param boardFileName The file name that contains the board
   */
  public void setBoardState(String boardFileName);

  public void setBoardState(File boardFile);

  /**
   * Acts on this set of coordinates. May include showing all possible moves, placing a piece, or
   * doing nothing
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public void actOnCoordinates(int x, int y);

  public void undoTurn();

  public void changePiece(String pieceName);

  // for testing
  public Engine getEngine();


}
