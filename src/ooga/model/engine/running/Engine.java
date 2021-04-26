package ooga.model.engine.running;

import ooga.model.components.GameBoard;
import ooga.model.components.player.Player;
import ooga.model.components.computer.AI;

/**
 * Implemented by any type of game engine that is designed to run a specific type of program. The
 * engine is the core class that contains all of the references to other classes
 * that are meant to be used in order to give the engine its functionality
 *
 * @author Cole Spector
 * @author Casey Szilagyi
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

  /**
   * Sets the ClickExecutor to be used
   * @param clickExecutor the ClickExecutor to be used
   */
  public abstract void setClickExecutor(ClickExecutor clickExecutor);

  /**
   * Sets the GameBoard to be used
   * @param board the GameBoard to be used
   */
  public abstract void setBoard(GameBoard board);

  /**
   * return the GameBoard which is being used
   * @return the current GameBoard being used
   */
  public abstract GameBoard getBoard();

  /**
   * Adds a player that is playing
   * @param player1 The object representing the player
   */
  public abstract void addActiveUsers(Player player1, Player player2);

  /**
   * Adds an AI to the game
   * @param ai the AI to be added to the game
   */
  public abstract void addAI(AI ai);
  /**
   * Gets the player who's turn it is
   * @return The string of the name of the player who's turn it is
   */
  public abstract String getCurrentPlayerTurn();

  /**
   * Sets if there are no turn rules for the game
   * @param rules whether or not there are turn rules for the game
   */
  public abstract void setIfNoTurnRules(Boolean rules);

  /**
   * Checks if someone has won the game
   * @return a boolean value representing whether or not someone has won the game
   */
  public abstract boolean checkForWin();

  /**
   * Checks to see if the winConidition is hit.  This method is mostly used for testing
   * @return whether or not the game is over
   */
  public abstract boolean isGameOver();



  /**
   * Saves the current state of the game in a file
   * @param fileName The name of the file to save the game in
   */
  public abstract void saveCurrentState(String fileName);

  /**
   * Undoes the previous turn
   */
  public abstract void undoTurn();

  /**
   * Sets the active AI
   * @param computer the AI to set as the active AI
   */
  public abstract void setAI(AI computer);

}
