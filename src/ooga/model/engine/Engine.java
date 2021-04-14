package ooga.model.engine;

import ooga.model.components.GameBoard;
import ooga.model.components.GameRules;
import ooga.model.components.Player;
import ooga.model.engine.action_files.Action;

/**
 * Implemented by any type of game engine that is designed to run a specific type of program. The
 * engine is the core class that contains all of the references to other classes
 * that are meant to be used in order to give the engine its functionality
 */
public abstract class Engine{


  public abstract void setGameType(String gameType);

  /**
   * Checks if a player has one, and makes the appropriate method calls to front end
   * if so
   */
  public abstract void checkForFinish();

  /**
   * Saves the current state of the game in a file
   * @param fileName The name of the file to save the game in
   */
  public abstract void saveCurrentState(String fileName);

  /**
   * Adds a player that is playing
   * @param player The object representing the player
   */
  public abstract void addActiveUser(Player player);

  /**
   * Checks the rules to see if it's the players next turn, and moves it along
   * if it is
   */
  public abstract void checkForNextTurn();

  /**
   * Starts a player's timer
   * @param player The player
   */
  public abstract void startPlayerTimer(Player player);

  /**
   * Stops a player's timer
   * @param player The player
   */
  public abstract void stopPlayerTimer(Player player);

  public abstract void setRules(GameRules rules);

  public abstract void setBoard(GameBoard board);

  /**
   * Alters the board in the way described in the Action parameter
   * @param action is the Action.java to perform
   */
  public abstract void executeAction(Action action);

  public abstract void executeAction(String action);

  public abstract GameBoard getBoard();

  public abstract void actOnCoordinates(int x, int y);

}
