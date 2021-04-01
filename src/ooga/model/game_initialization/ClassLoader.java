package ooga.model.game_initialization;

import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;
import ooga.model.game_engine.Engine;

public interface ClassLoader {

  /**
   * Makes the rules that define the game
   * @param gameType The name of the game
   * @return The rules
   */
  public GameRules makeRules(String gameType);

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
  public GameBoard makeBoard(String boardFileName);

  /**
   * Makes a piece from the given file
   * @param pieceFileName The file name that has the board information
   * @return The piece object representing the player
   */
  public GamePiece makePiece(String pieceFileName);
}
