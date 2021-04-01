package ooga.model.game_initialization;

import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;
import ooga.model.game_engine.Engine;

/**
 * This may or may not need to be different for each game type? probably not but not 100% sure
 */
public class GameClassLoader implements ClassLoader{

  @Override
  public GameRules makeRules(String gameType) { return null; }

  @Override
  public Player makePlayer(String playerFileName) {
    return null;
  }

  @Override
  public GameBoard makeBoard(String boardFileName) {
    return null;
  }

  @Override
  public GamePiece makePiece(String pieceFileName) {
    return null;
  }
}
