package ooga.model.game_initialization;

import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;
import ooga.model.game_engine.Engine;

public interface Loader {

  /**
   * Makes the rules that define the game
   * @param gameType The name of the game
   * @return The rules
   */
  public GameRules makeRules(String gameType);

  /**
   * Makes a player from the given file
   * @param playerDetails The details of the player
   * @return The player object representing the player
   */
  public Player makePlayer(String playerDetails);

  /**
   * Makes a board from the given file
   * @param boardDetails The details of the board
   * @return The board object
   */
  public GameBoard makeBoard(String boardDetails);

  /**
   * Makes a piece from the given file
   * @param pieceDetails The details of the piece
   * @return The piece object
   */
  public GamePiece makePiece(String pieceDetails);
}
