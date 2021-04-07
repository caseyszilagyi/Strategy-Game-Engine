package ooga.model.game_components.move_types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;

/**
 * This movement class represents a piece that can travel over other pieces, but only for a
 * specified number of spaces
 *
 * @author Casey Szilagyi
 */
public class FiniteJump extends PieceMovement {

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters The map of parameters
   */
  public FiniteJump(Map<String, String> parameters, int direction) {
    super(parameters, direction);
  }

  /**
   * Checks if the single move that this PieceMovement object can make if valid. If it is, it
   * returns it returns it in a single item list. If not, an empty arraylist is returned.
   *
   * @param coordinates The coordinates of  the piece that this move is acting on
   * @param board       The board that this piece is on
   * @param teamName    The team that the piece is associated with
   * @return Either the coordinates of the valid move, or an empty ArrayList
   */
  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board,
      String teamName) {
    List<Coordinate> possibleMoves = new ArrayList<>();
    if (checkIfMoveInBounds(coordinates) && checkEnemyPieceLocationConditions(coordinates, teamName)
        && checkThatNoFriendlyPieceInMoveDestination(coordinates, teamName)) {
      Coordinate result = makeCoordinate(coordinates.getX() + getChangeX(),
          coordinates.getY() + getChangeY());
      possibleMoves.add(result);
    }
    return possibleMoves;
  }
}
