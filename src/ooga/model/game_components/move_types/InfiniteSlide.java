package ooga.model.game_components.move_types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;

/**
 * This movement class represents a move that can travel an infinite number of spaces in a certain
 * direction. However, it cannot travel through other pieces because it slides
 *
 * @author Casey Szilagyi
 */
public class InfiniteSlide extends PieceMovement {

  /**
   * Initializes with the parameters of the move, as well as the direction that the piece should be
   * going
   *
   * @param parameters The parameters of the move
   * @param direction  1 if going up, -1 if going down. Used to adjust parameters for different
   *                   sides of the board
   */
  public InfiniteSlide(Map<String, String> parameters, int direction) {
    super(parameters, direction);
  }

  /**
   * Gets all the possible moves associated with this infiniteSlide movement object. Will be a
   * single set of coordinates or no coordinates if it can take pieces, because it will simply slide
   * until it is able to take a piece. Will be multiple coordinates if it can't (provided it isn't
   * at the edge of the board), because it will slide until a piece gets in its way or it reaches
   * the edge of the board. The coordinate object passed into this method is altered, because in
   * theory it should be passed from the front end but not used for anything else besides this
   * method
   *
   * @param coordinates The coordinates of the piece that this move is acting on
   * @param board       The board that this piece is on
   * @param teamName    The name of the team of this piece
   * @return A list of the coordinates of possible moves
   */
  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board,
      String teamName) {
    List<Coordinate> possibleMoves = new ArrayList<>();
    if (isMustTake()) {
      getTakeMoves(coordinates, teamName, possibleMoves);
    } else {
      getNonTakeMoves(coordinates, teamName, possibleMoves);
    }
    return possibleMoves;

  }

  // if this instance of the pieceMovement doesn't take pieces, this method is called
  private void getNonTakeMoves(Coordinate coordinates, String teamName, List<Coordinate> possibleMoves) {
    while (checkIfValidMove(coordinates, teamName)) {
      Coordinate newCoord = makeCoordinate(coordinates.getX() + getChangeX(),
          coordinates.getY() + getChangeY());
      possibleMoves.add(newCoord);
      coordinates = newCoord;
    }
  }

  // if this instance of the pieceMovement takes pieces, this method is called
  private void getTakeMoves(Coordinate coordinates, String teamName, List<Coordinate> possibleMoves) {
    while (checkIfMoveInBounds(coordinates) && checkThatNoFriendlyPieceInMoveDestination(
        coordinates, teamName)) {
      if (checkEnemyPieceLocationConditions(coordinates, teamName)) {
        possibleMoves.add(
            makeCoordinate(coordinates.getX() + getChangeX(), coordinates.getY() + getChangeY()));
        break;
      }
      coordinates = makeCoordinate(coordinates.getX() + getChangeX(),
          coordinates.getY() + getChangeY());
    }
  }
}
