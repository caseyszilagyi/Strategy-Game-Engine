package ooga.model.initialization.engine;

import java.io.File;
import ooga.model.engine.running.Engine;

/**
 * This interface will be used to initialize all types of games. This means choosing the game
 * type, setting the board configuration, settting the rules, and setting the players
 */
public interface Initializer {

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
   * Sets the initial state of the board
   * @param boardFile The file that has the board
   */
  public void setBoardState(File boardFile);

  /**
   * Sets a player that is playing the game
   * @param user The string associated with the person playing on the bottom of the board
   * @param opponent The string associated with the person pplaying on the top of the board
   */
  public void addPlayers(String user, String opponent);

  /**
   * Gets the engine that is being used to run the back end
   * @return The engine
   */
  public Engine getEngine();
}
