package ooga.model.components.moverestrictions;

import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class KingSideCastle extends Restriction {

  private GamePiece king;
  private GameBoard board;

  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param viewController The controller used to communicate with the front end
   * @param gameBoard      The board that the pieces are on.
   * @param parameters     The map with parameters, if there are any
   * @param piece          The piece that the restriction corresponds to
   */
  public KingSideCastle(FrontEndExternalAPI viewController, GameBoard gameBoard,
      Map<String, String> parameters, GamePiece piece) {
    super(viewController, gameBoard, parameters, piece);
    king = piece;
    board = gameBoard;
  }

  @Override
  public boolean checkRestriction(Coordinate endingCoordinates) {
    GamePiece rook = board.getPieceAtCoordinate(new Coordinate(endingCoordinates, 1, 0));
    if(!king.hasMoved() && rook != null && !rook.hasMoved() && rook.getPieceName().equals("rook")){
      return true;
    }
    return false;
  }
}
