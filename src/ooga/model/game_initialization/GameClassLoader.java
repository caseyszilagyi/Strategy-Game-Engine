package ooga.model.game_initialization;

import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;

/**
 * This may or may not need to be different for each game type? probably not but not 100% sure
 */
public class GameClassLoader implements Loader {

  @Override
  public GameRules makeRules(String gameType) { return new GameRules(); }

  @Override
  public Player makePlayer(String playerDetails) {
    return new Player();
  }

  @Override
  public GameBoard makeBoard(String boardDetails) {
    return new GameBoard();
  }

  @Override
  public GamePiece makePiece(String pieceDetails) {
    return null;
  }
}
