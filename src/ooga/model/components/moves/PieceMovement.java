package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.moveconditions.Condition;
import ooga.model.components.moverestrictions.Restriction;

/**
 * This class is used to represent a singular move that a piece can do. It needs the board, as well
 * as sets of opponent/friendly pieces in order to determine whether moves are valid
 *
 * @author Casey Szilagyi
 */
public abstract class PieceMovement {

  FrontEndExternalAPI viewController;
  private List<Restriction> restrictions = new ArrayList<>();
  private List<Condition> conditions = new ArrayList<>();
  private GameBoard gameBoard;
  private GamePiece correspondingPiece;

  private int changeX;
  private int changeY;
  private boolean mustTake;
  private int takeX;
  private int takeY;

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters The map of parameters
   * @param direction The multiplier used to change the direction that the piece uses
   * @param gameBoard The board that the piece moves on
   * @param viewController The view controller used to make front-end method calls
   * @param correspondingPiece The piece that this move corresponds to
   */
  public PieceMovement(Map<String, String> parameters, int direction, GameBoard gameBoard, FrontEndExternalAPI viewController, GamePiece correspondingPiece) {
    changeX = Integer.parseInt(parameters.get("changeX")) * direction;
    changeY = Integer.parseInt(parameters.get("changeY")) * direction;
    mustTake = Boolean.parseBoolean(parameters.get("mustTake"));
    if (mustTake) {
      takeX = Integer.parseInt(parameters.get("takeX")) * direction;
      takeY = Integer.parseInt(parameters.get("takeY")) * direction;
    }
    this.gameBoard = gameBoard;
    this.viewController = viewController;
    this.correspondingPiece = correspondingPiece;
  }

  /**
   * Can be called on subclasses to determine the possible moves using the methods in this class.
   * This must be implemented differently for each subclass so therefore it is abstract
   *
   * @param coordinates The coordinates of  the piece that this move is acting on
   * @param board       The board that this piece is on
   * @return A list of all the coordinates of the possible move locations
   */
  public abstract List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board,
      String pieceTeam);


  /**
   * Executes a move when given the final coordinates
   * @param startingCoordinates The coordinates that the piece starts at
   * @param endingCoordinates The ending coordinates of the move
   */
  public void executeMove(Coordinate startingCoordinates, Coordinate endingCoordinates){
    if(mustTake){
      int removeX = endingCoordinates.getX() + takeX;
      int removeY = endingCoordinates.getY() + takeY;
      gameBoard.removePiece(makeCoordinate(removeX, removeY));
      viewController.removePiece(removeX, removeY);
    }
    gameBoard.movePiece(startingCoordinates, endingCoordinates);
    viewController.movePiece(startingCoordinates.getX(), startingCoordinates.getY(),
        endingCoordinates.getX(), endingCoordinates.getY());
    executeConditions(endingCoordinates);
  }


  /**
   * Checks if a move is valid based on the ending coordinates and name of the team
   * @param coordinates The ending coordinates of the move
   * @param teamName The name of the team of the piece being moved
   * @return True if the move is valid, false if not
   */
  protected boolean checkIfValidMove(Coordinate coordinates, String teamName){
    return checkIfMoveInBounds(coordinates) &&
           checkThatNoFriendlyPieceInMoveDestination(coordinates, teamName) &&
           checkEnemyPieceLocationConditions(coordinates, teamName) &&
           checkRestrictions(coordinates);
  }


  /**
   * Set the conditions that may be executed after this move
   * @param conditions The conditions
   */
  public void setConditions(List<Condition> conditions) { this.conditions = conditions; }

  /**
   * Set the restrictions that define whether this move is legal
   * @param restrictions A list of restrictions
   */
  public void setRestrictions(List<Restriction> restrictions){
    this.restrictions=restrictions;
  }

  /**
   * Executes the conditions. Only does something if the condition is determined to be valid
   * @param endingCoordinates The ending coordinates of the move
   */
  public void executeConditions(Coordinate endingCoordinates){
    conditions.stream().forEach(condition -> condition.executeCondition(endingCoordinates));
  }

  private boolean checkRestrictions(Coordinate endingCoordinates){
    return restrictions.stream().allMatch(restriction -> restriction.checkRestriction(endingCoordinates));
  }

  /**
   * Checks if a move is in bounds. The >= is due to the fact that the coordinate system starts at
   * 0. So if we have an 8x8 board, the possible coordinates range from 0-7, making 8 out of bounds
   *
   * @param coordinates The coordinates of the piece
   * @return A boolean representing if the move is in bounds or not
   */
  protected boolean checkIfMoveInBounds(Coordinate coordinates) {
    return gameBoard.isCoordinateOnBoard(makeCoordinate(coordinates.getX() + changeX, coordinates.getY() + changeY));
  }

  /**
   * Checks if a piece is in the take position, if this move type takes pieces. If there is, then we
   * also need to see if the piece taken is where the piece lands, and if it isn't then we need to
   * make sure that the piece landing spot is empty.
   *
   * @param coordinates The coordinates of the piece that this movement belongs to
   * @param teamName    the name of the team that this piece corresponds to
   * @return The boolean representing whether it has pieces to take
   */
  protected boolean checkEnemyPieceLocationConditions(Coordinate coordinates, String teamName) {
    if (mustTake) {
      return checkEnemyPieceLocationConditionsForTakeMove(coordinates, teamName);
    }

    // if piece can't take, need to make sure landing spot doesn't have opponent's piece
    if (gameBoard.checkIfOpponentPieceInLocation(makeCoordinate(coordinates.getX() + changeX, coordinates.getY() + changeY),
        teamName)) {
      return false;
    }

    return true;
  }

  // Checks the locations of enemy pieces for a take move to see if conditions are met
  private boolean checkEnemyPieceLocationConditionsForTakeMove(Coordinate coordinates, String teamName) {
    //checks to see if piece is where it needs to be in order for it to be taken. If not, move invalid
    if (!gameBoard.checkIfOpponentPieceInLocation(makeCoordinate(coordinates.getX() + changeX + takeX,
        coordinates.getY() + changeY + takeY), teamName)) {
      return false;
    }
    // checks to make sure there is an empty space where the piece lands, if the take location
    // is different from the landing location of the piece. If no empty space, move invalid
    if (takeX != 0 || takeY != 0) {
      if (gameBoard.checkIfOpponentPieceInLocation(makeCoordinate(coordinates.getX() + changeX, coordinates.getY() + changeY),
          teamName)) {
        return false;
      }
    }
    // if piece in take location and not in landing location
    return true;
  }

  /**
   * Checks to make sure a friendly piece isn't in the location where the movement object is
   * supposed to be going
   *
   * @param coordinates The starting coordinates of the piece
   * @param teamName    The name of the team that the piece is on
   * @return True if there is no piece in the landing location, false otherwise
   */
  protected boolean checkThatNoFriendlyPieceInMoveDestination(Coordinate coordinates,
      String teamName) {
    Coordinate moveCoords = makeCoordinate(coordinates.getX() + changeX, coordinates.getY() + changeY);
    if (gameBoard.checkIfFriendlyPieceInLocation(moveCoords, teamName)){
      return false;
    }
    return true;
  }


  /**
   * Makes a coordinate object
   * @param xPos The x position of the coordinate
   * @param yPos The y position of the coordinate
   * @return The coordinate object
   */
  protected Coordinate makeCoordinate(int xPos, int yPos){
    return new Coordinate(xPos, yPos);
  }

  /**
   * The change in x position of the piece for this move. Could be a finite change, or an infinite
   * change, depending on the subclass that implements
   *
   * @return The change in x position
   */
  protected int getChangeX() {
    return changeX;
  }

  /**
   * The change in y position of the piece for this move. Could be a finite change, or an infinite
   * change, depending on the subclass that implements
   *
   * @return The change in y position
   */
  protected int getChangeY() {
    return changeY;
  }

  /**
   * Tells whether this movement must take another piece when this movement object is executed
   *
   * @return True if this move must take another piece, false if not
   */
  protected boolean isMustTake() {
    return mustTake;
  }

  /**
   * Allows a subclass to set the change in the x direction that this move has. Useful for
   * manipulating the implementation of different move types.
   * @param changeX The change in X direction for the move
   */
  protected void setChangeX(int changeX){
    this.changeX = changeX;
  }

  /**
   * Allows a subclass to set the change in the x direction that this move has. Useful for
   * manipulating the implementation of different move types.
   * @param changeY The change in X direction for the move
   */
  protected void setChangeY(int changeY){
    this.changeY = changeY;
  }

}
