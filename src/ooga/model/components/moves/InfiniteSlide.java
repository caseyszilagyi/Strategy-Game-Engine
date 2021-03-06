package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This movement class represents a move that can travel an infinite number of spaces in a certain
 * direction. However, it cannot travel through other pieces because it slides
 *
 * This class should be used via reflection, and should be defined in the GamePiece's .xml file in the
 * nested parameters:
 * <pre> {@code
 * <moves>
 *  <InfiniteJump>
 *    <changeX>1</changeX> //the integer is variable
 *       <changeY>2</changeY> //the integer is variable
 *       <mustTake>False</mustTake> //the boolean is variable
 *       <takeX>null</takeX> // the null value can be set to an integer
 *       <takeY>null</takeY> // the null value can be set to an integer
 *       <restrictions>
 *       </restrictions>
 *  </InfiniteJump>
 * </moves>
 * }
 * </pre>
 *
 *
 * @author Casey Szilagyi
 */
public class InfiniteSlide extends PieceMovement {

  private GameBoard board;

  /**
   * Initializes with the parameters of the move, as well as the direction that the piece should be
   * going
   *
   * @param parameters The parameters of the move
   * @param direction  1 if going up, -1 if going down. Used to adjust parameters for different
   *                   sides of the board
   */
  public InfiniteSlide(Map<String, String> parameters, int direction, GameBoard gameBoard, GamePiece piece) {
    super(parameters, direction, gameBoard);
    board = gameBoard;
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
   * @param teamName    The name of the team of this piece
   * @return A list of the coordinates of possible moves
   */
  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates,
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
  private void getNonTakeMoves(Coordinate coordinates, String teamName,
      List<Coordinate> possibleMoves) {
    while (checkIfValidMove(coordinates, teamName)) {
      Coordinate newCoordinate = new Coordinate(coordinates, getChangeX(), getChangeY());
      if (checkRestrictions(newCoordinate)) {
        possibleMoves.add(newCoordinate);
      }
      coordinates = newCoordinate;
    }
  }

  // if this instance of the pieceMovement takes pieces, this method is called
  private void getTakeMoves(Coordinate coordinates, String teamName,
      List<Coordinate> possibleMoves) {
    while (checkIfMoveInBounds(coordinates) && checkThatNoFriendlyPieceInMoveDestination(
        coordinates, teamName)) {
      Coordinate newCoordinate = new Coordinate(coordinates, getChangeX(), getChangeY());
      if (checkEnemyPieceLocationConditions(coordinates, teamName) && checkRestrictions(
          newCoordinate)) {
        possibleMoves.add(newCoordinate);
        break;
      }
      coordinates = newCoordinate;
    }
  }
}
