package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This movement class represents a piece that can travel over other pieces, but only for a
 * specified number of spaces
 *
 * @author Casey Szilagyi
 */
public class FiniteJump extends PieceMovement {

  private GameBoard board;

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters The map of parameters
   */
  public FiniteJump(Map<String, String> parameters, int direction, GameBoard gameBoard, GamePiece piece) {
    super(parameters, direction, gameBoard);
    board = gameBoard;
  }

  /**
   * Checks if the single move that this PieceMovement object can make if valid. If it is, it
   * returns it returns it in a single item list. If not, an empty arraylist is returned.
   *
   * @param coordinates The coordinates of  the piece that this move is acting on
   * @param teamName    The team that the piece is associated with
   * @return Either the coordinates of the valid move, or an empty ArrayList
   */
  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates,
      String teamName) {
    List<Coordinate> possibleMoves = new ArrayList<>();
    if (checkIfValidMove(coordinates, teamName) && checkRestrictions(coordinates)) {
      Coordinate result = makeCoordinate(coordinates.getX() + getChangeX(),
          coordinates.getY() + getChangeY());
      possibleMoves.add(result);
    }
    return possibleMoves;
  }

}
