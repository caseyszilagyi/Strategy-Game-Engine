package ooga.model.components.moves;

import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class KingSideCastle extends PieceMovement{

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters         The map of parameters
   * @param direction          The multiplier used to change the direction that the piece uses
   * @param gameBoard          The board that the piece moves on
   * @param viewController     The view controller used to make front-end method calls
   * @param correspondingPiece The piece that this move corresponds to
   */
  public KingSideCastle(Map<String, String> parameters, int direction,
      GameBoard gameBoard, FrontEndExternalAPI viewController,
      GamePiece correspondingPiece) {
    super(parameters, direction, gameBoard, viewController, correspondingPiece);
  }

  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board,
      String pieceTeam) {
    return null;
  }
}
