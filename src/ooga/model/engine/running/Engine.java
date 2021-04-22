package ooga.model.engine.running;

import ooga.model.components.GameBoard;
import ooga.model.components.Player;
import ooga.model.engine.actions.Action;

/**
 * Implemented by any type of game engine that is designed to run a specific type of program. The
 * engine is the core class that contains all of the references to other classes
 * that are meant to be used in order to give the engine its functionality
 */
public abstract class Engine{

  /**
   * This is the method that is called to run the current turn
   * must set a currentPlayerTurn before calling
   * Todo: make sure that a currentPlayerTurn has been set
   */
  public abstract void runTurn(int x, int y);

  /**
   * Sets the gameType associated with this engine
   * @param gameType is the String of the name of the game to be run
   */
  public abstract void setGameType(String gameType);

  public abstract void setBoard(GameBoard board);

  public abstract GameBoard getBoard();

  /**
   * Adds a player that is playing
   * @param player The object representing the player
   */
  public abstract void addActiveUser(Player player);

  /**
   * Gets the player who's turn it is
   * @return The string of the name of the player who's turn it is
   */
  public abstract String getCurrentPlayerTurn();

  public abstract void setIfTurnRules(Boolean rules);

  public abstract void checkForWin();

  /**
   * Alters the board in the way described in the Action parameter
   * @param action is the Action.java to perform
   */
  public abstract void executeAction(Action action);

  public abstract void executeAction(String action);

  /**
   * Saves the current state of the game in a file
   * @param fileName The name of the file to save the game in
   */
  public abstract void saveCurrentState(String fileName);

  public abstract void undoTurn();

}