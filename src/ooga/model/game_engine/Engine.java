package ooga.model.game_engine;

import ooga.model.game_components.Player;

/**
 * Implemented by any type of game engine that is designed to run a specific type of program. The
 * game_engine is the core class that contains all of the references to other classes
 * that are meant to be used in order to give the game_engine its functionality
 */
public interface Engine {

  /**
   * Checks if a player has one, and makes the appropriate method calls to front end
   * if so
   */
  public void checkForFinish();

  /**
   * Saves the current state of the game in a file
   * @param fileName The name of the file to save the game in
   */
  public void saveCurrentState(String fileName);

  /**
   * Adds a player that is playing
   * @param player The object representing the player
   */
  public void addActiveUser(Player player);

  /**
   * Checks the rules to see if it's the players next turn, and moves it along
   * if it is
   */
  public void checkForNextTurn();

  /**
   * Starts a player's timer
   * @param player The player
   */
  public void startPlayerTimer(Player player);

  /**
   * Stops a player's timer
   * @param player The player
   */
  public void stopPlayerTimer(Player player);


}
