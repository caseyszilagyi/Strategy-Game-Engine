package ooga.model.game_initialization;

import ooga.model.game_components.Board;
import ooga.model.game_components.Piece;
import ooga.model.game_components.Player;
import ooga.model.game_engine.Engine;

/**
 * This may or may not need to be different for each game type? probably not but not 100% sure
 */
public class GameClassLoader implements ClassLoader{

  @Override
  public Engine makeEngine(String gameType, String gameData) { return null; };

  @Override
  public Player makePlayer(String playerFileName) {
    return null;
  }

  @Override
  public Board makeBoard(String boardFileName) {
    return null;
  }

  @Override
  public Piece makePiece(String pieceFileName) {
    return null;
  }
}
