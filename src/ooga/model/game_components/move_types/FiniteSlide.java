package ooga.model.game_components.move_types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;

/**
 * This movement class represents a move that can travel an finite number of spaces in a certain
 * direction. However, it cannot travel through other pieces because it slides.
 * <p>
 * The implementation of this class makes the assumption that finite slide moves will be in either a
 * diagonal direction, or horizontal/vertical. This is because anything other than these cases makes
 * it unclear what path the piece takes, making it unworkable with the current information that the
 * class gets
 *
 * @author Casey Szilagyi
 */
public class FiniteSlide extends PieceMovement {

  /**
   * Initializes with the parameters of the move, as well as the direction that the piece should be
   * going
   *
   * @param parameters The parameters of the move
   * @param direction  1 if going up, -1 if going down. Used to adjust parameters for different
   *                   sides of the board
   */
  public FiniteSlide(Map<String, String> parameters, int direction, GameBoard gameBoard, FrontEndExternalAPI viewController) {
    super(parameters, direction, gameBoard, viewController);
  }

  /**
   * Gets all the possible moves associated with this finiteSlide movement object. Will be a single
   * set of coordinates or no coordinates if it can take pieces, because it will simply slide until
   * it is able to take a piece. Can be zero or more sets of coordinates if not,  because it will
   * slide until a piece gets in its way or it reaches the edge of the board or the end of it's
   * sliding distance. The coordinate object passed into this method is altered, because in theory
   * it should be passed from the front end but not used for anything else besides this method
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
    int xLimit = getChangeX();
    int yLimit = getChangeY();
    int xDirection;
    int yDirection;
    if(xLimit != 0){
      xDirection = xLimit/Math.abs(xLimit);
    }
    else{
      xDirection = 0;
    }
    if(yLimit != 0){
      yDirection = yLimit/Math.abs(yLimit);
    }
    else{
      yDirection = 0;
    }

    int currX = xDirection;
    int currY = yDirection;
    setChangeX(xDirection);
    setChangeY(yDirection);

    while (checkIfValidMove(coordinates, teamName) && Math.abs(currX) <= Math.abs(xLimit) && Math.abs(currY) <= Math.abs(yLimit)) {
      Coordinate newCoordinates = makeCoordinate(coordinates.getX() + xDirection,
          coordinates.getY() + yDirection);
      possibleMoves.add(newCoordinates);
      coordinates = newCoordinates;
      currX+= xDirection;
      currY+= yDirection;
    }
  }

  // if this instance of the pieceMovement takes pieces, this method is called
  private void getTakeMoves(Coordinate coordinates, String teamName, List<Coordinate> possibleMoves) {
    int xLimit = getChangeX();
    int yLimit = getChangeY();
    int xDirection;
    int yDirection;
    if(xLimit != 0){
      xDirection = xLimit/Math.abs(xLimit);
    }
    else{
      xDirection = 0;
    }
    if(yLimit != 0){
      yDirection = yLimit/Math.abs(yLimit);
    }
    else{
      yDirection = 0;
    }

    int currX = xDirection;
    int currY = yDirection;
    setChangeX(xDirection);
    setChangeY(yDirection);

    while (checkIfMoveInBounds(coordinates) && checkThatNoFriendlyPieceInMoveDestination(
        coordinates, teamName) && Math.abs(currX) <= Math.abs(xLimit) && Math.abs(currY) <= Math.abs(yLimit)) {
      if (checkEnemyPieceLocationConditions(coordinates, teamName)) {
        possibleMoves.add(
            makeCoordinate(coordinates.getX() + getChangeX(), coordinates.getY() + getChangeY()));
        break;
      }
      coordinates = makeCoordinate(coordinates.getX() + xDirection, coordinates.getY()+yDirection);
      currX+= xDirection;
      currY+= yDirection;
    }
  }

}
