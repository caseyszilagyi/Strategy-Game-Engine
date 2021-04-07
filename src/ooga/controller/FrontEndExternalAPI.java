package ooga.controller;

import java.util.Map;
import javafx.util.Pair;

/**
 * Classes that implement this interface are meant to be passed around the back end in
 * order to call the appropriate methods on the front end
 *
 * @author Casey Szilagyi
 */
public interface FrontEndExternalAPI {

  /**
   * Links this half of the controller to the other half that is passed around the front end
   * @param modelController The other half of the controller
   */
  public void setModelController(BackEndExternalAPI modelController);

  /**
   * Sets the board dimensions that should be displayed
   * @param width The width of the board
   * @param height The height of the board
   */
  public void setBoardDimensions(int width, int height);

  /**
   * Sets a specific board space to a certain object
   * @param row The row of the space
   * @param column The column of the space
   * @param identifier The identifier of the piece
   */
  public void setBoardSpace(int row, int column, String identifier);

  /**
   * Sets a board space to empty
   * @param row The row of the space
   * @param column The column of the space
   */
  public void clearBoardSpace(int row, int column);

  /**
   * Used for setting
   * @param friendlyPieces A map of location to piece name for friendly pieces
   * @param opponentPieces A map of location to piece name for opponent pieces
   */
  public void setInitialPieces(Map<String, String> friendlyPieces, Map<String, String> opponentPieces);

  /**
   * Gives all of the possible moves that were requested
   * @return An iterable of pairs that represent the possible moves
   */
  public Iterable<Pair<Integer, Integer>> giveAllPossibleMoves();


  /**
   * Called when the game is done and a player has won
   * @param playerName The name of the winning player
   */
  public void gameWin(String playerName);

  /**
   * Called when the game is done and a player has lost
   * @param playerName The name of the losing player
   */
  public void gameLose(String playerName);

  /**
   * Sets a player that is playing the game
   * @param playerNumber The number of the player so it can be kept track of
   * @param playerName The name of the player
   */
  public void setPlayer(int playerNumber, String playerName);


  /**
   * Gives the pieces that a selected piece can be changed to
   * @return The pieces it can be changed to
   */
  public Iterable<String> givePieceChangeOptions();
}
