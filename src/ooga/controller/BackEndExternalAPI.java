package ooga.controller;

import javafx.util.Pair;
import ooga.model.game_engine.Engine;

/**
 * Classes that implement this interface are meant to be passed around the front end in
 * order to call the appropriate methods on the back end
 *
 * @author Casey Szilagyi
 */
public interface BackEndExternalAPI {

  /**
   * Links this half of the controller to the other half that is passed around the back end
   * @param viewController The other half of the controller
   */
  public void setViewController(FrontEndExternalAPI viewController);


  /**
   * Sets the type of game that will be played
   * @param gameName The name of the game
   */
  public void setGameType(String gameName);


  /**
   * Sets the rules that the game will follow
   * @param rulesFileName The name of the file with all the rules
   */
  public void modifyGameRules(String rulesFileName);

  /**
   * Sets a player that is playing the game
   * @param player1 The string associated with the player name
   * @param player2 The string associated with the player name
   */
  public void setPlayers(String player1, String player2);

  /**
   * Sets the initial state of the board
   * @param boardFileName The file name that contains the board
   */
  public void setBoardState(String boardFileName);

  /**
   * Saves the current state of the game in a file
   * @param fileName The name of the file to store the game information
   */
  public void saveGame(String fileName);


  /**
   * Makes the engine execute an action
   * @param actionString The action to execute
   */
  public void executeAction(String actionString);


  /**
   * Acts on this set of coordinates. May include showing all possible moves, placing
   * a piece, or doing nothing
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public void actOnCoordinates(int x, int y);


  /**
   * Used to pause the game
   */
  public void pauseGame();

  /**
   * Used to resume the game
   */
  public void resumeGame();


  // for testing
  public Engine getEngine();


}
