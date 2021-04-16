package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class KingSide extends PieceMovement{

  GamePiece king;
  GamePiece rook;
  GameBoard board;

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
  public KingSide(Map<String, String> parameters, int direction,
      GameBoard gameBoard, FrontEndExternalAPI viewController,
      GamePiece correspondingPiece) {
    super(parameters, direction, gameBoard, viewController, correspondingPiece);
    king = correspondingPiece;
    board = gameBoard;
  }

  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board,
      String pieceTeam) {
      int kingX = coordinates.getX();
      int kingY = coordinates.getY();
      rook = board.getPieceAtCoordinate(kingX+3, kingY);
      List<Coordinate> moves = new ArrayList<>();
      if(!king.hasMoved() && rook != null && !rook.hasMoved() && !rook.hasMoved() &&
      !board.isPieceAtCoordinate(kingX+1, kingY) && !board.isPieceAtCoordinate(kingX+2, kingY)){
        moves.add(new Coordinate(kingX + 2, kingY));
      }
      return moves;
  }

  @Override
  public void executeMove(Coordinate startingCoordinates, Coordinate endingCoordinates){
    board.movePiece(startingCoordinates, endingCoordinates);
    board.movePiece(new Coordinate(endingCoordinates, 1, 0),
        new Coordinate(endingCoordinates, -1, 0));
  }
}
