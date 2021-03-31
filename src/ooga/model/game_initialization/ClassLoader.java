package ooga.model.game_initialization;

import ooga.model.game_components.Board;
import ooga.model.game_components.Piece;
import ooga.model.game_components.Player;
import ooga.model.game_engine.Engine;

public interface ClassLoader {

  /**
   * Makes an engine that runs the given game type
   * @param gameType The name of the game
   * @param gameData The data of the game
   * @return The engine
   */
  public Engine makeEngine(String gameType, String gameData);

  /**
   * Makes a player from the given file
   * @param playerFileName The file name that has the player information
   * @return The player object representing the player
   */
  public Player makePlayer(String playerFileName);

  /**
   * Makes a board from the given file
   * @param boardFileName The file name that has the board information
   * @return The board object representing the player
   */
  public Board makeBoard(String boardFileName);

  /**
   * Makes a piece from the given file
   * @param pieceFileName The file name that has the board information
   * @return The piece object representing the player
   */
  public Piece makePiece(String pieceFileName);
}
