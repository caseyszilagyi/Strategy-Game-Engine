package ooga.model.game_initialization;

import ooga.model.game_engine.Engine;

/**
 * This interface will be used to initialize all types of games. This means choosing the game
 * type, setting the board configuration, settting the rules, and setting the players
 */
public interface Creator {

  /**
   * Initializes the default rules of the game
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
   * @param playerFileName The string associated with the player name
   */
  public void setPlayer(String playerFileName);

  /**
   * Gets the engine that is being used to run the back end
   * @return The engine
   */
  public Engine getEngine();
}
