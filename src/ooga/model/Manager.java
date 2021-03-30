package ooga.model;

import ooga.model.game_initialization.Creator;
import ooga.model.game_initialization.GameCreator;


/**
 * Meant to be implemented by the model controller as the way to interface with the back end.
 */
public interface Manager {

  /**
   * Creates an instance of the specific game type
   * @param gameName The name of the game
   */
  public void initializeGame(String gameName);

  /**
   * Sets the initial state of the board
   * @param boardFileName The file name that contains the board
   */
  public void setBoardState(String boardFileName);

  /**
   * Sets the rules that the user will play with
   * @param rulesFileName The file name that contains the rules of the game
   */
  public void setGameRules(String rulesFileName);

  /**
   * Sets a player that is playing the game
   * @param playerPosition A number associated with the player
   * @param playerFileName The string associated with the player name
   */
  public void setPlayer(int playerPosition, String playerFileName);


}
